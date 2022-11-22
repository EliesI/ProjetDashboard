import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../_services/auth.service";
import {UserService} from "../../_services/user.service";
import {TokenStorageService} from "../../_services/token-storage.service";
import {ActivatedRoute, Route, Router, Routes} from '@angular/router';
import {AppConstants} from "../../common/app.constants";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  currentUser: any;
  googleURL = AppConstants.GOOGLE_AUTH_URL;
  githubURL = AppConstants.GITHUB_AUTH_URL;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private route: ActivatedRoute, private userService: UserService, public router: Router) {}

  ngOnInit(): void {
    const token: string = <string>this.route.snapshot.queryParamMap.get('token');
    const error: string = <string>this.route.snapshot.queryParamMap.get('error');
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.tokenStorage.getUser();
    }
    else if(token){
      this.tokenStorage.saveToken(token);
      this.userService.getCurrentUser().subscribe(
        data => {
          this.login(data);
        },
        err => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      );
    }
    else if(error){
      this.errorMessage = error;
      this.isLoginFailed = true;
    }
  }

  onSubmit(): void {
    this.authService.login(this.form).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.login(data.user);
        if(data.user.roles=="ROLE_ADMIN"){
          this.router.navigate(['admin']);
        }else{
          this.router.navigate(['home']);
        }
        
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  login(user: any): void {
    this.tokenStorage.saveUser(user);
    this.isLoginFailed = false;
    this.isLoggedIn = true;
    this.currentUser = this.tokenStorage.getUser();
    this.router.navigate(['home']);
    window.location.reload()
  }

}
