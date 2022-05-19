package com.miu.swa.project.prodcutservice.service;

import com.miu.swa.project.prodcutservice.model.Product;
import com.miu.swa.project.prodcutservice.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public void addProduct(BigInteger productID, String name, double price, String description, BigInteger stock) {
        Product product = new Product(productID, name, price, description, stock);
        productRepo.save(product);

    }

    public Product getProduct(BigInteger productID) {
        Optional<Product> result = productRepo.findById(productID);
        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    public void setStock(BigInteger productID, BigInteger quantity) {
        Optional<Product> result = productRepo.findById(productID);
        if (result.isPresent()) {
            Product product = result.get();
            product.setStock(quantity);
            productRepo.save(product);
        }
    }

    public void updateProduct(BigInteger productID, double price, String description) {
        Optional<Product> result = productRepo.findById(productID);
        if (result.isPresent()) {
            Product product = result.get();
            product.setPrice(price);
            product.setDescription(description);
            productRepo.save(product);
        }

    }
}
