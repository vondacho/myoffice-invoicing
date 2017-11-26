import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Honorary} from '../../model/honorary';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class HonoraryClient {

  constructor(private http: HttpClient) { }

  findAllByFolder(): Observable<Array<Honorary>> {
    return this.http.get('/invoicing/api/v1/honoraries');
  }
}
