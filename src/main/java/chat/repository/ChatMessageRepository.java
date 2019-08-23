package chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import chat.domain.ChatMessageModel;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageModel, String> {
    List<ChatMessageModel> findAllByOrderByCreateDateAsc();
}
