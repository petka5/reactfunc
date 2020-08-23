package org.petka.reactfunc.persistence.repository;

import org.petka.reactfunc.persistence.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
