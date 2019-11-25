package com.serviemporda.gestioclients.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.serviemporda.gestioclients.web.rest.TestUtil;

public class RepeticioTascaSetmanalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepeticioTascaSetmanal.class);
        RepeticioTascaSetmanal repeticioTascaSetmanal1 = new RepeticioTascaSetmanal();
        repeticioTascaSetmanal1.setId(1L);
        RepeticioTascaSetmanal repeticioTascaSetmanal2 = new RepeticioTascaSetmanal();
        repeticioTascaSetmanal2.setId(repeticioTascaSetmanal1.getId());
        assertThat(repeticioTascaSetmanal1).isEqualTo(repeticioTascaSetmanal2);
        repeticioTascaSetmanal2.setId(2L);
        assertThat(repeticioTascaSetmanal1).isNotEqualTo(repeticioTascaSetmanal2);
        repeticioTascaSetmanal1.setId(null);
        assertThat(repeticioTascaSetmanal1).isNotEqualTo(repeticioTascaSetmanal2);
    }
}
