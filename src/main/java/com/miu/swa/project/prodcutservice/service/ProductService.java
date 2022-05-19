package com.miu.swa.project.prodcutservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.swa.project.prodcutservice.model.Product;
import com.miu.swa.project.prodcutservice.model.ProductDTO;
import com.miu.swa.project.prodcutservice.repo.ProductRepo;
import com.thoughtworks.xstream.core.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    private void addProduct( String name, double price, String description, int stock) {
        BigInteger productID = this.sequenceGenarato();
        Product product = new Product(productID, name, price, description, stock);
        productRepo.save(product);

    }

    private BigInteger sequenceGenarato(){
        Random rand = new Random();
        BigInteger result = new BigInteger(15, rand);
        return result;
    }

    private final KafkaTemplate kafkaTemplate;

    public ProductService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void addProductUsingKafka( ProductDTO product) {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = "";
        try {
            data = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        kafkaTemplate.send("add-product", data);
    }

    @KafkaListener(topics = "add-product")
    public void  receiveKafkaMessage(@Payload String s) {
        System.out.println("-------------------------");
        System.out.println(s);
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO record = null;
        try {
            record = objectMapper.readValue(s, ProductDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("received message [product-1] =" + record);
        this.addProduct(record.getName(),record.getPrice(),record.getDescription(),record.getStock());
    }

    public Product getProduct(BigInteger productID) {
        Optional<Product> result = productRepo.findById(productID);
        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    public void setStock(BigInteger productID, int quantity) {
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


    @KafkaListener(topics = "update-product")
    public void  updateProductUsingKafka(@Payload String s) {
        System.out.println("-------------------------");
        System.out.println(s);
        ObjectMapper objectMapper = new ObjectMapper();
        Product record = null;
        try {
            record = objectMapper.readValue(s, Product.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("received message [product-1] =" + record);
//        this.updateProduct(record.getName(),record.getPrice(),record.getDescription(),record.getStock());
    }


}
