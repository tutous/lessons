import { Component, OnInit } from "@angular/core";
import { HelloEvent } from "./hello/hello.event";

@Component({
  selector: "app-binding",
  templateUrl: "./binding.component.html",
  styleUrls: ["./binding.component.css"]
})
export class BindingComponent implements OnInit {

  message_a = 'hello world';

  message_b: string;
  viewed_b = false;

  message_c: string;
  viewed_c = false;

  constructor() {}

  ngOnInit() {}

  onClick(message: string) {
    console.log(message);
    this.message_b = message;
  }
  onViewed(event: HelloEvent, lesson: string) {
    // log event
    console.log(event.greet);
    // lesson b
    if (!this.viewed_b && lesson == "b") {
      alert("lesson b " + event.greet);
      this.viewed_b = true;
    }
    // lesson c
    if (!this.viewed_c && lesson == "c") {
      alert("lesson c " + event.greet);
      this.viewed_c = true;
    }
  }
}
