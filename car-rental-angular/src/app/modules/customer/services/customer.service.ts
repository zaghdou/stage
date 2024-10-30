import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/components/services/storage/storage.service';

const BASIC_URL = 'http://localhost:8081';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  constructor(private http: HttpClient) {}

  getAllCars(): Observable<any> {
    return this.http.get(`${BASIC_URL}/api/customer/cars`, {
      headers: this.createAuthorizationHeader()
    });
  }

  getCarById(id: number): Observable<any> {
    return this.http.get(`${BASIC_URL}/api/customer/car/${id}`, {
      headers: this.createAuthorizationHeader()
    });
  }

  bookACar(bookACar: any): Observable<any> {
    return this.http.post(`${BASIC_URL}/api/customer/car/book`, bookACar, {
      headers: this.createAuthorizationHeader()
    });
  }

  getBookingsByUserId(): Observable<any> {
    const userId = StorageService.getUserId()
      ? Number(StorageService.getUserId())
      : 0;

    return this.http.get(`${BASIC_URL}/api/customer/car/bookings/${userId}`, {
      headers: this.createAuthorizationHeader()
    });
  }

  rateCar(carId: number, ratingDto: any): Observable<any> {
    return this.http.post(`${BASIC_URL}/api/customer/car/${carId}/ratings`, ratingDto, {
      headers: this.createAuthorizationHeader()
    });
  }

  getRatingsByCarId(carId: number): Observable<any> {
    return this.http.get(`${BASIC_URL}/api/customer/car/${carId}/ratings`, {
      headers: this.createAuthorizationHeader()
    });
  }

  getAverageRating(carId: number): Observable<any> {
    return this.http.get(`${BASIC_URL}/api/customer/car/${carId}/average-rating`, {
      headers: this.createAuthorizationHeader()
    });
}


  getTotalComments(carId: number): Observable<any> {
    return this.http.get(`${BASIC_URL}/api/customer/car/${carId}/total-comments`, {
      headers: this.createAuthorizationHeader()
    });
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = StorageService.getToken();
    return new HttpHeaders().set(
      'Authorization',
      `Bearer ${token}`
    );
  }
}
