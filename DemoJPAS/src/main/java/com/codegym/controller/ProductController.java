package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(Product product) {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        productService.save(product);
        modelAndView.addObject("message","Product created successful!");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView showListProducts() {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        Iterable<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showFormEdit(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/product/edit");
            modelAndView.addObject("product", product.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public ModelAndView updateProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product",product);
        modelAndView.addObject("message","Product updated successful!");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showFormDelete(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/product/delete");
            modelAndView.addObject("product",product.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteProduct(@ModelAttribute("product") Product product){
        productService.remove(product.getId());
        return "redirect:/products";
    }

    @GetMapping("/view/{id}")
    public ModelAndView showDetail(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/product/view");
            modelAndView.addObject("product",product.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

}
