package chat.domain;

import org.springframework.data.annotation.Id;

public class ChatMessageModel {

    @Id
    private String id;

    private String text;
    private String author;
    private String createDate;

    public ChatMessageModel() {
    }

    public ChatMessageModel(String text, String author, String createDate) {
        this.text = text;
        this.author = author;
        this.createDate = createDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ",\"text\":\"" + replaceQuotes(text) + '\"' +
                ",\"author\":\"" + replaceQuotes(author) + '\"' +
                ",\"createDate\":\"" + createDate+ '\"' +
                '}';
    }

    private static String replaceQuotes(String text) {
        return text.replaceAll("[\"]", "\\\\\"");
    }
}
