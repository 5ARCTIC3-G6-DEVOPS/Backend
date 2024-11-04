package tn.esprit.devops_project.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.Iservices.ISupplierService;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SupplierServiceImpl implements ISupplierService {

	private final SupplierRepository supplierRepository;

	@Override
	public List<Supplier> retrieveAllSuppliers() {
		return supplierRepository.findAll();
	}

	@Override
	public Supplier addSupplier(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

	@Override
	public Supplier updateSupplier(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

	@Override
	public void deleteSupplier(Long supplierId) {
		supplierRepository.deleteById(supplierId);
	}

	@Override
	public Supplier retrieveSupplier(Long supplierId) {
		return supplierRepository.findById(supplierId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid supplier Id:" + supplierId));
	}

	public Supplier selectOptimalSupplier(Product product, int requiredQuantity) {
		List<Supplier> suppliers = getSuppliersForProduct(product);

		return suppliers.stream()
				.filter(supplier -> supplier.hasStock(product, requiredQuantity))
				.sorted(Comparator.comparing(Supplier::getReliabilityScore).reversed()  // High reliability first
						.thenComparing(Supplier::getPrice)                                  // Lower price first
						.thenComparing(Supplier::getLeadTime))                              // Shorter lead time first
				.findFirst()
				.orElseThrow(() -> new RuntimeException("No supplier available for the required quantity"));
	}

	protected List<Supplier> getSuppliersForProduct(Product product) {
		return supplierRepository.findSuppliersByProduct(product);
	}
}
