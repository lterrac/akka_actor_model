package it.polimi.examples.actors.simple;

import akka.actor.AbstractActor;
import akka.actor.Props;
import it.polimi.examples.actors.simple.messages.SimpleMessage;

public class CounterActor extends AbstractActor {
	private int counter;

	public CounterActor() {
		this.counter = 0;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder() //Create a receiver
		    .match(SimpleMessage.class, this::onMessage) // If receive message of specified type forward it to the method onMessage
		    .build();
	}

	//Must be void to be in method match()
	void onMessage(SimpleMessage message) {
		++counter;
		System.out.println("Counter increased to: " + counter);
	}

	//Creates an Actor starting from the class
	static Props props() {
		return Props.create(CounterActor.class);
	}

}