package org.petka.reactfunc.persistence.repository;

import org.petka.reactfunc.persistence.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonReactiveProductRepository extends MongoRepository<Product, String> {

}
