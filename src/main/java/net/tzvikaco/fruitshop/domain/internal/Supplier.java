package net.tzvikaco.fruitshop.domain.internal;

import net.tzvikaco.fruitshop.domain.Contact;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Objects;

import static java.time.LocalDate.now;

@Document(collection = "supplier")
public class Supplier extends Contact {

    @Field("start_date")
    private LocalDate startDate;

    @Field("end_date")
    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Supplier startDate(LocalDate start) {
        setStartDate(start);
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Supplier endDate(LocalDate localDate) {
        setEndDate(endDate);
        return this;
    }

    public boolean isActive() {
        return startDate != null && !startDate.isAfter(now()) && (endDate == null || endDate.isAfter(now()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(startDate, supplier.startDate) && Objects.equals(endDate, supplier.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDate, endDate);
    }

    @Override
    public String toString() {
        return "Supplier{" +
            super.toString() +
            "start=" + startDate +
            ", end=" + endDate +
            '}';
    }
}
