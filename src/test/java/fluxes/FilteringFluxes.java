package fluxes;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FilteringFluxes {

    // Skips the first n items
    @Test
    public void usingSkip() {
        Flux<Integer> numbers = Flux
                .just(1, 2, 3, 4, 5)
                .skip(2);

        StepVerifier
                .create(numbers)
                .expectNext(3, 4, 5)
                .verifyComplete();
    }

    // Skips the items for the first n seconds
    @Test
    public void usingSkipWithDuration() {
        Flux<Integer> numbers = Flux
                .just(1, 2, 3, 4, 5)
                .delayElements(Duration.ofSeconds(1))  // delay-publish-delay-publish-delay-publish -> 3 seconds avoids first 2 items
                .skip(Duration.ofSeconds(3));

        StepVerifier
                .create(numbers)
                .expectNext(3, 4, 5)
                .verifyComplete();
    }

    // Take the first n items - opposite of skip
    @Test
    public void usingTake() {
        Flux<Integer> numbers = Flux
                .just(1, 2, 3, 4, 5)
                .take(2);

        StepVerifier
                .create(numbers)
                .expectNext(1, 2)
                .verifyComplete();
    }

    // Take the items for the duration n seconds
    @Test
    public void usingTakeWithDuration() {
        Flux<Integer> numbers = Flux
                .just(1, 2, 3, 4, 5)
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofSeconds(3));

        StepVerifier
                .create(numbers)
                .expectNext(1, 2)
                .verifyComplete();
    }

    // Filter Flux items using Predicate
    @Test
    public void usingFilter() {
        Flux<Integer> numbers = Flux
                .just(1, 2, 3, 4, 5)
                .filter(i -> i % 2 == 0);

        StepVerifier
                .create(numbers)
                .expectNext(2, 4)
                .verifyComplete();
    }

    // Only publish distinct items. If duplicate item exist, only publish item 1 time.
    @Test
    public void usingDistinct() {
        Flux<Integer> numbers = Flux
                .just(1, 2, 3, 4, 5, 1, 3, 7)
                .distinct();

        StepVerifier
                .create(numbers)
                .expectNext(1, 2, 3, 4, 5, 7)
                .verifyComplete();
    }
}
