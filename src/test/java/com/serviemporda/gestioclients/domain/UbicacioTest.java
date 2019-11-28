package com.serviemporda.gestioclients.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.serviemporda.gestioclients.web.rest.TestUtil;

public class UbicacioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ubicacio.class);
        Ubicacio ubicacio1 = new Ubicacio();
        ubicacio1.setId(1L);
        Ubicacio ubicacio2 = new Ubicacio();
        ubicacio2.setId(ubicacio1.getId());
        assertThat(ubicacio1).isEqualTo(ubicacio2);
        ubicacio2.setId(2L);
        assertThat(ubicacio1).isNotEqualTo(ubicacio2);
        ubicacio1.setId(null);
        assertThat(ubicacio1).isNotEqualTo(ubicacio2);
    }
}
