import { IPlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { Periodicitat } from 'app/shared/model/enumerations/periodicitat.model';

export interface IPeriodicitatConfigurable {
  id?: number;
  frequencia?: number;
  periodicitat?: Periodicitat;
  observacions?: string;
  plantilla?: IPlantillaFeina;
}

export class PeriodicitatConfigurable implements IPeriodicitatConfigurable {
  constructor(
    public id?: number,
    public frequencia?: number,
    public periodicitat?: Periodicitat,
    public observacions?: string,
    public plantilla?: IPlantillaFeina
  ) {}
}
