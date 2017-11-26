import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {InvoicingComponentsModule} from "./invoicing-components.module";
import {HonoraryListComponent} from "./honorary-list/honorary-list.component";

const routes: Routes = [
  {
    path: 'honorary',
    component: HonoraryListComponent
  }
];

@NgModule({
  imports: [
    InvoicingComponentsModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule]
})
export class InvoicingRoutingModule {
}
