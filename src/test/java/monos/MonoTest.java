package monos;

import org.junit.Test;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;

public class MonoTest {

    @Test
    public void testMonoCreationUsingJust() {
        String expected = "Hello World, REACTIVE style!";

        Mono.just("Reactive")
                .map(String::toUpperCase)
                .map(s -> "Hello World, " + s + " style!" )
                .subscribe(result -> assertEquals(expected, result));
    }

    @Test
    public void testMonoCreationUsingJustReverseMapSequence() {
        String expected = "HELLO WORLD, REACTIVE STYLE!";

        Mono.just("Reactive")
                .map(s -> "Hello World, " + s + " style!" )
                .map(String::toUpperCase)
                .subscribe(result -> assertEquals(expected, result));
    }
}
