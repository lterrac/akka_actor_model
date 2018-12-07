package it.polimi.examples.actors.mymessage;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import it.polimi.examples.actors.mymessage.Message.GetMsg;
import it.polimi.examples.actors.mymessage.Message.PutMsg;
import it.polimi.examples.actors.mymessage.Message.ReplyMsg;

import java.util.HashMap;
import java.util.Map;

public class Server extends AbstractActor {

    private Map<String, String> contactList = new HashMap<>();


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(GetMsg.class, this::get)
                .match(PutMsg.class, this::put)
                .build();
    }

    private void put(PutMsg message) {
        System.out.println("Server received: " + message);
        contactList.put(message.getKey(), message.getValue());
    }

    private void get(GetMsg message) {
        System.out.println("Server received: " + message);
        final String value = contactList.get(message.getKey());
        final ReplyMsg reply = new ReplyMsg(value);
        sender().tell(reply, self());

    }

    public static Props props() {
        return Props.create(Server.class);
    }
}
