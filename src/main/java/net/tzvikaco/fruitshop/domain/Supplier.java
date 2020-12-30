package net.tzvikaco.fruitshop.domain;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Objects;

@Document(collection = "contact")
@TypeAlias("supplier")
public class Supplier {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return start.equals(supplier.start) && Objects.equals(end, supplier.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
