import { NgModule } from '@angular/core';
import {CalendarModule} from 'primeng/primeng';
import {CustomerEditComponent} from './customer-edit/customer-edit.component';
import {FolderEditComponent} from './folder-edit/folder-edit.component';
import { FolderListComponent } from './folder-list/folder-list.component';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { HonoraryEditComponent } from './honorary-edit/honorary-edit.component';
import { InvoiceEditComponent } from './invoice-edit/invoice-edit.component';
import { InvoiceListComponent } from './invoice-list/invoice-list.component';
import { ReceptionEditComponent } from './reception-edit/reception-edit.component';
import { ReceptionListComponent } from './reception-list/reception-list.component';
import { TicketEditComponent } from './ticket-edit/ticket-edit.component';
import { TicketListComponent } from './ticket-list/ticket-list.component';
import { TicketSearchComponent } from './ticket-search/ticket-search.component';
import { DebtorListComponent } from './debtor-list/debtor-list.component';
import { DebtorListItemComponent } from './debtor-list-item/debtor-list-item.component';
import { HonoraryListItemComponent } from './honorary-list-item/honorary-list-item.component';
import { InvoiceListItemComponent } from './invoice-list-item/invoice-list-item.component';
import { TicketListItemComponent } from './ticket-list-item/ticket-list-item.component';
import { RequestEditComponent } from './request-edit/request-edit.component';
import { PaymentListComponent } from './payment-list/payment-list.component';
import { PaymentListItemComponent } from './payment-list-item/payment-list-item.component';
import { ReceptionListItemComponent } from './reception-list-item/reception-list-item.component';

@NgModule({
  imports: [
    CalendarModule
  ],
  declarations: [
  HonoraryEditComponent,
  InvoiceEditComponent,
  InvoiceListComponent,
  ReceptionEditComponent,
  ReceptionListComponent,
  TicketEditComponent,
  TicketListComponent,
  TicketSearchComponent,
  DebtorListComponent,
  DebtorListItemComponent,
  HonoraryListItemComponent,
  InvoiceListItemComponent,
  TicketListItemComponent,
  RequestEditComponent,
  PaymentListComponent,
  PaymentListItemComponent,
  ReceptionListItemComponent],
  exports: [
  ]
})
export class InvoicingComponentsModule { }
