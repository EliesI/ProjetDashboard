import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  users: any = [];
  patchUserForm: FormGroup;
  currentUser: any = {};

  constructor(private userService: UserService, public fb: FormBuilder) {
    this.patchUserForm = this.fb.group({
      id: [''],
      displayName: [''],
      email: [''],
      password: [''],
    });
  }

  ngOnInit(): void {

    this.loadUsers();

  }

  loadUsers() {
    return this.userService.getUsers().subscribe((data: {}) => {
      this.users = data;
    });
  }

  public patchUsers() {
    if(this.patchUserForm.value.id == 1){
      window.alert('Vous ne pouvez pas modifier l\'admin !')
    }else{
      this.userService.editUser(this.patchUserForm.value.id,this.patchUserForm.value).subscribe({
        next: (res: any) => {
          window.location.reload()
        },
        error: (err: any) => {
          window.alert('Impossible de modifier l\'utilisateur ! Vérifiez vos informations !');
        }
      });
    }
  }

  public deleteUsers(id: number) {
    if(id == 1){
      window.alert('Vous ne pouvez pas supprimer l\'admin !')
    }else{
      this.userService.deleteUsers(id);
    window.location.reload();
    }
  }

  loadCurrentUser(userId: string) {
    this.userService.getUserById(userId).subscribe((data: {}) => {
      this.currentUser = data;
    });
  }
}
