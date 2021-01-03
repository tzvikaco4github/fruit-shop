package net.tzvikaco.fruitshop.service.dto.internal;

import net.tzvikaco.fruitshop.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeDTOTest {

    private static final String DUMMY_ID = String.valueOf(new Random().nextInt());
    private static final String DUMMY_TITLE = "MR";

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDTO.class);
        EmployeeDTO employeeDTO1 = createDTO();
        employeeDTO1.setId("id1");
        EmployeeDTO employeeDTO2 = createDTO();
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
        employeeDTO2.setId(employeeDTO1.getId());
        assertThat(employeeDTO1).isEqualTo(employeeDTO2);
        employeeDTO2.setId("id2");
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
        employeeDTO1.setId(null);
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
    }

    public EmployeeDTO createDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setJobTitleId(DUMMY_ID);
        employeeDTO.setJobTitleTitle(DUMMY_TITLE);
        return employeeDTO;
    }
}
