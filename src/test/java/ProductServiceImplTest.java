import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.devops_project.DevOps_ProjectSpringBootApplication;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks

    private ProductServiceImpl productService;

    @Test
    public void testAddProduct() {
        // Create a mock product and stock
        Product product = new Product();
        product.setTitle("Product Test");
        product.setPrice(10.5f);
        product.setQuantity(100);

        Stock stock = new Stock();
        stock.setIdStock(1L);

        // Simulate repository behavior
        Mockito.when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        // Call the method to test
        Product result = productService.addProduct(product, 1L);

        // Check that the product has been saved with the stock
        assertNotNull(result, "The result should not be null after adding the product.");
        assertEquals("Product Test", result.getTitle(), "The product title should match the expected value.");
        assertEquals(stock, result.getStock(), "The product should be associated with the correct stock.");
        System.out.println("added product named" + product.getTitle());

        System.out.println("test add product is working");


    }

    @Test
    public void testRetrieveProduct() {
        // Create a mock product
        Product product = new Product();
        product.setIdProduct(1L);
        product.setTitle("Test Product");

        // Simulate repository behavior
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Call the method to test
        Product result = productService.retrieveProduct(1L);

        // Check that the product is retrieved correctly
        assertNotNull(result, "The retrieved product should not be null.");
        assertEquals("Test Product", result.getTitle(), "The retrieved product title should match the expected value.");
        System.out.println(product.getTitle());
        System.out.println("test retrieve product is working");



    }

    @Test
    public void testRetrieveAllProducts() {
        // Create a list of mock products
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", 15.0f, 50, ProductCategory.ELECTRONICS, null),
                new Product(2L, "Product 2", 25.0f, 30, ProductCategory.CLOTHING, null)
        );

        // Simulate repository behavior
        Mockito.when(productRepository.findAll()).thenReturn(products);

        // Call the method to test
        List<Product> result = productService.retreiveAllProduct();

        // Check that the list of products is correct
        assertEquals(2, result.size(), "The size of the retrieved product list should be 2.");

        for (Product product : result) {
            System.out.println("Product Name: " + product.getTitle());
        }

        System.out.println("test retrieve all products is working");
    }

    @Test
    public void testDeleteProduct() {
        // Call the delete method
        productService.deleteProduct(1L);

        // Verify that the deleteById method was called
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L);
        System.out.println("test delete product is working");


    }
}
