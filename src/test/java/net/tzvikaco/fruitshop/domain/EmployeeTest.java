package net.tzvikaco.fruitshop.domain;

import net.tzvikaco.fruitshop.domain.internal.Employee;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import net.tzvikaco.fruitshop.web.rest.TestUtil;

public class EmployeeTest {

    @Test
    public void equalsVerifier() throws Exception {
        Address address = new Address();
        JobTitle jobTitle = new JobTitle();
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = new Employee();
        employee1.setId("id1");
        employee1.setAddress(address);
        employee1.setJobTitle(jobTitle);
        Employee employee2 = new Employee();
        employee2.setId(employee1.getId());
        employee2.setAddress(address);
        employee2.setJobTitle(jobTitle);
        assertThat(employee1).isEqualTo(employee2);
        employee2.setId("id2");
        assertThat(employee1).isNotEqualTo(employee2);
        employee1.setId(null);
        assertThat(employee1).isNotEqualTo(employee2);
    }
}
