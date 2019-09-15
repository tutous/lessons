export interface Repository<T> {
    getAll: () => T[];
    add:(t: T) => void;
}