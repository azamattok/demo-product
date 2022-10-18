import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductListComponent} from './pages/product-list/product.list.component';
import {ProductEditComponent} from './pages/product-edit/product-edit.component';

const routes: Routes = [
  {path: '', component: ProductListComponent},
  {path: 'product/:id', component: ProductEditComponent},
  {
    path: 'product/:id/edit',
    component: ProductEditComponent
  },
  {
    path: 'product/:id/new',
    component: ProductEditComponent
  },

];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)//{onSameUrlNavigation: 'reload'}
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
