initial
	ng new table --style=css
	npm install --save @angular/material @angular/cdk @angular/animations
	ng add @angular/material
	npm install rxjs
	ng update rxjs

styles.css
	@import "~@angular/material/prebuilt-themes/indigo-pink.css";

index.html
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">	

	app.module.ts (initial)
	import { BrowserModule } from '@angular/platform-browser';
	import { NgModule } from '@angular/core';
	import { AppComponent } from './app.component';
	// material
	import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
	import { MatInputModule } from '@angular/material/input';
	import { MatPaginatorModule } from '@angular/material/paginator';
	import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';	
	import { MatSortModule } from '@angular/material/sort';
	import { MatTableModule } from '@angular/material/table';	

	@NgModule({
  	declarations: [
    	AppComponent
  	],
  	imports: [
    	BrowserModule,
    	BrowserAnimationsModule,
		// material
		MatInputModule,
		MatTableModule,
		MatPaginatorModule,
		MatSortModule,
		MatProgressSpinnerModule,
		],
		providers: [],
		bootstrap: [AppComponent]
		})
	export class AppModule { }
		
		
	