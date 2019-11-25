package com.serviemporda.gestioclients.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.serviemporda.gestioclients.web.rest.TestUtil;

public class VenedorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venedor.class);
        Venedor venedor1 = new Venedor();
        venedor1.setId(1L);
        Venedor venedor2 = new Venedor();
        venedor2.setId(venedor1.getId());
        assertThat(venedor1).isEqualTo(venedor2);
        venedor2.setId(2L);
        assertThat(venedor1).isNotEqualTo(venedor2);
        venedor1.setId(null);
        assertThat(venedor1).isNotEqualTo(venedor2);
    }
}
