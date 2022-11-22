import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {LoginComponent} from "./Component/login/login.component";
import {HomeComponent} from "./Component/home/home.component";
import { ProfileComponent } from './Component/profile/profile.component';
import { RegisterComponent } from './Component/register/register.component';
import {HttpClientModule} from "@angular/common/http";
import { authInterceptorProviders } from './auth.interceptor';
import { AdminComponent } from './Component/admin/admin.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ProfileComponent,
    RegisterComponent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]

})
export class AppModule { }
