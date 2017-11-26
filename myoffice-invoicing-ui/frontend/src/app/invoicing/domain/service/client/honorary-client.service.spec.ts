import { TestBed, inject } from '@angular/core/testing';

import {HonoraryClient} from './honorary-client.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('CustomerClient', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [HonoraryClient]
    });
  });

  it('should be created', inject([HonoraryClient], (service: HonoraryClient) => {
    expect(service).toBeTruthy();
  }));
});
