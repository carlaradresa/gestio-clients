import { Moment } from 'moment';
import { IFeina } from 'app/shared/model/feina.model';
import { Dia } from 'app/shared/model/enumerations/dia.model';
import { Periodicitat } from 'app/shared/model/enumerations/periodicitat.model';

export interface IPlantillaFeina {
  id?: number;
  numero?: number;
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
  feinas?: IFeina[];
}

export class PlantillaFeina implements IPlantillaFeina {
  constructor(
    public id?: number,
    public numero?: number,
    public dia?: Dia,
    public horaInici?: Moment,
    public horaFinal?: Moment,
    public periodicitat?: Periodicitat,
    public tempsPrevist?: number,
    public facturacioAutomatica?: boolean,
    public observacions?: string,
    public setmanaInicial?: Moment,
    public setmanaFinal?: Moment,
    public numeroControl?: number,
    public feinas?: IFeina[]
  ) {
    this.facturacioAutomatica = this.facturacioAutomatica || false;
  }
}
