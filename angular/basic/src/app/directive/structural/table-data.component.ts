import { Component, OnInit, Input } from "@angular/core";
import { Person } from "./person";

@Component({
  selector: "[table-data]",
  template: `
    {{ value }}
  `,
  styles: [``]
})
export class DataComponent implements OnInit {
  @Input()
  value: string;

  @Input()
  person: Person;

  @Input()
  property: string;

  constructor() { }

  ngOnInit() {
    if (this.property == "foreName") {
      this.value = this.person.foreName;
    }
    else if (this.property == "name") {
      this.value = this.person.name;
    }
    else if (this.property == "city") {
      this.value = this.person.city;
    }
    else if (this.value.length > 0) {
      console.log(this.value);
    }
  }
}
