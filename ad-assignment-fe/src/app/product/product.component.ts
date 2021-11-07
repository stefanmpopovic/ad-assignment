import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: '[product-row]',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  @Input() product: any = {};

  @Output() deliveryDataChanged = new EventEmitter<any>();

  @Output() editProduct = new EventEmitter<any>();

  quantityValidationError: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  onDeliveryDataChange() {
    this.quantityValidationError = false;
    const quantity = Number(this.product.quantity);
    const expressDelivery = this.product.expressDelivery;

    if (isNaN(quantity) || quantity <= 0) {
      this.quantityValidationError = true;
      return;
    }

    this.deliveryDataChanged.emit({
      quantity: quantity,
      expressDelivery: expressDelivery
    });
  }

  onEditProduct() {
    this.editProduct.emit();
  }
}
