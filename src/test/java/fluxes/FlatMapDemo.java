package fluxes;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FlatMapDemo {

    // flatmap allows for asynchronous processing
    // map is synchronous
    @Test
    public void flatMapSurfers() {
        Flux<Player> proSurferFlux = Flux.just("Kelly Slater", "Nic Von Rupp", "Gearoid McDaid")
                .flatMap(s -> Mono.just(s)
                        .map(Player::new)
                .subscribeOn(Schedulers.parallel()));

        List<Player> surfers = Arrays.asList(
                new Player("Kelly Slater"),
                new Player("Nic Von Rupp"),
                new Player("Gearoid McDaid"));

        StepVerifier.create(proSurferFlux)
                .expectNextMatches(surfers::contains)
                .expectNextMatches(surfers::contains)
                .expectNextMatches(surfers::contains)
                .verifyComplete();
    }
}
