package it.polimi.examples.actors.ask;

import akka.actor.AbstractActor;
import akka.actor.Props;
import it.polimi.examples.actors.ask.messages.TextMessage;

public class ServerActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder() //
		    .match(TextMessage.class, this::onTextMessage) //
		    .build();
	}

	private final void onTextMessage(TextMessage message) {
		System.out.println("Server received " + message);
		sender().tell(new TextMessage(message), self());
	}

	static Props props() {
		return Props.create(ServerActor.class);
	}

}