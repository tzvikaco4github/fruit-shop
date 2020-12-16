package net.tzvikaco.fruitshop.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemMapperTest {

    private ItemMapper itemMapper;

    @BeforeEach
    public void setUp() {
        itemMapper = new ItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(itemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(itemMapper.fromId(null)).isNull();
    }
}
