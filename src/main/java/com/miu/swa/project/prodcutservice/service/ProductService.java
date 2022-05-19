package com.miu.swa.project.prodcutservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.swa.project.prodcutservice.model.Product;
import com.miu.swa.project.prodcutservice.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
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

    private final KafkaTemplate kafkaTemplate;

    public ProductService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendKafkaTopic(String topic, Product product) {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = "";
        try {
            data = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        kafkaTemplate.send(topic, data);
    }

    @KafkaListener(topics = "product")
    public void  receiveKafkaMessage(@Payload String s) {
        System.out.println("-------------------------");
        System.out.println(s);
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
