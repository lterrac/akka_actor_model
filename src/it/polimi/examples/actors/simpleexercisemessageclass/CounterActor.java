package it.polimi.examples.actors.simpleexercisemessageclass;

import akka.actor.AbstractActor;
import akka.actor.Props;
import it.polimi.examples.actors.simpleexercisemessageclass.message.DecreaseMessage;
import it.polimi.examples.actors.simpleexercisemessageclass.message.IncreaseMessage;

public class CounterActor extends AbstractActor {
    private int counter;

    public CounterActor() {
        this.counter = 0;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder() //Create a receiver
                .match(DecreaseMessage.class, this::onMessage)
                .match(IncreaseMessage.class, this::onMessage) // If receive message of specified type forward it to the method onMessage
                .build();
    }

    void onMessage(IncreaseMessage message) {
        ++counter;
        System.out.println("Counter increased to: " + counter);
    }

    //Must be void to be in method match()
    void onMessage(DecreaseMessage message) {
        --counter;
        System.out.println("Counter decreeased to: " + counter);
    }

    //Creates an Actor starting from the class
    static Props props() {
        return Props.create(CounterActor.class);
    }

}