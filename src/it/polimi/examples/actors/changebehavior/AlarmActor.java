package it.polimi.examples.actors.changebehavior;

import akka.actor.AbstractActor;
import akka.actor.Props;
import it.polimi.examples.actors.changebehavior.messages.ActivateMsg;
import it.polimi.examples.actors.changebehavior.messages.DeactivateMsg;
import it.polimi.examples.actors.changebehavior.messages.TriggerMsg;

public class AlarmActor extends AbstractActor {
	@Override
	public Receive createReceive() {
		return disabled();
	}

	private final Receive enabled() {
		return receiveBuilder(). //
		    match(TriggerMsg.class, this::onTrigger). //
		    match(DeactivateMsg.class, this::onDeactivate). //
		    build();
	}

	private final Receive disabled() {
		return receiveBuilder(). //
		    match(ActivateMsg.class, this::onActivate). //
		    build();
	}

	private void onTrigger(TriggerMsg msg) {
		System.out.println("Alarm!!!!");
	}

	private void onActivate(ActivateMsg msg) {
		System.out.println("Becoming enabled");
		getContext().become(enabled());
	}

	private void onDeactivate(DeactivateMsg msg) {
		System.out.println("Becoming disabled");
		getContext().become(disabled());
	}

	static Props props() {
		return Props.create(AlarmActor.class);
	}

}