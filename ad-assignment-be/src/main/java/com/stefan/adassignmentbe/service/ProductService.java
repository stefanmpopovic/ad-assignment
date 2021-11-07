package com.stefan.adassignmentbe.service;

import com.stefan.adassignmentbe.dao.ProductRepository;
import com.stefan.adassignmentbe.exception.NotFoundException;
import com.stefan.adassignmentbe.exception.ValidationException;
import com.stefan.adassignmentbe.mapper.ProductMapper;
import com.stefan.adassignmentbe.model.DeliveryTime;
import com.stefan.adassignmentbe.model.Product;
import com.stefan.adassignmentbe.model.ProductDTO;
import com.stefan.adassignmentbe.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAllProducts() {
        return ProductMapper.toProductDTOList(productRepository.findAll());
    }

    public ProductDTO findById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> NotFoundException.of("Product not found for id: " + id));

        return ProductMapper.toProductDTO(product);
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        validateProductDTO(productDTO);
        sortDeliveryTimes(productDTO);

        Product product = ProductMapper.toProduct(productDTO);
        product.setId(null);

        return ProductMapper.toProductDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        validateProductDTO(productDTO);
        sortDeliveryTimes(productDTO);

        Product product = ProductMapper.toProduct(productDTO);
        product.setId(id);

        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            throw NotFoundException.of("Product with given id does not exist");
        }

        return ProductMapper.toProductDTO(productRepository.save(product));
    }

    private void validateProductDTO(ProductDTO productDTO) {
        if (StringUtils.isBlank(productDTO.getName())) {
            throw ValidationException.of("Product name cannot be blank");
        }

        if (StringUtils.isBlank(productDTO.getUnit())) {
            throw ValidationException.of("Product unit cannot be blank");
        }

        List<DeliveryTime> deliveryTimes = productDTO.getDeliveryTimes();

        if (deliveryTimes == null || deliveryTimes.isEmpty()) {
            throw ValidationException.of("Product must have at least one delivery time");
        }

        for (DeliveryTime deliveryTime : deliveryTimes) {
            if (deliveryTime.getMaxQuantity() <= 0) {
                throw ValidationException.of("Delivery time maximum quantity must be over 0");
            }

            if (deliveryTime.getDays() <= 0) {
                throw ValidationException.of("Delivery time days must be over 0");
            }
        }
    }

    private void sortDeliveryTimes(ProductDTO productDTO) {
        productDTO.getDeliveryTimes().sort(Comparator.comparingInt(DeliveryTime::getMaxQuantity));
    }
}
