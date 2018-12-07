package it.polimi.examples.actors.stash;

import akka.actor.AbstractActorWithStash;
import akka.actor.Props;
import it.polimi.examples.actors.stash.messages.TextMessage;

public class ServerActor extends AbstractActorWithStash {

	@Override
	public Receive createReceive() {
		return awake();
	}

	private final Receive awake() {
		return receiveBuilder() //
		    .match(TextMessage.class, msg -> msg.getContent().equals(Consts.sleepMsg), this::onSleepMessage) //
		    .match(TextMessage.class, this::onTextMessage) //
		    .build();
	}

	private final Receive asleep() {
		return receiveBuilder() //
		    .match(TextMessage.class, msg -> msg.getContent().equals(Consts.wakeUpMsg), this::onAwakeMessage) //
		    .matchAny(msg -> stash()) //
		    .build();
	}

	private final void onTextMessage(TextMessage message) {
		System.out.println("Server received " + message);
		sender().tell(new TextMessage(message), self());
	}

	private final void onSleepMessage(TextMessage message) {
		getContext().become(asleep());
	}

	private final void onAwakeMessage(TextMessage message) {
		getContext().become(awake());
		unstashAll();
	}

	public static final Props props() {
		return Props.create(ServerActor.class);
	}

}