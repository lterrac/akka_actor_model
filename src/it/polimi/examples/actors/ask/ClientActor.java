package it.polimi.examples.actors.ask;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.pattern.Patterns;
import it.polimi.examples.actors.ask.messages.TextMessage;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

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
		final Future<Object> reply = Patterns.ask(server, message, 1000);
		try {
			System.out.println("Client received reply " + Await.result(reply, Duration.Inf()));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	static Props props(ActorRef server) {
		return Props.create(ClientActor.class, server);
	}

}