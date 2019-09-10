import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { BindingComponent } from "./binding/binding.component";
import { StructuralComponent } from "./directive/structural/structural.component";
import { AppComponent } from './app.component';

const routes: Routes = [
  { path: "binding", component: BindingComponent },
  { path: "directive/structural", component: StructuralComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
