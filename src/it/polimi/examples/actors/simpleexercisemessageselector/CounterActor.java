package it.polimi.examples.actors.simpleexercisemessageselector;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class CounterActor extends AbstractActor {
    private int counter;

    public CounterActor() {
        this.counter = 0;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder() //Create a receiver
                .match(Message.class, this::onMessage) // If receive message of specified type forward it to the method onMessage
                .build();
    }

    void onMessage(Message message) {
        if (message instanceof IncreaseMessage) {
            ++counter;
            System.out.println("Counter increased to: " + counter);
        }
        if (message instanceof DecreaseMessage) {
            --counter;
            System.out.println("Counter decreased to: " + counter);
        }
    }


    //Creates an Actor starting from the class
    static Props props() {
        return Props.create(it.polimi.examples.actors.simpleexercisemessageclass.CounterActor.class);
    }

}
