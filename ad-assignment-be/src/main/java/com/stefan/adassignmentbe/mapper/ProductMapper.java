package com.stefan.adassignmentbe.mapper;

import com.stefan.adassignmentbe.model.DeliveryTime;
import com.stefan.adassignmentbe.model.Product;
import com.stefan.adassignmentbe.model.ProductDTO;
import com.stefan.adassignmentbe.util.JsonUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setUnit(product.getUnit());
        productDTO.setDeliveryTimes(JsonUtils.parseList(product.getDeliveryTimes(), DeliveryTime.class));

        return productDTO;
    }

    public static List<ProductDTO> toProductDTOList(List<Product> products) {
        if (products == null) {
            return List.of();
        }

        return products.stream()
                .map(ProductMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    public static Product toProduct(ProductDTO productDTO) {
        Product product = new Product();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setUnit(productDTO.getUnit());
        product.setDeliveryTimes(JsonUtils.toJson(productDTO.getDeliveryTimes()));

        return product;
    }

    public static List<Product> toProductList(List<ProductDTO> products) {
        if (products == null) {
            return List.of();
        }

        return products.stream()
                .map(ProductMapper::toProduct)
                .collect(Collectors.toList());
    }
}
