import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.devops_project.DevOps_ProjectSpringBootApplication; // Import your main application class
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.SupplierServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)

public class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Test
    public void testAddSupplier() {
        Supplier supplier = new Supplier();
        supplier.setCode("SUP001");
        supplier.setLabel("Test Supplier");

        Mockito.when(supplierRepository.save(supplier)).thenReturn(supplier);

        Supplier result = supplierService.addSupplier(supplier);

        assertNotNull(result, "The result should not be null after adding the supplier.");
        assertEquals("SUP001", result.getCode(), "The supplier code should match the expected value.");
        assertEquals("Test Supplier", result.getLabel(), "The supplier label should match the expected value.");

        System.out.println("added supplier named  " + supplier.getLabel());

        System.out.println("test add supplier is working");

    }

    @Test
    public void testRetrieveSupplier() {
        Supplier supplier = new Supplier();
        supplier.setIdSupplier(1L);
        supplier.setCode("SUP001");
        supplier.setLabel("Test Supplier");

        Mockito.when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        Supplier result = supplierService.retrieveSupplier(1L);

        assertNotNull(result, "The retrieved supplier should not be null.");
        assertEquals("SUP001", result.getCode(), "The retrieved supplier code should match the expected value.");

        System.out.println(supplier.getLabel());
        System.out.println("test retrieve product is working");
    }

    @Test
    public void testRetrieveAllSuppliers() {
        List<Supplier> suppliers = Arrays.asList(
                new Supplier(1L, "SUP001", "Test Supplier 1", null, null),
                new Supplier(2L, "SUP002", "Test Supplier 2", null, null)
        );

        Mockito.when(supplierRepository.findAll()).thenReturn(suppliers);

        List<Supplier> result = supplierService.retrieveAllSuppliers();

        assertEquals(2, result.size(), "The size of the retrieved supplier list should be 2.");
        for (Supplier supplier : result) {
            System.out.println("supplier Name: " + supplier.getLabel());
        }

        System.out.println("test retrieve all suppliers is working");

    }

    @Test
    public void testUpdateSupplier() {
        // Create a new supplier instance
        Supplier supplier = new Supplier();

        // Display the original supplier details (should be null since it hasn't been set)
        System.out.println("Original supplier: " + supplier.getLabel() + " " + supplier.getCode());

        // Set supplier details
        supplier.setIdSupplier(1L);
        supplier.setCode("SUP001");
        supplier.setLabel("Updated Supplier");

        // Display the updated supplier details
        System.out.println("Updated supplier: " + supplier.getLabel() + " " + supplier.getCode());

        // Simulate repository behavior
        Mockito.when(supplierRepository.save(supplier)).thenReturn(supplier);

        // Call the method to test
        Supplier result = supplierService.updateSupplier(supplier);

        // Assertions
        assertNotNull(result, "The result should not be null after updating the supplier.");
        assertEquals("Updated Supplier", result.getLabel(), "The supplier label should match the expected value after update.");

        // Print confirmation message
        System.out.println("Update supplier is working! Supplier details updated successfully.");
    }


    @Test
    public void testDeleteSupplier() {
        // Call the delete method
        supplierService.deleteSupplier(1L);

        // Verify that the deleteById method was called
        Mockito.verify(supplierRepository, Mockito.times(1)).deleteById(1L);

        // Print confirmation message
        System.out.println("testDeleteSupplier is working! Supplier with ID 1 was deleted.");
    }

}
