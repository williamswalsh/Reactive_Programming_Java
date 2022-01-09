package fluxes;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class CombiningFluxes {

    @Test
    public void combineTwoFluxUsingSetDelays() {
        Flux<String> characters   = Flux.just("Ash Ketchum", "Sponge Bob Square Pants", "Eric Cartman")
                .delayElements(Duration.ofMillis(500));     // Produce element every 500 millis

        Flux<String> favoriteFoods = Flux.just("Dumpling", "Krabby Patty", "KFC Gravy")
                .delayElements(Duration.ofMillis(500))      // Produce element every 500 millis
                .delaySubscription(Duration.ofMillis(250)); // Delay subscription to this Flux - 250 millis

        Flux<String> combined = characters.mergeWith(favoriteFoods);

        StepVerifier.create(combined)
                .expectNext("Ash Ketchum")
                .expectNext("Dumpling")
                .expectNext("Sponge Bob Square Pants")
                .expectNext("Krabby Patty")
                .expectNext("Eric Cartman")
                .expectNext("KFC Gravy")
                .verifyComplete();
    }

    @Test
    public void combineTwoFluxUsingZip() {
        Flux<String> characters    = Flux.just("Ash Ketchum", "Sponge Bob Square Pants", "Eric Cartman");
        Flux<String> favoriteFoods = Flux.just("Dumpling", "Krabby Patty", "KFC Gravy");

        Flux<Tuple2<String,String>> combined = Flux.zip(characters, favoriteFoods); // Zip function will combine Fluxes A1, B1, A2, B2, A3, B3, etc.

        StepVerifier.create(combined)
                .expectNextMatches(p ->
                        p.getT1().equals("Ash Ketchum") &&
                        p.getT2().equals("Dumpling"))
                .expectNextMatches(p ->
                        p.getT1().equals("Sponge Bob Square Pants") &&
                        p.getT2().equals("Krabby Patty"))
                .expectNextMatches(p ->
                        p.getT1().equals("Eric Cartman") &&
                        p.getT2().equals("KFC Gravy"))
                .verifyComplete();
    }

    @Test
    public void combineTwoFluxUsingZipWithLambda() {
        Flux<String> characters    = Flux.just("Ash Ketchum", "Sponge Bob Square Pants", "Eric Cartman");
        Flux<String> favoriteFoods = Flux.just("Dumpling", "Krabby Patty", "KFC Gravy");

        // Can pass lambda which will be used to combine the source fluxes
        // You can see that here the combined flux contains strings not Tuples (as before)
        Flux<String> combined = Flux.zip(characters, favoriteFoods, (c, f) -> c + " eats " + f);

        StepVerifier.create(combined)
                .expectNext("Ash Ketchum eats Dumpling")
                .expectNext("Sponge Bob Square Pants eats Krabby Patty")
                .expectNext("Eric Cartman eats KFC Gravy")
                .verifyComplete();
    }

    @Test
    public void combineTwoFluxUsingFirstMethod() {
        Flux<String> slowFlux = Flux.just("snail", "tortoise", "Sloth").delaySubscription(Duration.ofMillis(250));
        Flux<String> fastFlux = Flux.just("Cheetah", "Swallow", "Bear");

        Flux<String> faster = Flux.first(slowFlux, fastFlux);

        StepVerifier.create(faster)
                .expectNext("Cheetah")
                .expectNext("Swallow")
                .expectNext("Bear")
                .verifyComplete();
    }
}
