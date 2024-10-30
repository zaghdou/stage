import { Injectable } from '@angular/core';
import { StorageService } from '../../../auth/components/services/storage/storage.service'
import { Observable } from 'rxjs'
import { HttpClient, HttpHeaders } from '@angular/common/http'


const BASIC_URL = 'http://localhost:8081'

@Injectable({
  providedIn: 'root'
})
export class AgentService {
  constructor(private http: HttpClient) {}

  getAllCars(): Observable<any> {
    return this.http.get(`${BASIC_URL}/api/agentimmob/cars`, {
      headers: this.createAuthorizationHeader()
    })
  }

 deleteCar(id: number): Observable<any> {
    return this.http.delete(`${BASIC_URL}/api/agentimmob/car/${id}`, {
      headers: this.createAuthorizationHeader()
    })
  }
  updateCar(carId: number, carDto: any): Observable<any> {
    return this.http.put(`${BASIC_URL}/api/agentimmob/car/${carId}`, carDto, {
      headers: this.createAuthorizationHeader()
    })
  }

  getCarById(id: number): Observable<any> {
    return this.http.get(`${BASIC_URL}/api/agentimmob/car/${id}`, {
      headers: this.createAuthorizationHeader()
    })
  }

  bookACar(bookACar: any): Observable<any> {
    return this.http.post(`${BASIC_URL}/api/agentimmob/car/book`, bookACar, {
      headers: this.createAuthorizationHeader()
    })
  }

  getBookingsByUserId(): Observable<any> {
    const userId = StorageService.getUserId()
      ? Number(StorageService.getUserId())
      : 0

    return this.http.get(`${BASIC_URL}/api/agentimmob/car/bookings/${userId}`, {
      headers: this.createAuthorizationHeader()
    })
  }
  searchCar(searchDto: any): Observable<any> {
    return this.http.post(`${BASIC_URL}/api/agentimmob/car/search`, searchDto, {
      headers: this.createAuthorizationHeader()
    })
  }

  private createAuthorizationHeader(): HttpHeaders {
    let authHeaders: HttpHeaders = new HttpHeaders()

    return authHeaders.set(
      'Authorization',
      `Bearer ${StorageService.getToken()}`
    )
  }
  
}
