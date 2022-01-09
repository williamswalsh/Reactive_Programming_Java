package fluxes;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TransformingFluxes {

    // map method performs the submitted Function on the items within the Flux
    @Test
    public void usingMap() {
        Flux<Player> proSurferFlux = Flux.just("Kelly Slater", "Nic Von Rupp", "Gearoid McDaid")
                .map(Player::new);

        StepVerifier.create(proSurferFlux)
                .expectNext(new Player("Kelly Slater"))
                .expectNext(new Player("Nic Von Rupp"))
                .expectNext(new Player("Gearoid McDaid"))
                .verifyComplete();
    }

    // buffer combines the items into multiple lists of items of a specific size, 2 in this example
    // N.B. Flux<String> becomes a Flux<List<String>>
    @Test
    public void usingBuffer() {
        Flux<String> surfers = Flux.just("Kelly", "Nic", "Gearoid", "Mick", "Laurie");
        Flux<List<String>> bufferedSurfers = surfers.buffer(2);

        StepVerifier.create(bufferedSurfers)
                .expectNext(Arrays.asList("Kelly", "Nic"))
                .expectNext(Arrays.asList("Gearoid", "Mick"))
                .expectNext(Arrays.asList("Laurie"))
                .verifyComplete();
    }

    // buffer can be used with flatMap to process lists in parallel
    // Above example will process multiple "2 item lists" in parallel
    // List of items will be processed in its own thread
    @Test
    public void usingBufferWithFlatmap() {
        Flux.just("grape", "banana", "pineapple", "apple", "tomato")
                .buffer(2)
                .flatMap(fl -> Flux.fromIterable(fl)
                        .map(String::toUpperCase)
                        .subscribeOn(Schedulers.parallel())
                        .log())
                .subscribe();
    }

    // store all items in flux in a single list
    // call buffer method with no arguments
    // not sure of use case???
    @Test
    public void usingBufferNoArguments() {
        Flux<List<String>> fruitListFlux= Flux.just("grape", "banana", "pineapple", "apple", "tomato")
                .buffer();
    }

    // Collects a flux of items into a single Mono which contains a List of items
    @Test
    public void usingCollectList() {
        Flux<String> fruitFlux= Flux.just("grape", "banana", "pineapple", "apple", "tomato");

        Mono<List<String>> fruitListMono = fruitFlux.collectList();

        StepVerifier.create(fruitListMono)
                .expectNext(Arrays.asList("grape", "banana", "pineapple", "apple", "tomato"))
                .verifyComplete();
    }

    // Collects a flux of items into a single Mono which contains a List of items
    // verifyComplete does behave as expected -> Single Mono
    @Test
    public void usingCollectMap() {
        Flux<String> fruitFlux= Flux.just("grape", "banana", "pineapple", "apple", "tomato");

        Mono<Map<Character, String>> fruitMapMono = fruitFlux.collectMap(value -> value.charAt(0));

        StepVerifier.create(fruitMapMono)
                .expectNextMatches(map -> map.size() == 5 && map.get('g').equals("grape"))
                .verifyComplete();
    }
}
