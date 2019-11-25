import { IUbicacio } from 'app/shared/model/ubicacio.model';

export interface IVenedor {
  id?: number;
  numero?: number;
  nom?: string;
  telefon?: string;
  email?: string;
  observacions?: string;
  ubicacio?: IUbicacio;
}

export class Venedor implements IVenedor {
  constructor(
    public id?: number,
    public numero?: number,
    public nom?: string,
    public telefon?: string,
    public email?: string,
    public observacions?: string,
    public ubicacio?: IUbicacio
  ) {}
}
