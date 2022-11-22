import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./Component/login/login.component";
import {AppComponent} from "./app.component";
import {HomeComponent} from "./Component/home/home.component";
import {ProfileComponent} from "./Component/profile/profile.component";
import {RegisterComponent} from "./Component/register/register.component";
import {DragDropModule} from '@angular/cdk/drag-drop';
import { AdminComponent } from './Component/admin/admin.component';
import { RoleGuard } from './_services/role.guard';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'app', component: AppComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'admin', component: AdminComponent, canActivate: [RoleGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule, DragDropModule]
})
export class AppRoutingModule { }
