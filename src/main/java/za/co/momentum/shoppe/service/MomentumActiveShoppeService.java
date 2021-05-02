package za.co.momentum.shoppe.service;

import org.springframework.web.bind.annotation.RequestBody;
import za.co.momentum.shoppe.model.MomentumActiveShoppeResponse;
import za.co.momentum.shoppe.model.PurchaseProductRequest;

public interface MomentumActiveShoppeService {

    MomentumActiveShoppeResponse productPurchase(@RequestBody PurchaseProductRequest purchaseProductRequest);

    MomentumActiveShoppeResponse viewAllProducts();

}
