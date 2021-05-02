package za.co.momentum.shoppe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import za.co.momentum.shoppe.entity.Product;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MomentumActiveShoppeResponse {

    private Long resultsCode;
    private String resultsMessage;
    private List<Product> products;

    public MomentumActiveShoppeResponse(Long aResultsCode, String aResultsMessage) {
        resultsCode = aResultsCode;
        resultsMessage = aResultsMessage;
    }

    public Long getResultsCode() {
        return resultsCode;
    }

    public void setResultsCode(Long resultsCode) {
        this.resultsCode = resultsCode;
    }

    public String getResultsMessage() {
        return resultsMessage;
    }

    public void setResultsMessage(String resultsMessage) {
        this.resultsMessage = resultsMessage;
    }

    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
