package net.tzvikaco.fruitshop;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static net.tzvikaco.fruitshop.RandomDateUtils.START_LOCAL_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RandomDateUtilsTest {

    @Test
    void randomLocalDate() {
        assertThat(RandomDateUtils.randomLocalDate()).isStrictlyBetween(START_LOCAL_DATE, LocalDate.now());
    }
}
