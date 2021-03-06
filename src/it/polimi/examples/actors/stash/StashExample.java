package it.polimi.examples.actors.stash;

import java.util.Scanner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import it.polimi.examples.actors.stash.messages.TextMessage;

/**
 * The server stashes the messages when it receives a message "sleep" and
 * unstashes and processes all pending messages when it receives a message
 * "wakeup".
 */

public class StashExample {

	public static void main(String[] args) throws InterruptedException {
		final ActorSystem sys = ActorSystem.create("System");

		final ActorRef server = sys.actorOf(ServerActor.props(), "ServerActor");
		final ActorRef client = sys.actorOf(ClientActor.props(server), "ClientActor");

		final Scanner scanner = new Scanner(System.in);

		while (true) {
			final String content = scanner.nextLine();
			if (content.equals("quit")) {
				break;
			} else {
				client.tell(new TextMessage(content), ActorRef.noSender());
			}
		}

		scanner.close();
		sys.terminate();
	}

}
