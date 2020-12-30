package net.tzvikaco.fruitshop.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import net.tzvikaco.fruitshop.web.rest.TestUtil;

public class JobTitleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobTitleDTO.class);
        JobTitleDTO jobTitleDTO1 = new JobTitleDTO();
        jobTitleDTO1.setId("id1");
        JobTitleDTO jobTitleDTO2 = new JobTitleDTO();
        assertThat(jobTitleDTO1).isNotEqualTo(jobTitleDTO2);
        jobTitleDTO2.setId(jobTitleDTO1.getId());
        assertThat(jobTitleDTO1).isEqualTo(jobTitleDTO2);
        jobTitleDTO2.setId("id2");
        assertThat(jobTitleDTO1).isNotEqualTo(jobTitleDTO2);
        jobTitleDTO1.setId(null);
        assertThat(jobTitleDTO1).isNotEqualTo(jobTitleDTO2);
    }
}
