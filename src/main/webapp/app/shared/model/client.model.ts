import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { IVenedor } from 'app/shared/model/venedor.model';

export interface IClient {
  id?: number;
  nom?: string;
  direccio?: string;
  localitat?: string;
  telefon?: string;
  email?: string;
  nif?: string;
  codiUbicacio?: string;
  observacions?: string;
  ubicacio?: IUbicacio;
  venedor?: IVenedor;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public nom?: string,
    public direccio?: string,
    public localitat?: string,
    public telefon?: string,
    public email?: string,
    public nif?: string,
    public codiUbicacio?: string,
    public observacions?: string,
    public ubicacio?: IUbicacio,
    public venedor?: IVenedor
  ) {}
}
