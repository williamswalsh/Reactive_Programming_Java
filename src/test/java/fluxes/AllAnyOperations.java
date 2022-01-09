package fluxes;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class AllAnyOperations {

    @Test
    public void usingAll() {
        Flux<String> surfers = Flux.just("Kelly", "Nic", "Gearoid", "Mick", "Laurie");
        Mono<Boolean> doesAllSurfersNamesStartsWithK = surfers.all(s -> s.charAt(0) == 'K');

        StepVerifier.create(doesAllSurfersNamesStartsWithK)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    public void usingAny() {
        Flux<String> surfers = Flux.just("Kelly", "Nic", "Gearoid", "Mick", "Laurie");
        Mono<Boolean> doesAnysurfersNameStartsWithK = surfers.any(s -> s.charAt(0) == 'K');

        StepVerifier.create(doesAnysurfersNameStartsWithK)
                .expectNext(true)
                .verifyComplete();
    }
}
