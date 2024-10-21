import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.devops_project.DevOps_ProjectSpringBootApplication; // Import your main application class
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.SupplierServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = DevOps_ProjectSpringBootApplication.class) // Specify the main application class
public class SupplierServiceImplTest {

    @MockBean
    private SupplierRepository supplierRepository;

    @Autowired
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
    }

    @Test
    public void testUpdateSupplier() {
        Supplier supplier = new Supplier();
        supplier.setIdSupplier(1L);
        supplier.setCode("SUP001");
        supplier.setLabel("Updated Supplier");

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
}
