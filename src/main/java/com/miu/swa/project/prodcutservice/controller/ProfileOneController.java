package com.miu.swa.project.prodcutservice.controller;

import com.miu.swa.project.prodcutservice.model.Product;
import com.miu.swa.project.prodcutservice.model.ProductDTO;
import com.miu.swa.project.prodcutservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@Profile("one")
public class ProfileOneController {

    @Autowired
    ProductService productService;


    @PostMapping(value = "/product")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO product) {
        productService.addProductUsingKafka(product);
        return new ResponseEntity<Product>(HttpStatus.OK);
    }

    @GetMapping("/product/{productID}")
    public ResponseEntity<?> getProduct(@PathVariable BigInteger productID) {
        Product product = productService.getProduct(productID);
        if (product == null) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Product not found= "
                + productID + " is not available"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PutMapping(value = "/product/{productID}")
    public ResponseEntity<?> updateProduct(@PathVariable BigInteger productID, @RequestBody Product product) {
        Product oldProduct = productService.getProduct(productID);
        productService.updateProduct(product.getProdID(), product.getPrice(), product.getDescription());
        return new ResponseEntity<Product>(HttpStatus.OK);
    }

    @GetMapping(value = "/product/{productID}/stock")
    public ResponseEntity<?> getStock(@PathVariable BigInteger productID) {
        return new ResponseEntity<String>(productService.getProduct(productID).getStock() + "", HttpStatus.OK);
    }
}
