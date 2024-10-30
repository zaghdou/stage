// agent-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgentDashboardComponent } from './components/agent-dashboard/agent-dashboard.component';
import { SearchComponent } from './components/search/search.component';
import { UpdateComponent } from './components/update/update.component';

const routes: Routes = [
  { path: 'dashboard', component: AgentDashboardComponent },
  { path: 'car/:id', component: UpdateComponent },
  { path: 'search', component: SearchComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' } // Redirection par défaut vers le tableau de bord de l'agent
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AgentRoutingModule {}
