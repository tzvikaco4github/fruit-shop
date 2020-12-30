package net.tzvikaco.fruitshop.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import net.tzvikaco.fruitshop.web.rest.TestUtil;

public class ContactDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactDTO.class);
        ContactDTO contactDTO1 = new ContactDTO();
        contactDTO1.setId("id1");
        ContactDTO contactDTO2 = new ContactDTO();
        assertThat(contactDTO1).isNotEqualTo(contactDTO2);
        contactDTO2.setId(contactDTO1.getId());
        assertThat(contactDTO1).isEqualTo(contactDTO2);
        contactDTO2.setId("id2");
        assertThat(contactDTO1).isNotEqualTo(contactDTO2);
        contactDTO1.setId(null);
        assertThat(contactDTO1).isNotEqualTo(contactDTO2);
    }
}
