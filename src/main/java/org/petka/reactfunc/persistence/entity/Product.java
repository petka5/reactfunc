package org.petka.reactfunc.persistence.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Value
@Builder(toBuilder = true)
@Document
public class Product {
    @Id
    private String id;
    private String name;
    private Double price;
}
