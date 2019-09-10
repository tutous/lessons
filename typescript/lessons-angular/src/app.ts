import { InMemHeroService } from "./webapi/InMemHeroService";
import { HttpClientInMemoryWebApiModule } from "angular-in-memory-web-api";
import { ModuleWithProviders, Provider, FactoryProvider } from "@angular/core";
import { HttpBackend, HttpRequest, HttpEvent } from "@angular/common/http";
import { Observable } from 'rxjs';


let client: ModuleWithProviders<InMemHeroService> = HttpClientInMemoryWebApiModule.forRoot(InMemHeroService);
/** factory from provider */
let providers: Provider[] = client.providers as Provider[];
let fp: FactoryProvider = providers[2] as FactoryProvider;
/** request */
let backend: HttpBackend = fp.provide;
let req: HttpRequest<any> = new HttpRequest("GET", "api/heroes");
let resp: Observable<HttpEvent<any>> = backend.handle(req);
resp.subscribe(value => console.log(value)); 
