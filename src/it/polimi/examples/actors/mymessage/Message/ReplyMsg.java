package it.polimi.examples.actors.mymessage.Message;

import java.io.Serializable;

public class ReplyMsg implements Serializable {
    private final String value;

    public ReplyMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Reveid Reply : " + value;
    }
}
