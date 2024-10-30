import { Component } from '@angular/core';
import { AgentService} from '../../service/Agent.service'
import { NzMessageService } from 'ng-zorro-antd/message'
@Component({
  selector: 'app-agent-dashboard',
  templateUrl: './agent-dashboard.component.html',
  styleUrl: './agent-dashboard.component.scss'
})
export class AgentDashboardComponent {
  cars: any[] = []

  constructor(
    private AgentService: AgentService,
    private message: NzMessageService
  ) {}
  ngOnInit() {
    this.getAllCars()
  }
  getAllCars() {
    this.AgentService.getAllCars().subscribe(res => {
      res.forEach((car: any) => {
        car.processedImage = `data:image/jpeg;base64,${car.returnedImage}`
        this.cars.push(car)
      })
    })
  }

  deleteCar(id: number) {
    this.AgentService.deleteCar(id).subscribe(res => {
      this.cars = this.cars.filter(car => car.id !== id)

      this.message.success('Car deleted successfully', { nzDuration: 3000 })
    })
  }

}
