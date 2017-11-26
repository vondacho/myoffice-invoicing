import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {JsonCleaner} from '../cleaner/json-cleaner';

export class DirtyRequestBodyInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req.clone({ body: JsonCleaner.clean(req.body) }));
  }
}
