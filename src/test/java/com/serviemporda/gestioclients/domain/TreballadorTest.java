package com.serviemporda.gestioclients.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.serviemporda.gestioclients.web.rest.TestUtil;

public class TreballadorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Treballador.class);
        Treballador treballador1 = new Treballador();
        treballador1.setId(1L);
        Treballador treballador2 = new Treballador();
        treballador2.setId(treballador1.getId());
        assertThat(treballador1).isEqualTo(treballador2);
        treballador2.setId(2L);
        assertThat(treballador1).isNotEqualTo(treballador2);
        treballador1.setId(null);
        assertThat(treballador1).isNotEqualTo(treballador2);
    }
}
