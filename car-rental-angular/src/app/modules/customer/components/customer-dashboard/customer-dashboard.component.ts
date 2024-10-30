import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-customer-dashboard',
  templateUrl: './customer-dashboard.component.html',
  styleUrls: ['./customer-dashboard.component.scss']  // Notez le 's' à la fin pour être cohérent avec l'extension du fichier CSS
})
export class CustomerDashboardComponent implements OnInit {
  cars: any[] = [];

  constructor(private service: CustomerService) {}

  ngOnInit() {
    this.getAllCars();
  }

  getAllCars() {
    this.service.getAllCars().subscribe(
      res => {
        console.log('Cars received:', res);  // Ajoutez cette ligne pour vérifier la réponse
        this.cars = res.map((car: any) => {
          car.processedImage = `data:image/jpeg;base64,${car.returnedImage}`;
          return car;
        });
      },
      error => {
        console.error('Error fetching cars:', error);  // Ajoutez cette ligne pour voir les erreurs
      }
    );
  }
}
