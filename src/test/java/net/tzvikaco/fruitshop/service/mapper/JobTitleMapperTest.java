package net.tzvikaco.fruitshop.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JobTitleMapperTest {

    private JobTitleMapper jobTitleMapper;

    @BeforeEach
    public void setUp() {
        jobTitleMapper = new JobTitleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(jobTitleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jobTitleMapper.fromId(null)).isNull();
    }
}
