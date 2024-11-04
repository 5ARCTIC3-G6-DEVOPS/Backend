package tn.esprit.devops_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Supplier;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT s FROM Supplier s JOIN s.productStock ps WHERE key(ps) = :product")
    List<Supplier> findSuppliersByProduct(Product product);
}
