package net.tzvikaco.fruitshop.domain.internal;

import net.tzvikaco.fruitshop.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class SupplierTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Supplier.class);
        Supplier supplier1 = new Supplier();
        supplier1.setId("id1");
        Supplier supplier2 = new Supplier();
        supplier2.setId(supplier1.getId());
        assertThat(supplier1).isEqualTo(supplier2);
        supplier2.setId("id2");
        assertThat(supplier1).isNotEqualTo(supplier2);
        supplier1.setId(null);
        assertThat(supplier1).isNotEqualTo(supplier2);
    }

    @Test
    public void testIsActive_whenStartDateIsNull_thenFalse() {
        Supplier supplier = new Supplier();
        assertThat(supplier.isActive()).isFalse();
    }

    @Test
    public void testIsActive_whenStartDateIsAfterNow_thenFalse() {
        Supplier supplier = new Supplier();
        supplier.setStartDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
        assertThat(supplier.isActive()).isFalse();
    }

    @Test
    public void testIsActive_whenStartDateIsNotAfterNowAndEndDateIsNull_thenTrue() {
        Supplier supplier = new Supplier();
        supplier.setStartDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
        assertThat(supplier.isActive()).isTrue();
    }

    @Test
    public void testIsActive_whenStartDateIsNotAfterNowAndEndDateIsBeforeToday_thenFalse() {
        Supplier supplier = new Supplier();
        supplier.setStartDate(LocalDate.now().minus(2, ChronoUnit.DAYS));
        supplier.setEndDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
        assertThat(supplier.isActive()).isFalse();
    }

    @Test
    public void testIsActive_whenStartDateIsBeforeNowAndEndDateIsAfterToday_thenTrue() {
        Supplier supplier = new Supplier();
        supplier.setStartDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
        supplier.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
        assertThat(supplier.isActive()).isTrue();
    }
}
