package it.polimi.examples.actors.simpleexercisemessageselector;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Counter {
    private static final int numThreads = 16;
    private static final int numMessages = 1000;

    public static void main(String[] args) throws InterruptedException, IOException {
        final ActorSystem sys = ActorSystem.create("System");
        final ActorRef counter = sys.actorOf(CounterActor.props(), "counter"); //Creates a new actor taking in input CounterActor.properties and name

        // Send messages from multiple threads in parallel
        final ExecutorService exec = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numMessages; i++) {
            //Sends message to actor counter. Send message and the sender
            exec.submit(() -> counter.tell(new IncreaseMessage(), ActorRef.noSender()));
        }

        for (int i = 0; i < numMessages/2; i++) {
            exec.submit(() -> counter.tell(new DecreaseMessage(), ActorRef.noSender()));
        }
        // Wait for all messages to be sent and received
        System.in.read();
        exec.shutdown();
        sys.terminate();
    }

}

