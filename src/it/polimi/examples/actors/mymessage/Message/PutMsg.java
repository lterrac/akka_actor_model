package it.polimi.examples.actors.mymessage.Message;

import java.io.Serializable;

public class PutMsg implements Serializable {
    private final String key;
    private final String value;

    public PutMsg(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Received Put Message. Key: " + key + ". value: " + value;
    }
}
