import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { DeliveryService } from '../service/delivery.service';
import { ModalService } from '../service/modal.service';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  products: any[] = [];

  constructor(private deliveryService: DeliveryService, private modalService: ModalService, private productService: ProductService) {
  }

  ngOnInit(): void {
    this.productService.getAllProducts().subscribe(products => this.products = products);
  }

  onProductDeliveryDataChanged(product: any, deliveryData: any) {
    this.deliveryService.getDeliveryTime(product.id, deliveryData.quantity, deliveryData.expressDelivery).subscribe(response => {
      product.deliveryDate = response;
    });
  }

  onEditProduct(product: any) {
    this.modalService.showEditModal(product);
  }

  onAddProduct() {
    this.modalService.showCreateModal();
  }

  saveProduct(product: any) {
    if (product.id) {
      this.productService.updateProduct(product.id, product).subscribe(
        updatedProduct => this.onProductSaved(updatedProduct),
        errorResponse => this.onProductError(product, errorResponse)
      );
    } else {
      this.productService.createProduct(product).subscribe(
        newProduct => this.onProductSaved(newProduct),
        errorResponse => this.onProductError(product, errorResponse)
      );
    }
  }

  onProductSaved(product: any) {
    const index = this.products.findIndex(existingProduct => existingProduct.id === product.id);

    if (index >= 0) {
      this.products.splice(index, 1, product);
    } else {
      this.products.push(product);
    }

    this.modalService.closeModal();
  }

  onProductError(product: any, response: any) {
    let errorMessage = 'An error occurred, please try again';

    if (response.error && response.error.errorMessage) {
      errorMessage = response.error.errorMessage;
    }

    product.error = errorMessage;
  }
}
