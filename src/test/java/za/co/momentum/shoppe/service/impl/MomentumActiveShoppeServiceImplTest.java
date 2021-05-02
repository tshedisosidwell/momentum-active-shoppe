package za.co.momentum.shoppe.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import za.co.momentum.shoppe.common.CommonFactory;
import za.co.momentum.shoppe.entity.Customer;
import za.co.momentum.shoppe.entity.Product;
import za.co.momentum.shoppe.model.MomentumActiveShoppeResponse;
import za.co.momentum.shoppe.model.PurchaseProductRequest;
import za.co.momentum.shoppe.repository.CustomerDao;
import za.co.momentum.shoppe.repository.ProductDao;
import za.co.momentum.shoppe.service.MomentumActiveShoppeService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MomentumActiveShoppeServiceImplTest {

    @MockBean
    private CustomerDao customerDao;
    @MockBean
    private ProductDao productDao;

    @Autowired
    private MomentumActiveShoppeService momentumActiveShoppeService;

    @Test
    public void testViewAllProducts() {
        when(productDao.findAll()).thenReturn(CommonFactory.createProducts().getProducts());
        List<Product> productList = momentumActiveShoppeService.viewAllProducts().getProducts();

        Assert.notEmpty(productList, "No records found, test failed");
    }

    @Test
    public void testSuccessfulProductPurchase() {
        when(productDao.findProducts(anyList())).thenReturn(CommonFactory.createProducts().getProducts());
        when(customerDao.findCustomerByUniqueID(anyString())).thenReturn(createCustomer());

        MomentumActiveShoppeResponse response = momentumActiveShoppeService.productPurchase(createPurchaseProductRequest());

        Assert.isTrue(response.getResultsMessage().equals("OK"), "Something must have happened, this shouldn't fail");
    }

    @Test
    public void testCustomerDoesNotExist() {
        when(productDao.findProducts(anyList())).thenReturn(CommonFactory.createProducts().getProducts());
        when(customerDao.findCustomerByUniqueID(anyString())).thenReturn(null);

        MomentumActiveShoppeResponse response = momentumActiveShoppeService.productPurchase(createPurchaseProductRequest());
        Assert.isTrue(response.getResultsMessage().equals("Customer ID provided does not exist"), "Testing if customer exist in a store");
    }

    @Test
    public void testNoProductsProvidedForPurchase() {
        when(productDao.findProducts(anyList())).thenReturn(CommonFactory.createProducts().getProducts());
        when(customerDao.findCustomerByUniqueID(anyString())).thenReturn(createCustomer());

        PurchaseProductRequest purchaseProductRequest = new PurchaseProductRequest();
        purchaseProductRequest.setCustomerId("T20");

        MomentumActiveShoppeResponse response = momentumActiveShoppeService.productPurchase(purchaseProductRequest);
        Assert.isTrue(response.getResultsMessage().equals("No products were provided for purchase"), "Customer did not provide any products");
    }

    @Test
    public void testProductDoesNotExist() {
        PurchaseProductRequest purchaseProductRequest = new PurchaseProductRequest();
        purchaseProductRequest.setCustomerId("T20");
        purchaseProductRequest.getProducts().add(220);

        List<Product> productList = CommonFactory.createProducts().getProducts();

        when(productDao.findProducts(anyList())).thenReturn(productList);
        when(customerDao.findCustomerByUniqueID(anyString())).thenReturn(createCustomer());

        MomentumActiveShoppeResponse response = momentumActiveShoppeService.productPurchase(purchaseProductRequest);
        Assert.isTrue(response.getResultsMessage().equals("One of the products provided does not exist"), "Product does not exist");
    }

    private PurchaseProductRequest createPurchaseProductRequest() {
        PurchaseProductRequest purchaseProductRequest = new PurchaseProductRequest();
        purchaseProductRequest.setCustomerId("T20");
        purchaseProductRequest.getProducts().add(270);
        purchaseProductRequest.getProducts().add(280);

        return purchaseProductRequest;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setTotalPoints(1000);
        customer.setName("Tshediso");
        customer.setUniqueID("T20");

        return customer;
    }

}