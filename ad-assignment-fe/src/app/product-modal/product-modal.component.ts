import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';

import { ModalService } from '../service/modal.service';

@Component({
  selector: 'product-modal',
  templateUrl: './product-modal.component.html',
  styleUrls: ['./product-modal.component.scss']
})
export class ProductModalComponent implements OnInit, OnDestroy {

  @Output() onSave = new EventEmitter<any>();

  displayStyle: string = 'none';

  currentProduct: any = {};

  subscriptions: any[] = [];

  constructor(private modalService: ModalService) { }

  ngOnInit(): void {
    this.subscriptions.push(this.modalService.subscribe((product: any) => {
      if (product) {
        // A better way to copy should be used here, for example lodash
        this.currentProduct = JSON.parse(JSON.stringify(product));
      } else {
        this.currentProduct = {};
      }

      this.showModal();
    }));

    this.subscriptions.push(this.modalService.subscribeToClose(() => this.closeModal()));
  }

  ngOnDestroy(): void {
    for (const subscription of this.subscriptions) {
      subscription.unsubscribe();
    }
  }

  removeDeliveryTime(index: number) {
    this.currentProduct.deliveryTimes.splice(index, 1);
  }

  addDeliveryTime() {
    if (!this.currentProduct.deliveryTimes) {
      this.currentProduct.deliveryTimes = [];
    }

    this.currentProduct.deliveryTimes.push({});
  }

  sortDeliveryTimes() {
    this.currentProduct.deliveryTimes.sort((first: any, second: any) => Number(first.maxQuantity) > Number(second.maxQuantity));
  }

  saveProduct() {
    this.onSave.emit(this.currentProduct);
  }

  closeModal() {
    this.displayStyle = 'none';
  }

  showModal() {
    this.displayStyle = 'block';
  }
}
