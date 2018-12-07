package it.polimi.examples.actors.mymessage;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import it.polimi.examples.actors.mymessage.Message.GetMsg;
import it.polimi.examples.actors.mymessage.Message.PutMsg;
import it.polimi.examples.actors.mymessage.Message.ReplyMsg;

public class Client extends AbstractActor {

    private final ActorRef server;

    public Client(ActorRef server) {
        this.server = server;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(GetMsg.class, this::onGet)
                .match(PutMsg.class, this::onPut)
                .match(ReplyMsg.class, this::onReply)
                .build();
    }

    private void onReply(ReplyMsg message) {
        System.out.println("Client received: " + message);
    }

    private void onPut(PutMsg message) {
        server.tell(message, self());
    }

    private void onGet(GetMsg message) {
        server.tell(message, self());
    }

    static Props props() {
        return Props.create(Client.class);
    }

}
