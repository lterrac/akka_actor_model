package it.polimi.examples.actors.messagereply;

import akka.actor.AbstractActor;
import akka.actor.Props;
import it.polimi.examples.actors.messagereply.message.GetMsg;
import it.polimi.examples.actors.messagereply.message.PutMsg;
import it.polimi.examples.actors.messagereply.message.ReplyMsg;

import java.util.HashMap;
import java.util.Map;

public class Server extends AbstractActor {
    public final Map<String, String> contacts = new HashMap<>();


    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(GetMsg.class, this::get).
                match(PutMsg.class, this::put).
                build();
    }

    private void put(PutMsg message) {
        System.out.println("Server received " + message);
        contacts.put(message.key, message.value);
    }

    private void get(GetMsg message) {
        System.out.println("Server received " + message);
        final String value = contacts.get(message.key);
        final ReplyMsg replyMsg = new ReplyMsg(value);
        sender().tell(replyMsg, self());
    }

    static Props props() {
        return Props.create(Server.class);
    }
}
