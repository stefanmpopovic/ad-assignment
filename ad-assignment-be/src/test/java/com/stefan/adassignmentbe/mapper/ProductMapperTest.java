package com.stefan.adassignmentbe.mapper;

import com.stefan.adassignmentbe.model.DeliveryTime;
import com.stefan.adassignmentbe.model.Product;
import com.stefan.adassignmentbe.model.ProductDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {

    @Test
    void test_toProductDTO() {
        Product product = new Product();
        product.setId(2L);
        product.setName("Test Product");
        product.setUnit("kg");
        product.setDeliveryTimes("[{\"maxQuantity\":10,\"days\":1},{\"maxQuantity\":30,\"days\":2}]");

        ProductDTO result = ProductMapper.toProductDTO(product);

        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getUnit(), result.getUnit());

        List<DeliveryTime> deliveryTimes = result.getDeliveryTimes();

        assertEquals(2, deliveryTimes.size());
        assertEquals(30, deliveryTimes.get(1).getMaxQuantity());
        assertEquals(2, deliveryTimes.get(1).getDays());
    }

}
