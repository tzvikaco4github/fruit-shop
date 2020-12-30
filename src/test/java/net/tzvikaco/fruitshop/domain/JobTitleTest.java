package net.tzvikaco.fruitshop.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import net.tzvikaco.fruitshop.web.rest.TestUtil;

public class JobTitleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobTitle.class);
        JobTitle jobTitle1 = new JobTitle();
        jobTitle1.setId("id1");
        JobTitle jobTitle2 = new JobTitle();
        jobTitle2.setId(jobTitle1.getId());
        assertThat(jobTitle1).isEqualTo(jobTitle2);
        jobTitle2.setId("id2");
        assertThat(jobTitle1).isNotEqualTo(jobTitle2);
        jobTitle1.setId(null);
        assertThat(jobTitle1).isNotEqualTo(jobTitle2);
    }
}
