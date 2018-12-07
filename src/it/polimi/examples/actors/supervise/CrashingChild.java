package it.polimi.examples.actors.supervise;

import akka.actor.AbstractActor;
import akka.actor.Props;
import it.polimi.examples.actors.supervise.messages.Command;

public class CrashingChild extends AbstractActor {
	private int messages;

	public CrashingChild() {
		this.messages = 0;
	}

	private void onCommand(Command c) {
		messages++;
		if (messages % 4 == 0) {
			throw new RuntimeException("Crash!!!");
		} else {
			System.out.println("Message " + messages);
		}
	}

	public static Props props() {
		return Props.create(CrashingChild.class);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder(). //
		    match(Command.class, this::onCommand). //
		    build();
	}

}
