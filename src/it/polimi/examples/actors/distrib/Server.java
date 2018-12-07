package it.polimi.examples.actors.distrib;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import it.polimi.examples.actors.distrib.messages.TextMessage;

public class Server {

	public static void main(String[] args) throws InterruptedException {
		final Config conf = ConfigFactory.parseFile(new File("conf/server.conf"));
		final ActorSystem sys = ActorSystem.create("Server", conf);
		sys.actorOf(ServerActor.props(), "ServerActor");
	}

}

class ServerActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder() //
		    .match(TextMessage.class, this::onTextMessage) //
		    .build();
	}

	private final void onTextMessage(TextMessage message) {
		System.out.println("Received " + message);
		sender().tell(new TextMessage(message), self());
	}

	static Props props() {
		return Props.create(ServerActor.class);
	}

}
