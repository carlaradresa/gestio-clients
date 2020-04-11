import { Moment } from 'moment';
import { IPlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { ICategoria } from 'app/shared/model/categoria.model';
import { IClient } from 'app/shared/model/client.model';
import { ITreballador } from 'app/shared/model/treballador.model';
import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { Estat } from 'app/shared/model/enumerations/estat.model';

export interface IFeina {
  id?: number;
  nom?: string;
  descripcio?: string;
  setmana?: Moment;
  tempsPrevist?: Moment;
  tempsReal?: Moment;
  estat?: Estat;
  intervalControl?: number;
  facturacioAutomatica?: boolean;
  observacions?: string;
  comentarisTreballador?: string;
  plantillaFeina?: IPlantillaFeina;
  categoria?: ICategoria;
  client?: IClient;
  treballadors?: ITreballador[];
  ubicacios?: IUbicacio[];
}

export class Feina implements IFeina {
  constructor(
    public id?: number,
    public nom?: string,
    public descripcio?: string,
    public setmana?: Moment,
    public tempsPrevist?: Moment,
    public tempsReal?: Moment,
    public estat?: Estat,
    public intervalControl?: number,
    public facturacioAutomatica?: boolean,
    public observacions?: string,
    public comentarisTreballador?: string,
    public plantillaFeina?: IPlantillaFeina,
    public categoria?: ICategoria,
    public client?: IClient,
    public treballadors?: ITreballador[],
    public ubicacios?: IUbicacio[]
  ) {
    this.facturacioAutomatica = this.facturacioAutomatica || false;
  }
}
