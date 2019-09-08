import { Component, OnInit } from "@angular/core";
import { HelloEvent } from "./hello/hello.event";

@Component({
  selector: "app-binding",
  templateUrl: "./binding.component.html",
  styleUrls: ["./binding.component.css"]
})
export class BindingComponent implements OnInit {
  message_a: string;
  message_b: string;
  viewed_a = false;
  viewed_b = false;


  constructor() {}

  ngOnInit() {}
  onClick(message: string) {
    console.log(message);
    this.message_a = message;
  }
  onViewed(event: HelloEvent, messageAorB: string) {
    console.log(event.greet);
    if (!this.viewed_a && messageAorB == "a") {
      alert(event.greet);
      this.viewed_a = true;
    }
    if (!this.viewed_b && messageAorB == "b") {
      alert(event.greet);
      this.viewed_b = true;
    }
  }
}
