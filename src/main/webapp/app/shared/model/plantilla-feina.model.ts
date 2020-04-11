import { Moment } from 'moment';
import { IPeriodicitatConfigurable } from 'app/shared/model/periodicitat-configurable.model';
import { IClient } from 'app/shared/model/client.model';
import { IPeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';
import { ITreballador } from 'app/shared/model/treballador.model';

export interface IPlantillaFeina {
  id?: number;
  nom?: string;
  horaInici?: Moment;
  horaFinal?: Moment;
  tempsPrevist?: Moment;
  facturacioAutomatica?: boolean;
  observacions?: string;
  setmanaInicial?: Moment;
  setmanaFinal?: Moment;
  numeroControl?: number;
  periodicitatConfigurable?: IPeriodicitatConfigurable;
  client?: IClient;
  periodicitatSetmanals?: IPeriodicitatSetmanal[];
  treballadors?: ITreballador[];
}

export class PlantillaFeina implements IPlantillaFeina {
  constructor(
    public id?: number,
    public nom?: string,
    public horaInici?: Moment,
    public horaFinal?: Moment,
    public tempsPrevist?: Moment,
    public facturacioAutomatica?: boolean,
    public observacions?: string,
    public setmanaInicial?: Moment,
    public setmanaFinal?: Moment,
    public numeroControl?: number,
    public periodicitatConfigurable?: IPeriodicitatConfigurable,
    public client?: IClient,
    public periodicitatSetmanals?: IPeriodicitatSetmanal[],
    public treballadors?: ITreballador[]
  ) {
    this.facturacioAutomatica = this.facturacioAutomatica || false;
  }
}
