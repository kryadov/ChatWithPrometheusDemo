package chat.controller;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import chat.domain.ChatMessageModel;
import chat.message.ChatMessage;
import chat.repository.ChatMessageRepository;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ChatMessageController {
    //
    private static final Counter newMessageRequestsTotal =
            Counter.build()
                    .name("new_messages_total").
                    help("Total new messages.").
                    register();
    //
    private static final Counter newMessageRequestsSaved =
            Counter.build()
                    .name("new_messages_saved").
                    help("Saved new messages.").
                    register();
    //
    public static final String ACTIVE_REQUESTS_LABEL = "active_requests";
    private static final Gauge activeNewMessagesRequests =
            Gauge.build()
                    .name("new_messages_active").
                    help("Active new messages.").
                    labelNames(ACTIVE_REQUESTS_LABEL).
                    register();
    //
    private static final String LOGIN = "login";
    private static final int SIZE = 25;

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView method() {
        return new ModelAndView("redirect:/" + LOGIN);
    }

    @RequestMapping("/login")
    public String login() {
        return LOGIN;
    }

    @RequestMapping("/chat")
    public String chat() {
        return "chat";
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    @MessageMapping("/newMessage")
    @SendTo("/topic/newMessage")
    public ChatMessage save(ChatMessageModel chatMessageModel) {
        try {
            newMessageRequestsTotal.inc();
            activeNewMessagesRequests.labels(ACTIVE_REQUESTS_LABEL).inc();
            //
            LocalDateTime time = LocalDateTime.now();
            ChatMessageModel chatMessage = new ChatMessageModel(chatMessageModel.getText(), chatMessageModel.getAuthor(), time.format(DateTimeFormatter.ISO_LOCAL_TIME) + " " + time.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ChatMessageModel message = chatMessageRepository.save(chatMessage);
            //
            newMessageRequestsSaved.inc();
            //
            List<ChatMessageModel> chatMessageModelList = chatMessageRepository.findAll(PageRequest.of(0, SIZE, Sort.Direction.DESC, "createDate")).getContent();
            return new ChatMessage(chatMessageModelList.toString());
        } finally {
            //
            activeNewMessagesRequests.labels(ACTIVE_REQUESTS_LABEL).dec();
        }
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public HttpEntity<List<ChatMessageModel>> list() {
        List<ChatMessageModel> chatMessageModelList = chatMessageRepository.findAll(PageRequest.of(0, SIZE, Sort.Direction.DESC, "createDate")).getContent();
        return new ResponseEntity<>(chatMessageModelList, HttpStatus.OK);
    }
}
