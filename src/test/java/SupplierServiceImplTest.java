import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.SupplierServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

        System.out.println("Added supplier named: " + supplier.getLabel());
    }

    @Test
    public void testRetrieveSupplier() {
        Supplier supplier = new Supplier(1L, "SUP001", "Test Supplier", BigDecimal.valueOf(20.00), 85, 5);

        Mockito.when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        Supplier result = supplierService.retrieveSupplier(1L);

        assertNotNull(result, "The retrieved supplier should not be null.");
        assertEquals("SUP001", result.getCode(), "The retrieved supplier code should match the expected value.");
    }

    @Test
    public void testRetrieveAllSuppliers() {
        List<Supplier> suppliers = Arrays.asList(
                new Supplier(1L, "SUP001", "Test Supplier 1", BigDecimal.valueOf(20.00), 85, 5),
                new Supplier(2L, "SUP002", "Test Supplier 2", BigDecimal.valueOf(18.00), 90, 7)
        );

        Mockito.when(supplierRepository.findAll()).thenReturn(suppliers);

        List<Supplier> result = supplierService.retrieveAllSuppliers();

        assertEquals(2, result.size(), "The size of the retrieved supplier list should be 2.");
        for (Supplier supplier : result) {
            System.out.println("Supplier Name: " + supplier.getLabel());
        }
    }

    @Test
    public void testUpdateSupplier() {
        Supplier supplier = new Supplier(1L, "SUP001", "Updated Supplier", BigDecimal.valueOf(20.00), 85, 5);

        Mockito.when(supplierRepository.save(supplier)).thenReturn(supplier);

        Supplier result = supplierService.updateSupplier(supplier);

        assertNotNull(result, "The result should not be null after updating the supplier.");
        assertEquals("Updated Supplier", result.getLabel(), "The supplier label should match the expected value after update.");
    }

    @Test
    public void testDeleteSupplier() {
        supplierService.deleteSupplier(1L);
        Mockito.verify(supplierRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testSelectOptimalSupplier_Success() {
        Product product = new Product(1L, "Test Product");
        int requiredQuantity = 10;

        Supplier supplier1 = new Supplier(1L, "SUP001", "Supplier A", BigDecimal.valueOf(20.00), 85, 5);
        Supplier supplier2 = new Supplier(2L, "SUP002", "Supplier B", BigDecimal.valueOf(18.00), 90, 7);
        Supplier supplier3 = new Supplier(3L, "SUP003", "Supplier C", BigDecimal.valueOf(22.00), 80, 4);

        supplierService = new SupplierServiceImpl(supplierRepository) {
            @Override
            protected List<Supplier> getSuppliersForProduct(Product product) {
                return Arrays.asList(supplier1, supplier2, supplier3);
            }
        };

        // Simulate stock for the suppliers
        supplier1.setProductStock(Map.of(product, 15));
        supplier2.setProductStock(Map.of(product, 10));
        supplier3.setProductStock(Map.of(product, 5));

        Supplier optimalSupplier = supplierService.selectOptimalSupplier(product, requiredQuantity);
        assertNotNull(optimalSupplier);
        System.out.println(optimalSupplier.getLabel());
        assertEquals("Supplier B", optimalSupplier.getLabel(), "The optimal supplier should be Supplier B");
    }
}
