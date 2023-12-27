import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CKEditorModule} from '@ckeditor/ckeditor5-angular';
import {NgxSkeletonLoaderModule} from 'ngx-skeleton-loader';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {NgApexchartsModule} from 'ng-apexcharts';
import { DepotEmployeeComponent } from './depot_employee.component';
import { NavSidebarComponent } from './depot_employee-sidebar/nav-sidebar.component';
import { AdminNavbarComponent } from './navbar/navbar.component';
import { TransactionPakageListComponent } from './transaction-pakage-list/transaction-pakagelist.component';
import { AdminFooterComponent } from './admin-footer/admin-footer.component';
import { DepotPakageListComponent } from './depot-pakage-list/depot-pakage-list.component';
import { AddTransactionPakageComponent } from './add-transaction-pakage/add-transaction-pakage.component';
@NgModule({
  declarations: [DepotEmployeeComponent, NavSidebarComponent, AdminNavbarComponent, TransactionPakageListComponent, AdminFooterComponent,DepotPakageListComponent],
    imports: [
        CommonModule,
        RouterModule,
        ScrollingModule,
        SharedModule,
        FormsModule,
        ReactiveFormsModule,
        CKEditorModule,
        NgxSkeletonLoaderModule,
        DragDropModule,
        NgApexchartsModule,

    ]
})
export class DepotEmployeeModule {
}
