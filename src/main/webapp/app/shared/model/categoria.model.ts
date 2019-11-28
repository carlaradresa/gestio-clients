export interface ICategoria {
  id?: number;
  nomCategoria?: string;
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nomCategoria?: string) {}
}
