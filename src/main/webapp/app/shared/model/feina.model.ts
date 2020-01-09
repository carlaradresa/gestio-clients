import { Moment } from 'moment';
import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { IPlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { ICategoria } from 'app/shared/model/categoria.model';
import { IClient } from 'app/shared/model/client.model';
import { ITreballador } from 'app/shared/model/treballador.model';

export interface IFeina {
  id?: number;
  nom?: string;
  descripcio?: string;
  setmana?: Moment;
  tempsPrevist?: number;
  tempsReal?: number;
  estat?: boolean;
  intervalControl?: number;
  facturacioAutomatica?: boolean;
  observacions?: string;
  comentarisTreballador?: string;
  ubicacio?: string;
  ubicacio?: IUbicacio;
  plantillaFeina?: IPlantillaFeina;
  categoria?: ICategoria;
  client?: IClient;
  treballadors?: ITreballador[];
}

export class Feina implements IFeina {
  constructor(
    public id?: number,
    public nom?: string,
    public descripcio?: string,
    public setmana?: Moment,
    public tempsPrevist?: number,
    public tempsReal?: number,
    public estat?: boolean,
    public intervalControl?: number,
    public facturacioAutomatica?: boolean,
    public observacions?: string,
    public comentarisTreballador?: string,
    public ubicacio?: string,
    public ubicacio?: IUbicacio,
    public plantillaFeina?: IPlantillaFeina,
    public categoria?: ICategoria,
    public client?: IClient,
    public treballadors?: ITreballador[]
  ) {
    this.estat = this.estat || false;
    this.facturacioAutomatica = this.facturacioAutomatica || false;
  }
}
