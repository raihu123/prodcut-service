package com.miu.swa.project.prodcutservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@NoArgsConstructor
public class ProductDTO implements Serializable {
    String name;
    Double price;
    String description;
    int stock;
}
