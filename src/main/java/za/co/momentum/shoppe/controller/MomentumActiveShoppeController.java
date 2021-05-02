package za.co.momentum.shoppe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import za.co.momentum.shoppe.entity.Product;
import za.co.momentum.shoppe.model.MomentumActiveShoppeResponse;
import za.co.momentum.shoppe.model.PurchaseProductRequest;
import za.co.momentum.shoppe.service.MomentumActiveShoppeService;

import java.util.List;

@RestController
@RequestMapping(value = "/momentum-active-shoppe")
public class MomentumActiveShoppeController {

    private MomentumActiveShoppeService momentumActiveShoppeService;

    @RequestMapping(method = RequestMethod.GET, value = "/getProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public MomentumActiveShoppeResponse getProducts() {
        return momentumActiveShoppeService.viewAllProducts();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/purchaseProducts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody MomentumActiveShoppeResponse purchaseProducts(@RequestBody PurchaseProductRequest purchaseProductRequest) {
        return momentumActiveShoppeService.productPurchase(purchaseProductRequest);
    }

    @Autowired
    public void setMomentumActiveShoppeService(MomentumActiveShoppeService momentumActiveShoppeService) {
        this.momentumActiveShoppeService = momentumActiveShoppeService;
    }
}
