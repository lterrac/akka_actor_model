package it.polimi.examples.actors.MyActorModel;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import it.polimi.examples.actors.MyActorModel.messages.MinusMessage;
import it.polimi.examples.actors.MyActorModel.messages.PlusMessage;

public class MyActor extends AbstractActor {

    private int counter = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PlusMessage.class, this::increment)
                .match(MinusMessage.class, this::decrement)
                .build();
    }

    private void decrement(MinusMessage message) {
        counter--;
        System.out.println("Counter decreases : " + counter);
    }

    private void increment(PlusMessage message) {
        counter++;
        System.out.println("Counter increases : " + counter);
    }


    static Props props() {
        return Props.create(MyActor.class);
    }
}
