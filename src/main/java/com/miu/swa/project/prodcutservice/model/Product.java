package com.miu.swa.project.prodcutservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@Document
public class Product {
    @Id
    BigInteger prodID;
    String name;
    Double price;
    String description;
    BigInteger stock;
}
