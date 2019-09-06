/** 
import * as mod_type from "./type/type";
import { InMemHeroService } from "./webapi/InMemHeroService";
import { HttpClientInMemoryWebApiModule } from "angular-in-memory-web-api";
import { ModuleWithProviders, Provider, FactoryProvider } from "@angular/core";
import { HttpBackend, HttpRequest, HttpEvent } from "@angular/common/http";
import { Observable } from "rxjs";
import * as mod_iface from './interface/interface'; */
import * as mod_classes from './classes/classes';
import * as mod_functions from './functions/functions';
import * as mod_generics from './generics/generics';
import * as mod_decorators from './decorators/decorators';

//mod_type;
//mod_iface;
//mod_classes;
//mod_functions;
//mod_generics;
mod_decorators;

/** 
let client: ModuleWithProviders<
  InMemHeroService
> = HttpClientInMemoryWebApiModule.forRoot(InMemHeroService);
let providers: Provider[] = client.providers as Provider[];
let fp: FactoryProvider = providers[2] as FactoryProvider;
let backend: HttpBackend = fp.provide;
let req: HttpRequest<any> = new HttpRequest("GET", "api/heroes");
let resp: Observable<HttpEvent<any>> = backend.handle(req);
resp.subscribe(value => console.log(value)); */
