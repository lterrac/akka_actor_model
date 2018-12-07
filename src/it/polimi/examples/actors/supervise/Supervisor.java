package it.polimi.examples.actors.supervise;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

public class Supervisor extends AbstractActor {

	// OneForOneStrategy: the strategy applies only to the crashed child
	// OneForAllStrategy: the strategy applies to all children

	// We apply the strategy at most 10 times in a window of 10 seconds.
	// If in the last 10 seconds the child crashed more than 10 times, then
	// we do not apply any strategy anymore.

	// We can also select a different strategy for each type of exception/error.

	// Possible strategies: resume, restart, stop, escalate.
	// Try them out!
	public static final OneForOneStrategy strategy = new OneForOneStrategy(//
	    10, //
	    Duration.create("10 seconds"), //
	    DeciderBuilder //
	        .match(RuntimeException.class, ex -> SupervisorStrategy.resume()) //
	        .build() //
	);

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

	public static Props props() {
		return Props.create(Supervisor.class);
	}

	@Override
	public Receive createReceive() {
		final ActorRef child = getContext().actorOf(CrashingChild.props(), "child");
		return receiveBuilder(). //
		    matchAny(any -> child.forward(any, getContext())). //
		    build();
	}
}
