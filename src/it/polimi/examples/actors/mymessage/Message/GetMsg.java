package it.polimi.examples.actors.mymessage.Message;

import java.io.Serializable;

public class GetMsg implements Serializable {
    private final String key;

    public GetMsg(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Received Get Message. Key: " + key;
    }
}
