package it.polimi.examples.actors.messagereply.message;

public class ReplyMsg {
    private final String value;

    public ReplyMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == null)
            return "ReplyMsg[No values found]";
        return "ReplyMsg[" + value + "]";
    }
}
