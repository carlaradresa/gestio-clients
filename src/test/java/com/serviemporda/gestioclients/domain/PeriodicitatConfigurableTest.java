package com.serviemporda.gestioclients.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.serviemporda.gestioclients.web.rest.TestUtil;

public class PeriodicitatConfigurableTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodicitatConfigurable.class);
        PeriodicitatConfigurable periodicitatConfigurable1 = new PeriodicitatConfigurable();
        periodicitatConfigurable1.setId(1L);
        PeriodicitatConfigurable periodicitatConfigurable2 = new PeriodicitatConfigurable();
        periodicitatConfigurable2.setId(periodicitatConfigurable1.getId());
        assertThat(periodicitatConfigurable1).isEqualTo(periodicitatConfigurable2);
        periodicitatConfigurable2.setId(2L);
        assertThat(periodicitatConfigurable1).isNotEqualTo(periodicitatConfigurable2);
        periodicitatConfigurable1.setId(null);
        assertThat(periodicitatConfigurable1).isNotEqualTo(periodicitatConfigurable2);
    }
}
