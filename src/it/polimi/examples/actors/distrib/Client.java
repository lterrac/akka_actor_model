package it.polimi.examples.actors.distrib;

import java.io.File;
import java.util.Scanner;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import it.polimi.examples.actors.distrib.messages.TextMessage;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		final Config conf = ConfigFactory.parseFile(new File("conf/client.conf"));
		final ActorSystem sys = ActorSystem.create("Client", conf);
		final ActorRef client = sys.actorOf(ClientActor.props(), "ClientActor");

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

class ClientActor extends AbstractActor {
	private ActorSelection server;

	@Override
	public void preStart() throws Exception {
		super.preStart();
		server = getContext().actorSelection("akka.tcp://Server@127.0.0.1:6123/user/ServerActor");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder() //
		    .match(TextMessage.class, this::onTextMessage) //
		    .build();
	}

	private final void onTextMessage(TextMessage message) {
		if (!message.reply()) {
			System.out.println("Received " + message + " from client. Forwarding to server.");
			server.tell(message, self());
		} else {
			System.out.println("Received reply " + message + " from server.");
		}
	}

	static Props props() {
		return Props.create(ClientActor.class);
	}

}
