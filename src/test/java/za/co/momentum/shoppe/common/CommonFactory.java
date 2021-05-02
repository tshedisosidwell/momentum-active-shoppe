package za.co.momentum.shoppe.common;

import za.co.momentum.shoppe.entity.Product;
import za.co.momentum.shoppe.model.MomentumActiveShoppeResponse;

import java.util.Arrays;

public class CommonFactory {

    public static MomentumActiveShoppeResponse createProducts() {
        MomentumActiveShoppeResponse momentumActiveShoppeResponse = new MomentumActiveShoppeResponse(200L, "OK");
        Product product = new Product();
        product.setCode(270);
        product.setCost(100);
        product.setName("Device");

        Product product2 = new Product();
        product2.setCode(280);
        product2.setCost(100);
        product2.setName("Device");

        momentumActiveShoppeResponse.setProducts(Arrays.asList(product, product2));
        return momentumActiveShoppeResponse;
    }
}
