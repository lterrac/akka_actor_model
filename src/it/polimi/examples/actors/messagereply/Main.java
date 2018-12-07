package it.polimi.examples.actors.messagereply;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import it.polimi.examples.actors.messagereply.message.GetMsg;
import it.polimi.examples.actors.messagereply.message.PutMsg;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final ActorSystem sys = ActorSystem.create("System");
        final ActorRef server = sys.actorOf(Server.props(), "server");
        final ActorRef client = sys.actorOf(Client.props(server), "client");

        final Scanner scanner = new Scanner(System.in);
        while (true) {
            final String line = scanner.nextLine();
            final String[] words = line.split(" ");
            if (words[0].equalsIgnoreCase("put")) {
                final String key = words[1];
                final String val = words[2];
                final PutMsg msg = new PutMsg(key, val);
                client.tell(msg, ActorRef.noSender());
            } else if (words[0].equalsIgnoreCase("get")) {
                final String key = words[1];
                final GetMsg msg = new GetMsg(key);
                client.tell(msg, ActorRef.noSender());
            } else if (words[0].equalsIgnoreCase("quit")) {
                break;
            } else {
                System.out.println("Unknown command");
            }
        }

        scanner.close();
        sys.terminate();
    }

}
