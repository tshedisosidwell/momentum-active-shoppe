package za.co.momentum.shoppe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.momentum.shoppe.entity.Product;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {

    List<Product> findProducts(List<Integer> providedCodes);
}
