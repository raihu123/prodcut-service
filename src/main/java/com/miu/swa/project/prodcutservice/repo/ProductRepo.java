package com.miu.swa.project.prodcutservice.repo;

import com.miu.swa.project.prodcutservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface ProductRepo extends MongoRepository<Product, BigInteger> {
    Optional<Product> findByProdID(BigInteger prod_num);
}
