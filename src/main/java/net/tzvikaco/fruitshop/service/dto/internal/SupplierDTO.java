package net.tzvikaco.fruitshop.service.dto.internal;

import java.time.LocalDate;

public class SupplierDTO extends PersonDTO {

    private LocalDate start;

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
    public String toString() {
        return "SupplierDTO{ " +
            super.toString() +
            "start=" + start +
            ", end=" + end +
            '}';
    }
}
