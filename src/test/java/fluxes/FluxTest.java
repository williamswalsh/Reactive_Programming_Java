import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FluxTest {

    @Test
    public void createFlux() {
        Flux<String> letterFlux = Flux.just("A", "B", "C", "D");

        // Test Class to assert Flux contents
        StepVerifier.create(letterFlux)
                .expectNext("A")    // is "A" the first element?
                .expectNext("B")
                .expectNext("C")
                .expectNext("D")
                .verifyComplete();  // is the Flux completed?
    }

    @Test
    public void createFruitFlux() {
        Flux<String> fruitFlux = Flux.just("Apple", "Banana");
        fruitFlux.subscribe(System.out::println);               // I/O takes a lot more time

        // Values still present in Flux
        // Verified by the assertions below
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Banana")
                .verifyComplete();
    }

    @Test
    public void createFluxFromStringArray() {
        String[] fruitArr = {"Apple", "Banana"};

        Flux<String> fruitFlux = Flux.fromArray(fruitArr);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Banana")
                .verifyComplete();
    }

    @Test
    public void createFluxFromIterable() {
        List<String> fruitList = Arrays.asList("Apple", "Banana");

        Flux<String> fruitFlux = Flux.fromIterable(fruitList);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Banana")
                .verifyComplete();
    }

    @Test
    public void createFluxFromStream() {
        Stream<String> fruitStream = Stream.of("Apple", "Banana");

        Flux<String> fruitFlux = Flux.fromStream(fruitStream);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Banana")
                .verifyComplete();
    }

    @Test
    public void createRangeFlux() {
        Flux<Integer> rangeFlux = Flux.range(0, 5); // Start at 0, 5 numbers total - 0,1,2,3,4

        StepVerifier.create(rangeFlux)
                .expectNext(0)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }

    @Test
    public void createIntervalFlux() {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1))  // Every 1 second emit a value
                        .take(2);   // Emit 2 values starting at 0 -> 0,1

        StepVerifier.create(intervalFlux)
                .expectNext(0L)
                .expectNext(1L)
                .verifyComplete();
    }
}
