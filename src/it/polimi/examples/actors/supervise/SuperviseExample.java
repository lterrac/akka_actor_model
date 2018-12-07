package it.polimi.examples.actors.supervise;

import java.io.IOException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import it.polimi.examples.actors.supervise.messages.Command;

public class SuperviseExample {

	private static final int numMessages = 100;

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create();

		final ActorRef supervisor = system.actorOf(Supervisor.props(), "supervisor");

		for (int i = 0; i < numMessages; i++) {
			supervisor.tell(new Command(), ActorRef.noSender());
		}

		try {
			System.in.read();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		system.terminate();
	}
}
