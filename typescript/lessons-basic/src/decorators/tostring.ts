import "reflect-metadata";

const toStringPropKey = Symbol("ToStringPropKey");

export var ToString = function () { // this is the decorator factory
    return function (target: any) { // this is the decorator
        target.prototype.toString = function () {
            let out: string = target.name + '[';
            Reflect.ownKeys(this).forEach(p => {
                if (Reflect.getMetadata(toStringPropKey, this, p as string) === true) {
                    out = `${out}${p as string}='${Reflect.get(this, p)}',`
                };
            });
            return out + ']';
        }
    }
}

export var ToStringProp = function (eanabled: boolean): Function {
    let decoratorFunction: Function = Reflect.metadata(toStringPropKey, eanabled);
    return decoratorFunction;
}
