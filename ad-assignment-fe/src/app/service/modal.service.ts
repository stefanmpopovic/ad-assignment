import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  modalSubject = new Subject<any>();
  closeModalSubject = new Subject<void>();

  constructor() { }

  subscribe(callback: any) {
    return this.modalSubject.subscribe(callback);
  }

  subscribeToClose(callback: any) {
    return this.closeModalSubject.subscribe(callback);
  }

  showEditModal(product: any) {
    this.modalSubject.next(product);
  }

  showCreateModal() {
    this.modalSubject.next({});
  }

  closeModal() {
    this.closeModalSubject.next();
  }
}
