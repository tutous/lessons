import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injector } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { LayoutModule } from './layout/layout.module';

import { HttpClientModule, HttpClient } from '@angular/common/http';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ErrorModule } from './error/error.module';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { MessageStoreModule } from './shared/store/message-store/message-store.module';

import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { APP_BASE_HREF, registerLocaleData } from '@angular/common';
import { UserStoreModule } from './shared/store/user-store/user-store.module';

import localeDe from '@angular/common/locales/de';
import { DataContainerDetailModule } from './domain/data-container-detail/data-container-detail.module';
import { StartUpUtil } from './shared/util/StartUpUtil';
import { DataContainerDetailStoreModule } from './domain/data-container-detail/data-container-deatil-store/data-container-detail-store.module';

/* locale and translation settings (TODO: move to own module/) */
export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

registerLocaleData(localeDe);
export let CURRENT_LOCALE = 'de-DE';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    }),
    HttpClientModule,
    // HttpClientInMemoryWebApiModule.forRoot(
    //   InMemoryDataService, { dataEncapsulation: false }
    // ),
    // Custom Modules
    LayoutModule,
    AppRoutingModule,
    ErrorModule,
    MessageStoreModule,
    UserStoreModule,
    DataContainerDetailModule,

    // NgRx Store
    StoreModule.forRoot({}),
    EffectsModule.forRoot([]),
  ],
  providers: [
    { provide: APP_BASE_HREF, useValue: window['base-href'] },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private injector: Injector) {
    StartUpUtil.init(this.injector);
  }
}
