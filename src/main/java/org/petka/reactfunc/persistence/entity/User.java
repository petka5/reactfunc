package org.petka.reactfunc.persistence.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Value;


@Data
@Value
@Builder(toBuilder = true)
public class User {
    private String username;
    private String password;
    private int age;
}
