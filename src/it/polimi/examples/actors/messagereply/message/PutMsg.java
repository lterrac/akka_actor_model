package it.polimi.examples.actors.messagereply.message;

public class PutMsg {
    public String key;
    public String value;

    public PutMsg(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "PutMsg[" + key + ", " + value + "]";
    }
}
