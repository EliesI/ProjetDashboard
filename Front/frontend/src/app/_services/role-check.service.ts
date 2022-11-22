import { Injectable } from '@angular/core';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class RoleCheckService {

  constructor(private tokenStorage: TokenStorageService) { }

  get isAdmin(): boolean {
    if (this.tokenStorage.getToken()) {
      const currentUser = this.tokenStorage.getUser();
      if (currentUser.roles == "ROLE_ADMIN") {
        return true;
      }
    }
    return false;
  }

}
