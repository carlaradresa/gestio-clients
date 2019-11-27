import { Moment } from 'moment';
import { IFeina } from 'app/shared/model/feina.model';
import { ITreballador } from 'app/shared/model/treballador.model';

export interface IMarcatge {
  id?: number;
  horaEntrada?: Moment;
  horaSortida?: Moment;
  desviacio?: boolean;
  feina?: IFeina;
  treballador?: ITreballador;
}

export class Marcatge implements IMarcatge {
  constructor(
    public id?: number,
    public horaEntrada?: Moment,
    public horaSortida?: Moment,
    public desviacio?: boolean,
    public feina?: IFeina,
    public treballador?: ITreballador
  ) {
    this.desviacio = this.desviacio || false;
  }
}
