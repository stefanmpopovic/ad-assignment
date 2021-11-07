package com.stefan.adassignmentbe.controller;

import com.stefan.adassignmentbe.exception.NotFoundException;
import com.stefan.adassignmentbe.exception.ValidationException;
import com.stefan.adassignmentbe.model.ErrorMessage;
import com.stefan.adassignmentbe.model.ProductDTO;
import com.stefan.adassignmentbe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/product")
    public List<ProductDTO> getAllProducts() {
        return productService.findAllProducts();
    }

    @PostMapping(path = "/product")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @PutMapping(path = "/product/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class})
    public ErrorMessage handleValidationException(ValidationException ex) {
        return ErrorMessage.of(ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ErrorMessage handleNotFoundException(NotFoundException ex) {
        return ErrorMessage.of(ex.getMessage());
    }
}
