import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgentRoutingModule } from './agent-routing.module';
import { AgentDashboardComponent } from './components/agent-dashboard/agent-dashboard.component';
import { SearchComponent } from './components/search/search.component';
import { UpdateComponent } from './components/update/update.component';
import { NgZorroImportsModule } from '../../NgZorroImportsModule'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AgentDashboardComponent,
    SearchComponent,
    UpdateComponent
  ],
  imports: [
    CommonModule,
    AgentRoutingModule,
    NgZorroImportsModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class AgentModule { }
