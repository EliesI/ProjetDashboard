import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { WidgetService } from 'src/app/_services/widget.service';
import { Widget } from 'src/app/_services/widget';
import { Weather } from 'src/app/_services/weather';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  isLoggedIn = false;
  widgets: any = [];
  currentId: any = 1;
  displayWidget: any =[];
  editWidgetSubcrForm: FormGroup;
  currentWidget: any = {};

  constructor(private tokenStorageService: TokenStorageService, private widgetService: WidgetService, public fb: FormBuilder) { 
    this.editWidgetSubcrForm = this.fb.group({
      id:[''],
      param_1: [''],
      param_2: [''],
      refresh_rate: [''],
      user_id: [],
    });
  }

  ngOnInit(): void {

    if (this.tokenStorageService.getToken()) {
      this.isLoggedIn = true;
    }

    if (this.isLoggedIn) {
    const user = this.tokenStorageService.getUser();
      this.currentId = user.id;
    }

      this.getUserWidgets()

  }

  getUserWidgets() {
    this.widgetService.getUserWidgets(this.currentId).subscribe((data: {}) => {
      this.widgets = data;
      this.loadUserWidgets();
    });
  }

  loadUserWidgets() {
    this.widgets.forEach((widget: any) => {
      if (widget.widget_id == 1) {
        this.widgetService.callWeatherWidget(widget.param_1).subscribe((data2: {id:string}) => {
          data2.id=widget.id
          this.displayWidget.push(data2);
        });
      }
      if (widget.widget_id == 2) {
        this.widgetService.callTwitter1Widget(widget.param_1).subscribe((data2: {widget_id: string, id:string}) => {
          data2.widget_id="2";
          data2.id=widget.id
          this.displayWidget.push(data2);
        });
      }
      if (widget.widget_id == 3) {
        this.widgetService.callTwitter2Widget(widget.param_1).subscribe((data3: {widget_id: string, username: string, id:string}) => {
          data3.widget_id="3";
          data3.id=widget.id
          data3.username=widget.param_1
          this.displayWidget.push(data3);
        });
      }
      if (widget.widget_id == 4) {
        this.widgetService.callStarwars1Widget(widget.param_1).subscribe((data2: {widget_id: string, id:string}) => {
          data2.widget_id="4";
          data2.id=widget.id
          this.displayWidget.push(data2);
        });
      }
      if (widget.widget_id == 5) {
        this.widgetService.callStarwars2Widget(widget.param_1).subscribe((data2: {widget_id: string, id:string}) => {
          data2.widget_id="5";
          data2.id=widget.id
          this.displayWidget.push(data2);
        });
      }
      if (widget.widget_id == 6) {
        this.widgetService.callStarwars3Widget(widget.param_1).subscribe((data2: {widget_id: string, id:string}) => {
          data2.widget_id="6";
          data2.id=widget.id
          this.displayWidget.push(data2);
        });
      }
      if (widget.widget_id == 7) {
        this.widgetService.callNewsWidget(widget.param_1,widget.param_2).subscribe((data2: {widget_id: string, id:string}) => {
          data2.widget_id="7";
          data2.id=widget.id
          this.displayWidget.push(data2);
        });
      }
      if (widget.widget_id == 8) {
        this.widgetService.callFun1Widget(widget.param_1).subscribe((data2: {widget_id: string, id:string}) => {
          data2.widget_id="8";
          data2.id=widget.id
          this.displayWidget.push(data2);
        });
      }
      if (widget.widget_id == 9) {
        this.widgetService.callFun2Widget(widget.param_1).subscribe((data2: {widget_id: string, id:string}) => {
          data2.widget_id="9";
          data2.id=widget.id
          this.displayWidget.push(data2);
        });
      }
    });
  }

  deleteWidget(id: string){
    this.widgetService.deleteWidget(id).subscribe({
      next: (res: any) => {
          window.location.reload()
      },
    });
  }

  editWidget(id:string){
    this.widgetService.editWidget(this.editWidgetSubcrForm.value.id, this.editWidgetSubcrForm.value).subscribe({
      next: (res: any) => {
        if (res) {
          window.location.reload()
        }
      },
      error: (err: any) => {
        window.alert('Impossible de modifier le widget. Vérifiez vos informations !')
      }
    });
  }

  loadWidget(id:string){
    this.widgetService.getWidgetById(id).subscribe((data: {}) => {
      this.currentWidget = data;
    });
  }

}
