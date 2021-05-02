package za.co.momentum.shoppe.model;

import java.util.ArrayList;
import java.util.List;

public class PurchaseProductRequest {

    private String customerId;
    private List<Integer> products;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Integer> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public void setProducts(List<Integer> products) {
        this.products = products;
    }
}
