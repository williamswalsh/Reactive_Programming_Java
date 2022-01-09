import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

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
}

class Player {
    String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
