package it.polimi.examples.actors.stash;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import it.polimi.examples.actors.stash.messages.TextMessage;

public class ClientActor extends AbstractActor {
	private final ActorRef server;

	public ClientActor(ActorRef server) {
		this.server = server;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder() //
		    .match(TextMessage.class, this::onTextMessage) //
		    .build();
	}

	private final void onTextMessage(TextMessage message) {
		if (!message.reply()) {
			System.out.println("Client received " + message + " from input. Forwarding to server.");
			server.tell(message, self());
		} else {
			System.out.println("Client received reply " + message + " from server.");
		}
	}

	public static final Props props(ActorRef server) {
		return Props.create(ClientActor.class, server);
	}

}