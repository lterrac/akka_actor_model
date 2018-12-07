package it.polimi.examples.actors.messagereply;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import it.polimi.examples.actors.messagereply.message.GetMsg;
import it.polimi.examples.actors.messagereply.message.PutMsg;
import it.polimi.examples.actors.messagereply.message.ReplyMsg;

public class Client extends AbstractActor {
    private final ActorRef server;

    public Client(ActorRef server) {
        this.server = server;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(GetMsg.class, this::onGet).
                match(PutMsg.class, this::onPut).
                match(ReplyMsg.class, this::onReply).
                build();
    }

    private void onReply(ReplyMsg message) {
        System.out.println("Client received " + message);
    }

    private void onPut(PutMsg message) {
        server.tell(message, self());
    }

    private void onGet(GetMsg message) {
        server.tell(message,self());
    }

    static Props props(ActorRef server) {
        return Props.create(Client.class, server);
    }
}
