package com.example.Elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Ecommerce {
    @GetMapping("/ecommerce/add-product")
    public String addProduct() {
        return "ecommerce/add-product";
    }

    @GetMapping("/ecommerce/categories")
    public String categoris() {
        return "ecommerce/categories";
    }

    @GetMapping("/ecommerce/customers")
    public String customers() {
        return "ecommerce/customers";
    }

    @GetMapping("/ecommerce/order-details")
    public String orderDetails() {
        return "ecommerce/order-details";
    }

    @GetMapping("/ecommerce/orders")
    public String orders() {
        return "ecommerce/orders";
    }

    @GetMapping("/ecommerce/product-details")
    public String productDetails() {
        return "ecommerce/product-details";
    }

    @GetMapping("/ecommerce/products-grid")
    public String productsGrid() {
        return "ecommerce/products-grid";
    }

    @GetMapping("/ecommerce/products")
    public String products() {
        return "ecommerce/products";
    }

    @GetMapping("/ecommerce/reviews")
    public String reviews() {
        return "ecommerce/reviews";
    }

    @GetMapping("/ecommerce/seller-details")
    public String sellerDetails() {
        return "ecommerce/seller-details";
    }

    @GetMapping("/ecommerce/sellers")
    public String sellers() {
        return "ecommerce/sellers";
    }
}