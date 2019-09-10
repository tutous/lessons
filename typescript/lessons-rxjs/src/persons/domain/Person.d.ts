import { Identifier as Ident} from './Entity';
export declare interface Person extends Ident<number>{
    id: number;
    name: string;
    foreName: string;
}

