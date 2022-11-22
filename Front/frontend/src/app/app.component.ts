import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { TokenStorageService } from './_services/token-storage.service';
import { WidgetService } from './_services/widget.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  private roles: string[] = [];
  isLoggedIn = false;
  title = 'frontend';
  username: string = "";
  currentId: any = 1;
  createWidgetSubcrForm: FormGroup;
  isAdmin = false;

  constructor(private tokenStorageService: TokenStorageService, public fb: FormBuilder, private widgetService: WidgetService, public router: Router) {
    this.createWidgetSubcrForm = this.fb.group({
      param_1: [''],
      param_2: [''],
      refresh_rate: [''],
      user_id: [],
      widget_id: [''],
    });
  }

  ngOnInit(): void {
    if (this.tokenStorageService.getToken()) {
      this.isLoggedIn = true;
    }

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      this.username = user.displayName;
      this.currentId = user.id;
      if(user.roles == "ROLE_ADMIN"){
        this.isAdmin = true;
      }
    }

  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

  addWidget(widget_id: string): void {
    this.createWidgetSubcrForm.value.widget_id = widget_id;
    this.createWidgetSubcrForm.value.user_id = this.currentId;
    this.widgetService.createWidgetSubcr(this.createWidgetSubcrForm.value).subscribe({
      next: (res: any) => {
        if (res) {
          window.location.reload()
        }
      },
      error: (err: any) => {
        window.alert('error')
      }
    });
  }

}
