package net.tzvikaco.fruitshop.domain.internal;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Objects;

import static java.time.LocalDate.now;

@Document(collection = "supplier")
public class Supplier extends Person {

    @Field("start")
    private LocalDate start;

    @Field("end")
    private LocalDate end;

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public boolean isActive() {
        return start != null && !start.isAfter(now()) && (end == null || end.isAfter(now()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(start, supplier.start) && Objects.equals(end, supplier.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), start, end);
    }

    @Override
    public String toString() {
        return "Supplier{" +
            super.toString() +
            "start=" + start +
            ", end=" + end +
            '}';
    }
}
