import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CoreModule} from './core/core.module';
import {InvoicingModule} from './invoicing/invoicing.module';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {DirtyRequestBodyInterceptor} from './core/util/interceptor/dirty-request-body-interceptor';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    InvoicingModule,
    CoreModule
  ],
  providers: [ { provide: HTTP_INTERCEPTORS, useClass: DirtyRequestBodyInterceptor, multi: true } ],
  bootstrap: [AppComponent]
})
export class AppModule { }
