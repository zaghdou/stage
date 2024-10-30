import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerDashboardComponent } from './components/customer-dashboard/customer-dashboard.component';
import { BookCarComponent } from './components/book-car/book-car.component';
import { MyBookingsComponent } from './components/my-bookings/my-bookings.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NzButtonModule } from 'ng-zorro-antd/button'; // Import correct for buttons
import { NzMessageModule } from 'ng-zorro-antd/message'; // Import correct for messages
import { NzSpinModule } from 'ng-zorro-antd/spin'; // Import for nz-spin

@NgModule({
  declarations: [
    CustomerDashboardComponent,
    BookCarComponent,
    MyBookingsComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NzButtonModule,
    NzMessageModule,
    NzSpinModule // Add NzSpinModule here
  ]
})
export class CustomerModule {}
