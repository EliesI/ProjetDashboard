import { TestBed } from '@angular/core/testing';

import { RoleCheckService } from './role-check.service';

describe('RoleCheckService', () => {
  let service: RoleCheckService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoleCheckService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
