import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { ErrorViewComponent } from './error/error-view/error-view.component';

const routes: Routes = [
  { path: 'error', component: ErrorViewComponent },
  { path: 'dc', loadChildren: () => import('./domain/data-container/data-container.module')
    .then(m => m.DataContainerModule) },
  { path: 'dc/detail/:id', loadChildren: () => import('./domain/data-container-detail/data-container-detail.module')
    .then(m => m.DataContainerDetailModule) },
  {
    path: '',
    redirectTo: 'dc',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(
    routes,
    {
      enableTracing: false,
      preloadingStrategy: PreloadAllModules // Preloading needed, because lazy laoding modules does'nt work on Tomcat.
    }
  )],
  exports: [RouterModule]
})
export class AppRoutingModule { }
