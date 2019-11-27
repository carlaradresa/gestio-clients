package com.serviemporda.gestioclients.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.serviemporda.gestioclients.web.rest.TestUtil;

public class MarcatgeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Marcatge.class);
        Marcatge marcatge1 = new Marcatge();
        marcatge1.setId(1L);
        Marcatge marcatge2 = new Marcatge();
        marcatge2.setId(marcatge1.getId());
        assertThat(marcatge1).isEqualTo(marcatge2);
        marcatge2.setId(2L);
        assertThat(marcatge1).isNotEqualTo(marcatge2);
        marcatge1.setId(null);
        assertThat(marcatge1).isNotEqualTo(marcatge2);
    }
}
