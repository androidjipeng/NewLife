package com.xiaoshulin.vipbanlv.bean;

/**
 * Created by jipeng on 2019/3/14.
 */
public class EventBusMessage {
    private int type;
    private String message;
    private String message1;
    public EventBusMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public EventBusMessage(int type) {
        this.type = type;
    }


    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
