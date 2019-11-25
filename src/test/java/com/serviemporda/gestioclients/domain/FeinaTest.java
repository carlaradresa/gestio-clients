package com.serviemporda.gestioclients.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.serviemporda.gestioclients.web.rest.TestUtil;

public class FeinaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Feina.class);
        Feina feina1 = new Feina();
        feina1.setId(1L);
        Feina feina2 = new Feina();
        feina2.setId(feina1.getId());
        assertThat(feina1).isEqualTo(feina2);
        feina2.setId(2L);
        assertThat(feina1).isNotEqualTo(feina2);
        feina1.setId(null);
        assertThat(feina1).isNotEqualTo(feina2);
    }
}
