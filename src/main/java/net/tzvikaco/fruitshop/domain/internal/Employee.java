package net.tzvikaco.fruitshop.domain.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.tzvikaco.fruitshop.domain.JobTitle;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document(collection = "employee")
public class Employee extends Person {

    @DBRef
    @Field("jobTitle")
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private JobTitle jobTitle;

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Employee jobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return jobTitle.equals(employee.jobTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jobTitle);
    }

    @Override
    public String toString() {
        return "Employee{" +
            super.toString() +
            "jobTitle=" + jobTitle +
            '}';
    }
}
