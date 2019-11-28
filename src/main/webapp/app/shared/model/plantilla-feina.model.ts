import { Moment } from 'moment';
import { Dia } from 'app/shared/model/enumerations/dia.model';
import { Periodicitat } from 'app/shared/model/enumerations/periodicitat.model';

export interface IPlantillaFeina {
  id?: number;
  dia?: Dia;
  horaInici?: Moment;
  horaFinal?: Moment;
  periodicitat?: Periodicitat;
  tempsPrevist?: number;
  facturacioAutomatica?: boolean;
  observacions?: string;
  setmanaInicial?: Moment;
  setmanaFinal?: Moment;
  numeroControl?: number;
}

export class PlantillaFeina implements IPlantillaFeina {
  constructor(
    public id?: number,
    public dia?: Dia,
    public horaInici?: Moment,
    public horaFinal?: Moment,
    public periodicitat?: Periodicitat,
    public tempsPrevist?: number,
    public facturacioAutomatica?: boolean,
    public observacions?: string,
    public setmanaInicial?: Moment,
    public setmanaFinal?: Moment,
    public numeroControl?: number
  ) {
    this.facturacioAutomatica = this.facturacioAutomatica || false;
  }
}
