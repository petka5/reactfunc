package org.petka.reactfunc;

import org.petka.reactfunc.persistence.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
