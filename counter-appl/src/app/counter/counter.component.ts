import { Component, OnInit } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/Rx';

@Component({
  selector: 'app-counter',
  templateUrl: './counter.component.html',
  styleUrls: ['./counter.component.css']
})
export class CounterComponent implements OnInit {
	counter = "0"

  constructor(private _http: Http) { }

  ngOnInit() {
	  Observable.timer(0,5000).subscribe(x => this.refreshData());
  }
  
  private refreshData(): void {
    this._http.get("/click").map((res: Response, index:number) => res.text())
        .subscribe((str: string) => {
            this.counter = str;
        })
}

}
