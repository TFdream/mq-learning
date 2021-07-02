package mq.learning.rabbitmq.entity;

import java.util.Date;

/**
 * @author Ricky Fung
 */
public class CloseOrderDTO {
    private String orderNo;
    /**
     * 关单时间
     */
    private Date closeTime;
    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 延时时间，单位：毫秒
     */
    private int delayMs;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getDelayMs() {
        return delayMs;
    }

    public void setDelayMs(int delayMs) {
        this.delayMs = delayMs;
    }
}
