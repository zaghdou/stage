import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms'
import { AgentService} from '../../service/Agent.service'

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.scss'
})
export class SearchComponent {
  searchCarForm!: FormGroup
  listOfOption: Array<{ label: string; value: string }> = []
 listOfType = ['Appartement', 'Maison', 'Villa', 'Bureau']
  listOfColor = ['Red', 'Blue', 'Brown', 'Green']
  
  isSpinning = false
  cars: any[] = []

  constructor(
    private fb: FormBuilder,
    private service: AgentService
  ) {
    this.searchCarForm = this.fb.group({
      brand: [null],
      type: [null],
      transmission: [null],
      color: [null]
    })
  }

  searchCar() {
    this.isSpinning = true
    this.service.searchCar(this.searchCarForm.value).subscribe(
      res => {
        this.isSpinning = false

        const carDtoList = res.carDtoList

        carDtoList.forEach((car: any) => {
          car.processedImage = `data:image/jpeg;base64,${car.returnedImage}`
          this.cars.push(car)
        })
      },
      err => {
        this.isSpinning = false
      }
    )
  }
}
