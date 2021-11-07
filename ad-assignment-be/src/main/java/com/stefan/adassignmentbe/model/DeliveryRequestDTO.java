package com.stefan.adassignmentbe.model;

public class DeliveryRequestDTO {

    private long productId;
    private int quantity;
    private boolean expressDelivery;

    public static DeliveryRequestDTO of(Long productId, Integer quantity, Boolean expressDelivery) {
        DeliveryRequestDTO deliveryRequestDTO = new DeliveryRequestDTO();

        deliveryRequestDTO.setProductId(productId);
        deliveryRequestDTO.setQuantity(quantity);
        deliveryRequestDTO.setExpressDelivery(expressDelivery);

        return deliveryRequestDTO;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isExpressDelivery() {
        return expressDelivery;
    }

    public void setExpressDelivery(boolean expressDelivery) {
        this.expressDelivery = expressDelivery;
    }
}
