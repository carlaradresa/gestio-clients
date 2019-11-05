import { Moment } from 'moment';
import { IPlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { ICategoria } from 'app/shared/model/categoria.model';
import { IClient } from 'app/shared/model/client.model';
import { ITreballador } from 'app/shared/model/treballador.model';
import { IRepeticioTascaSetmanal } from 'app/shared/model/repeticio-tasca-setmanal.model';
import { IMarcatge } from 'app/shared/model/marcatge.model';
import { IControl } from 'app/shared/model/control.model';

export interface IFeina {
  id?: number;
  numero?: number;
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
  plantillaFeina?: IPlantillaFeina;
  categoria?: ICategoria;
  client?: IClient;
  treballadors?: ITreballador[];
  repeticios?: IRepeticioTascaSetmanal[];
  marcatges?: IMarcatge[];
  revisionsRebudes?: IControl[];
}

export class Feina implements IFeina {
  constructor(
    public id?: number,
    public numero?: number,
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
    public plantillaFeina?: IPlantillaFeina,
    public categoria?: ICategoria,
    public client?: IClient,
    public treballadors?: ITreballador[],
    public repeticios?: IRepeticioTascaSetmanal[],
    public marcatges?: IMarcatge[],
    public revisionsRebudes?: IControl[]
  ) {
    this.estat = this.estat || false;
    this.facturacioAutomatica = this.facturacioAutomatica || false;
  }
}