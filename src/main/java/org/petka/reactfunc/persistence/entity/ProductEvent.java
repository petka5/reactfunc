package org.petka.reactfunc.persistence.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Data
@Builder(toBuilder = true)
public class ProductEvent {
    private Long eventId;
    private String eventType;
}
