package org.petka.reactfunc.controllers;

import java.util.List;

import org.petka.reactfunc.persistence.entity.Product;
import org.petka.reactfunc.persistence.repository.NonReactiveProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    protected final NonReactiveProductRepository productRepository;

    @Autowired
    public ProductController(NonReactiveProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping("/test/products")
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
