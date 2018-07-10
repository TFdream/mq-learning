package mq.tutorials.rabbitmq.springboot.model;

import java.util.Date;

/**
 * @author Ricky Fung
 */
public class EchoDTO {
    private Long id;
    private String content;
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
