package it.polimi.examples.actors.messagereply.message;

public class GetMsg {
    public String key;

    public GetMsg(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "GetMsg[" + key + "]";
    }
}
