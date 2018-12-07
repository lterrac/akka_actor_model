package it.polimi.examples.actors.changebehavior;

import java.util.Scanner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import it.polimi.examples.actors.changebehavior.messages.ActivateMsg;
import it.polimi.examples.actors.changebehavior.messages.DeactivateMsg;
import it.polimi.examples.actors.changebehavior.messages.TriggerMsg;

public class ChangeBehavior {

	public static void main(String[] args) {
		final ActorSystem sys = ActorSystem.create("System");
		final ActorRef alarm = sys.actorOf(AlarmActor.props());

		final Scanner scanner = new Scanner(System.in);
		while (true) {
			final String line = scanner.nextLine();
			if (line.equalsIgnoreCase("activate")) {
				alarm.tell(new ActivateMsg(), ActorRef.noSender());
			} else if (line.equalsIgnoreCase("deactivate")) {
				alarm.tell(new DeactivateMsg(), ActorRef.noSender());
			} else if (line.equalsIgnoreCase("trigger")) {
				alarm.tell(new TriggerMsg(), ActorRef.noSender());
			} else if (line.equalsIgnoreCase("quit")) {
				break;
			} else {
				System.out.println("Unknown command");
			}
		}

		scanner.close();
		sys.terminate();
	}

}
