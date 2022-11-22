import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProfileService } from 'src/app/_services/profile.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  editUserForm: FormGroup;
  currentUser: any = {};
  currentId!: string;
  constructor(public fb: FormBuilder,
    public ProfileService: ProfileService,
    public tokenStorageService: TokenStorageService) {
    this.editUserForm = this.fb.group({
      id: [''],
      displayName: [''],
      email: [''],
      password: [''],
    });
  }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    if (this.tokenStorageService.getToken()) {
      this.currentId = user.id;
    }
    this.loadUserProfile();
  }

  loadUserProfile() {
    return this.ProfileService.getUserProfile(this.currentId).subscribe((data: {}) => {
      this.currentUser = data;
    })
  }

  editUser() {
    if (this.editUserForm.value.id == 1) {
      window.alert('Vous ne pouvez pas modifier un admin')
    } else {
      this.ProfileService.editUser(this.editUserForm.value.id, this.editUserForm.value).subscribe({
        next: (res: any) => {
          window.location.reload()
        },
        error: (err: any) => {
          window.alert('Impossible de modifier l\'utilisateur ! Vérifiez vos informations !')
        }
      });
    }
  }

}
