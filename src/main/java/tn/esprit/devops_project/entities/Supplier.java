package tn.esprit.devops_project.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Supplier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long idSupplier;
	String code;
	String label;

	@Enumerated(EnumType.STRING)
	SupplierCategory supplierCategory;

	BigDecimal price;
	int reliabilityScore;  // Reliability score out of 100
	int leadTime;          // Lead time in days

	@OneToMany(mappedBy = "supplier")
	@JsonIgnore
	Set<Invoice> invoices;

	@ElementCollection
	@MapKeyJoinColumn(name = "product_id")
	@Column(name = "quantity")
	Map<Product, Integer> productStock;  // Maps a product to its available quantity

	// Constructor to initialize Supplier with all fields
	public Supplier(Long idSupplier, String code, String label, BigDecimal price, int reliabilityScore, int leadTime) {
		this.idSupplier = idSupplier;
		this.code = code;
		this.label = label;
		this.price = price;
		this.reliabilityScore = reliabilityScore;
		this.leadTime = leadTime;
		this.productStock = Map.of(); // Initialize with an empty map or pass a map if needed
	}

	public boolean hasStock(Product product, int requiredQuantity) {
		return productStock.getOrDefault(product, 0) >= requiredQuantity;
	}
}
