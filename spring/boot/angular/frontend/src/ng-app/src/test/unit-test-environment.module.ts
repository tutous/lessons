import { NgModule } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { HttpClientModule } from '@angular/common/http';
import { CustomComponentsModule } from '../app/shared/custom-components/custom-components.module';
import { AppRoutingModule } from '../app/app-routing.module';
import { ErrorModule } from '../app/error/error.module';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { AppModule } from 'src/app/app.module';

@NgModule({
  imports: [
    AppModule
  ],
  exports: [
    TranslateModule,
    CustomComponentsModule,
    AppRoutingModule,
    ErrorModule,
    HttpClientModule,
    StoreModule,
    EffectsModule
  ]
})
export class UnitTestEnvironmentModule {
  constructor(private translateService: TranslateService) {
    this.translateService.setDefaultLang('de');
    this.translateService.use('de');
  }
}
