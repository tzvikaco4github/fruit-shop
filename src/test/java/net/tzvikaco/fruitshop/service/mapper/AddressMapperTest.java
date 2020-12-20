package net.tzvikaco.fruitshop.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    public void setUp() {
        addressMapper = new AddressMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(addressMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(addressMapper.fromId(null)).isNull();
    }
}
