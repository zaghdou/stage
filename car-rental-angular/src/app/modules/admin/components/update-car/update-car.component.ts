import { Component } from '@angular/core'
import { AdminService } from '../../services/admin.service'
import { ActivatedRoute, Router } from '@angular/router'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { NzMessageService } from 'ng-zorro-antd/message'

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrl: './update-car.component.scss'
})
export class UpdateCarComponent {
  constructor(
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router
  ) {}

  isSpinning: boolean = false
  carId: number = this.activatedRoute.snapshot.params['id']
  imgChanged: boolean = false
  selectedFile: any = null
  imagePreview: string | ArrayBuffer | null = null
  existingImage: string | null = null
  updateForm!: FormGroup // ! is used to tell TypeScript that this variable will be initialized later
  listOfOption: Array<{ label: string; value: string }> = []
 
  listOfType = ['Appartement ', 'Maison', 'Villa', 'Bureau']
  listOfColor = ['Red', 'Blue', 'Brown', 'Green']
  

  ngOnInit() {
    this.updateForm = this.fb.group({
      Location: [null, Validators.required],
      Title: [null, Validators.required],
      type: [null, Validators.required],
      color: [null, Validators.required],
      price: [null, Validators.required],
      description: [null, Validators.required],
    })

    this.getCarById()
  }

  getCarById() {
    this.isSpinning = true

    this.adminService.getCarById(this.carId).subscribe(res => {
      this.isSpinning = false

      const carDto = res
      this.existingImage = `data:image/jpeg;base64,${carDto.returnedImage}`
      this.updateForm.patchValue(carDto)
    })
  }

  updateCar() {
    this.isSpinning = true

    const formData: FormData = new FormData()
    if (this.imgChanged && this.selectedFile) {
      formData.append('image', this.selectedFile as Blob)
    }

  
    formData.append('Title', this.updateForm.value.Title)
    formData.append('location', this.updateForm.value.Location)
    formData.append('type', this.updateForm.value.type)
    formData.append('color', this.updateForm.value.color)
    formData.append('description', this.updateForm.value.description)
    formData.append('price', this.updateForm.value.price)
    

    this.adminService.updateCar(this.carId, formData).subscribe(
      res => {
        this.message.success('Car updated successfully', { nzDuration: 3000 })
        this.isSpinning = false
        this.router.navigateByUrl('/admin/dashboard')
      },
      error => {
        this.message.error('Error updating car', { nzDuration: 3000 })
        console.log(error)
      }
    )
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0]
    this.imgChanged = true
    this.existingImage = null
    this.previewImage()
  }

  previewImage() {
    const reader = new FileReader()
    reader.onload = () => {
      this.imagePreview = reader.result
    }
    reader.readAsDataURL(this.selectedFile)
  }
}
