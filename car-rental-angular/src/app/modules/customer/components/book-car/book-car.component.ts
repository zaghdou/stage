import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerService } from '../../services/customer.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from '../../../../auth/components/services/storage/storage.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrls: ['./book-car.component.scss']
})
export class BookCarComponent implements OnInit {
  carId: number = this.activeRoute.snapshot.params['id'];
  car: any;
  validateForm!: FormGroup;
  ratingForm!: FormGroup;
  isSpinning: boolean = false;
  ratings: any[] = [];
  averageRating: number = 0;
  totalComments: number = 0;

  constructor(
    private service: CustomerService,
    private activeRoute: ActivatedRoute,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router
  ) {}

  ngOnInit() {
    this.validateForm = this.fb.group({
      toDate: [null, Validators.required],
      fromDate: [null, Validators.required]
    });

    this.ratingForm = this.fb.group({
      rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]],
      comment: [null, Validators.required]
    });

    this.getCarById();
    this.getRatingsByCarId();
    this.getAverageRating();
    this.getTotalComments();
  }

  bookACar() {
    if (this.validateForm.invalid) {
      this.message.error('Please fill in all fields.');
      return;
    }

    this.isSpinning = true;

    const bookACarDto = {
      fromDate: this.validateForm.value.fromDate,
      toDate: this.validateForm.value.toDate,
      userId: StorageService.getUserId(),
      immobilierId: this.carId
    };

    this.service.bookACar(bookACarDto).subscribe(
      res => {
        this.isSpinning = false;
        this.message.success('Car booked successfully');
        this.router.navigateByUrl('/customer/dashboard');
      },
      error => {
        this.isSpinning = false;
        this.message.error('Error occurred while booking the car');
      }
    );
  }

  submitRating() {
    if (this.ratingForm.invalid) {
      this.message.error('Please fill in all fields.');
      return;
    }

    const ratingDto = {
      rating: this.ratingForm.value.rating,
      comment: this.ratingForm.value.comment,
      userId: StorageService.getUserId()  // Ajoutez l'ID utilisateur
    };

    this.service.rateCar(this.carId, ratingDto).subscribe(
      res => {
        this.getRatingsByCarId(); // Refresh ratings after submission
        this.getAverageRating(); // Refresh average rating
        this.getTotalComments(); // Refresh total comments
        this.ratingForm.reset();
        this.message.success('Rating submitted successfully');
      },
      error => {
        this.message.error('Error occurred while submitting the rating');
      }
    );
  }

  private getCarById() {
    this.service.getCarById(this.carId).subscribe(res => {
      this.car = res;
      this.car.processedImage = `data:image/jpeg;base64,${res.returnedImage}`;
    });
  }

  private getRatingsByCarId() {
    this.service.getRatingsByCarId(this.carId).subscribe(res => {
      this.ratings = res;
    });
  }

  private getAverageRating() {
    this.service.getAverageRating(this.carId).subscribe(res => {
      this.averageRating = res;
    });
  }

  private getTotalComments() {
    this.service.getTotalComments(this.carId).subscribe(res => {
      this.totalComments = res;
    });
  }
}
