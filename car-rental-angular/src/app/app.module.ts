import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'

import { AppRoutingModule } from './app-routing.module'
import { AppComponent } from './app.component'
import { LoginComponent } from './auth/components/login/login.component'
import { SignupComponent } from './auth/components/signup/signup.component'
import { ReactiveFormsModule } from '@angular/forms'
import { HttpClientModule } from '@angular/common/http'
import { NzMessageModule } from 'ng-zorro-antd/message'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { NgZorroImportsModule } from './NgZorroImportsModule';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { NzButtonModule } from 'ng-zorro-antd/button';


@NgModule({
  declarations: [AppComponent, LoginComponent, SignupComponent, ForgotPasswordComponent, ResetPasswordComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NzMessageModule,
    BrowserAnimationsModule,
    NgZorroImportsModule,
    NzButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
