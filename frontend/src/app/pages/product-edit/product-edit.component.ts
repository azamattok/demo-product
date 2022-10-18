import {AfterContentChecked, Component, OnInit} from '@angular/core';
import {ProductService} from '../../services/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Product} from '../../models/Product';
import {Subscription} from 'rxjs';
import {FileService} from '../../services/file.service';

//import {NgxUiLoaderService} from 'ngx-ui-loader';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit, AfterContentChecked {
  subscriptions: Subscription = new Subscription();
  imageUrl: any;
  selectedImage: any;
  isInvalidForm: boolean;
  product = new Product();

  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router,
              // private ngxLoader: NgxUiLoaderService,
              public fileService: FileService) {
  }

  productId: number;
  isEdit = false;

  ngOnInit() {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.productId) {
      this.isEdit = true;
      this.productService.getDetail(this.productId).subscribe(prod => this.product = prod);
    }

  }

  update() {
    this.productService.update(this.product).subscribe(prod => {
        if (!prod) {
          throw new Error();
        }
        this.router.navigate(['']);
      },
      err => {
      });

  }

  onSubmit() {
    this.subscriptions.add(this.fileService.uploadData(this.selectedImage).subscribe(data => {
      // this.ngxLoader.startBackground();
      if (data && data.uuid) {
        this.product.photo = 'https://storage-api-dev.jurta.kz/test/' + data.uuid + '.jpg';
        //   this.ngxLoader.stopBackground();
        this.productService.addPhoto(this.product.uid, this.product.photo);
      }
    }));
    if (this.productId) {
      this.update();
    } else {
      this.add();
    }
  }

  add() {
    this.productService.create(this.product).subscribe(prod => {
        if (!prod) {
          throw new Error;
        }
        this.router.navigate(['/']);
      },
      e => {
      });
  }

  handleFile(event) {
    const reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    this.selectedImage = event.target.files[0];
    reader.onload = (() => {
      this.imageUrl = reader.result;
    });
  }

  deleteFile() {
    this.imageUrl = null;
    this.selectedImage = null;
  }

  ngAfterContentChecked(): void {
    console.log(this.product);
  }
}
