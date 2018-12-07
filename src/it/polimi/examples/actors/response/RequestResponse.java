package it.polimi.examples.actors.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import it.polimi.examples.actors.response.messages.GetMsg;
import it.polimi.examples.actors.response.messages.PutMsg;
import it.polimi.examples.actors.response.messages.ReplyMsg;

public class RequestResponse {

	public static void main(String[] args) {
		final ActorSystem sys = ActorSystem.create("System");
		final ActorRef server = sys.actorOf(SimpleServer.props(), "server");
		final ActorRef client = sys.actorOf(ClientActor.props(server), "client");

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

class SimpleServer extends AbstractActor {
	private final Map<String, String> map = new HashMap<>();

	private final void put(PutMsg msg) {
		System.out.println("Server received " + msg);
		map.put(msg.getKey(), msg.getVal());
	}

	private final void get(GetMsg msg) {
		System.out.println("Server received " + msg);
		final String val = map.get(msg.getKey());
		final ReplyMsg reply = new ReplyMsg(val);
		sender().tell(reply, self());
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder() //
		    .match(PutMsg.class, this::put) //
		    .match(GetMsg.class, this::get) //
		    .build();
	}

	static Props props() {
		return Props.create(SimpleServer.class);
	}
}

class ClientActor extends AbstractActor {
	private final ActorRef server;

	public ClientActor(ActorRef server) {
		this.server = server;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder() //
		    .match(PutMsg.class, this::onPutMsg) //
		    .match(GetMsg.class, this::onGetMsg) //
		    .match(ReplyMsg.class, this::onReplyMsg) //
		    .build();
	}

	private final void onPutMsg(PutMsg putMsg) {
		server.tell(putMsg, self());
	}

	private final void onGetMsg(GetMsg getMsg) {
		server.tell(getMsg, self());
	}

	private final void onReplyMsg(ReplyMsg replyMsg) {
		System.out.println("Client received: " + replyMsg);
	}

	static Props props(ActorRef server) {
		return Props.create(ClientActor.class, server);
	}

}