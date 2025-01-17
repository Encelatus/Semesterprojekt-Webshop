package com.webshop.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.webshop.demo.model.Product;
import com.webshop.demo.repository.ProductRepository;
import com.webshop.demo.service.ProductService;


//Unit Tests for ProductService
public class ProductServiceTests {
    

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    //This test is for the save method of ProductService. It creates a new Product, mocks the repository's save method 
    //to return the same product, and then verifies that the returned product is not null. It also checks if the save 
    //method was called exactly once.
    @Test
    public void testSaveProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.save(product);

        assertNotNull(savedProduct);
        verify(productRepository, times(1)).save(product);
    }

    //This test checks the findById method when a product is found. It sets up a mock repository to return a 
    //specific product when findById is called with a given ID. Then, it verifies that the returned Optional<Product> 
    //contains a value (product) and that the value matches the expected product.
    @Test
    public void testFindProductById() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findById(productId);

        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
        
    }

    //This test is similar to the previous one but checks the case when a product is not found. 
    //It sets up the repository to return an empty Optional, and then it verifies that the returned Optional is empty 
    //(i.e., no product is found).
    @Test
    public void testFindProductByIdNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<Product> foundProduct = productService.findById(productId);

        assertFalse(foundProduct.isPresent());
    }


    // This test is for the findAllByCategory method. It sets up the repository to return an empty list of products 
    //for a specific category and then verifies that the list returned by findAllByCategory matches the expected empty list.
    @Test
    public void testFindAllProducts() {
        List<Product> products = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(products);

        List<Product> foundProducts = productService.findAll();

        assertEquals(products, foundProducts);
    }


    //This test is for the deleteById method. It calls the deleteById method with a specific product ID and 
    //verifies that the deleteById method in the repository was called exactly once with the same ID.
    @Test
    public void testFindAllProductsByCategory() {
        String category = "pflanzen";
        List<Product> products = new ArrayList<>();
        when(productRepository.findAllByCategory(category)).thenReturn(products);

        List<Product> foundProducts = productService.findAllByCategory(category);

        assertEquals(products, foundProducts);
    }

    @Test
    public void testDeleteProductById() {
        Long productId = 1L;
        productService.deleteById(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }





    
}
