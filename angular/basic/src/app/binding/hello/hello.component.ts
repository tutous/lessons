import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { HelloEvent } from "./hello.event";
import { BehaviorSubject } from "rxjs";

@Component({
  selector: "app-hello",
  templateUrl: "./hello.component.html",
  styleUrls: ["./hello.component.css"]
})
export class HelloComponent implements OnInit {
  @Input("greet")
  greet: string;

  @Output("viewed")
  viewedEvent: EventEmitter<HelloEvent> = new EventEmitter();

  private viewed = false;

  helloClasses = new BehaviorSubject({
    hello: !this.viewed,
    "hello-viewed": this.viewed
  });

  constructor() {}

  ngOnInit() {}

  onViewed(event: Event) {
    this.viewedEvent.emit({ greet: this.greet });
    this.viewed = true;
    this.helloClasses.next({
      hello: !this.viewed,
      "hello-viewed": this.viewed
    });
  }
}
