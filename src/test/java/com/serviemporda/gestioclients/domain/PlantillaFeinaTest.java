package com.serviemporda.gestioclients.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.serviemporda.gestioclients.web.rest.TestUtil;

public class PlantillaFeinaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantillaFeina.class);
        PlantillaFeina plantillaFeina1 = new PlantillaFeina();
        plantillaFeina1.setId(1L);
        PlantillaFeina plantillaFeina2 = new PlantillaFeina();
        plantillaFeina2.setId(plantillaFeina1.getId());
        assertThat(plantillaFeina1).isEqualTo(plantillaFeina2);
        plantillaFeina2.setId(2L);
        assertThat(plantillaFeina1).isNotEqualTo(plantillaFeina2);
        plantillaFeina1.setId(null);
        assertThat(plantillaFeina1).isNotEqualTo(plantillaFeina2);
    }
}
