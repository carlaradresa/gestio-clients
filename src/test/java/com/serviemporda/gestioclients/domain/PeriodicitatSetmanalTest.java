package com.serviemporda.gestioclients.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.serviemporda.gestioclients.web.rest.TestUtil;

public class PeriodicitatSetmanalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodicitatSetmanal.class);
        PeriodicitatSetmanal periodicitatSetmanal1 = new PeriodicitatSetmanal();
        periodicitatSetmanal1.setId(1L);
        PeriodicitatSetmanal periodicitatSetmanal2 = new PeriodicitatSetmanal();
        periodicitatSetmanal2.setId(periodicitatSetmanal1.getId());
        assertThat(periodicitatSetmanal1).isEqualTo(periodicitatSetmanal2);
        periodicitatSetmanal2.setId(2L);
        assertThat(periodicitatSetmanal1).isNotEqualTo(periodicitatSetmanal2);
        periodicitatSetmanal1.setId(null);
        assertThat(periodicitatSetmanal1).isNotEqualTo(periodicitatSetmanal2);
    }
}
