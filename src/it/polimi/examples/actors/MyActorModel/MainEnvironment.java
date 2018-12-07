package it.polimi.examples.actors.MyActorModel;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import it.polimi.examples.actors.MyActorModel.messages.MinusMessage;
import it.polimi.examples.actors.MyActorModel.messages.PlusMessage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainEnvironment {

    private final static int NUM_THREADS = 16;
    final static int NUM_OF_MESSAGES = 10000;

    public static void main(String[] args) throws IOException {
        final ActorSystem sys = ActorSystem.create("System");
        final ActorRef counter = sys.actorOf(MyActor.props(), "ActorCounter");

        final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_OF_MESSAGES; i++) {
            executorService.submit(() -> counter.tell(new PlusMessage(), ActorRef.noSender()));
            executorService.submit(() -> counter.tell(new MinusMessage(), ActorRef.noSender()));
        }

        System.in.read();
        executorService.shutdown();
        sys.terminate();
    }

}
