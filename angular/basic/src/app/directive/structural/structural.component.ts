import { Component, OnInit } from "@angular/core";
import { PERSONS } from "./persons";
import { Person } from "./person";

@Component({
  selector: "app-structural",
  templateUrl: "./structural.component.html",
  styleUrls: ["./structural.component.css"]
})
export class StructuralComponent implements OnInit {
  persons: Person[];

  constructor() {
    this.persons = PERSONS;
  }

  ngOnInit() {}
}
