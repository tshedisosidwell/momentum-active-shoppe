package za.co.momentum.shoppe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.momentum.shoppe.entity.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {

    Customer findCustomerByUniqueID(String uniqueId);
}
