import { Injectable } from '@angular/core';

const PREFIX_TOKEN_KEY = 'car_rental';
const TOKEN = `${PREFIX_TOKEN_KEY}.token`;
const USER = `${PREFIX_TOKEN_KEY}.user`;

interface User {
  id: string;
  userRole: string;
}

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() {}

  static saveToken(token: string): void {
    localStorage.setItem(TOKEN, token);
  }

  static saveUser(user: User): void {
    localStorage.setItem(USER, JSON.stringify(user));
  }

  static getToken(): string | null {
    return localStorage.getItem(TOKEN);
  }

  static getUser(): User | null {
    const user = localStorage.getItem(USER);
    return user ? JSON.parse(user) : null;
  }

  static getUserId(): string {
    const user = this.getUser();
    return user ? user.id : '';
  }

  static getUserRole(): string {
    const user = this.getUser();
    return user ? user.userRole : '';
  }

  static isAdminLoggedIn(): boolean {
    return this.getToken() !== null && this.getUserRole() === 'ADMIN';
  }

  static isCustomerLoggedIn(): boolean {
    return this.getToken() !== null && this.getUserRole() === 'CUSTOMER';
  }

  static isAgentLoggedIn(): boolean {
    return this.getToken() !== null && this.getUserRole() === 'AGENT_IMMOBILIER';
  }

  static logout(): void {
    localStorage.removeItem(TOKEN);
    localStorage.removeItem(USER);
  }
}
