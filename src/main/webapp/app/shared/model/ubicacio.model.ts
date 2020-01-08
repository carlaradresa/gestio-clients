import { IClient } from 'app/shared/model/client.model';
import { IVenedor } from 'app/shared/model/venedor.model';
import { IFeina } from 'app/shared/model/feina.model';

export interface IUbicacio {
  id?: number;
  longitud?: number;
  latitud?: number;
  ubicacio?: string;
  client?: IClient;
  venedor?: IVenedor;
  venedor?: IFeina;
}

export class Ubicacio implements IUbicacio {
  constructor(
    public id?: number,
    public longitud?: number,
    public latitud?: number,
    public ubicacio?: string,
    public client?: IClient,
    public venedor?: IVenedor,
    public venedor?: IFeina
  ) {}
}
