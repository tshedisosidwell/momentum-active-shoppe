package za.co.momentum.shoppe.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import za.co.momentum.shoppe.entity.Customer;
import za.co.momentum.shoppe.entity.Product;
import za.co.momentum.shoppe.model.MomentumActiveShoppeResponse;
import za.co.momentum.shoppe.model.PurchaseProductRequest;
import za.co.momentum.shoppe.repository.CustomerDao;
import za.co.momentum.shoppe.repository.ProductDao;
import za.co.momentum.shoppe.service.MomentumActiveShoppeService;

import java.util.List;

@Service
public class MomentumActiveShoppeServiceImpl implements MomentumActiveShoppeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MomentumActiveShoppeServiceImpl.class);

    private static final String CUSTOMER_ID_DOES_NOT_EXIST_MESSAGE = "Customer ID provided does not exist";
    private static final long CUSTOMER_ID_DOES_NOT_EXIST_CODE = 220L;
    private static final String INSUFFICIENT_POINTS_MESSAGE = "There are no sufficient points to purchase";
    private static final long INSUFFICIENT_POINTS_CODE = 223L;
    private static final String NO_PRODUCTS_WERE_PROVIDED_FOR_PURCHASE_MESSAGE = "No products were provided for purchase";
    private static final long NO_PRODUCTS_WERE_PROVIDED_FOR_PURCHASE_CODE = 221L;
    private static final String PRODUCT_PROVIDED_DOES_NOT_EXIST_MESSAGE = "One of the products provided does not exist";
    private static final long PRODUCT_PROVIDED_DOES_NOT_EXIST_CODE = 222L;
    private static final String SUCCESSFUL_MESSAGE = "OK";
    private static final long SUCCESSFUL_CODE = 200L;

    private CustomerDao customerDao;
    private ProductDao productDao;

    @Override
    public MomentumActiveShoppeResponse productPurchase(PurchaseProductRequest purchaseProductRequest) {
        LOGGER.info("Processing a product purchase");
        long start = System.currentTimeMillis();

        try {
            List<Product> storeProductList = productDao.findProducts(purchaseProductRequest.getProducts());
            Customer customer = customerDao.findCustomerByUniqueID(purchaseProductRequest.getCustomerId());
            MomentumActiveShoppeResponse momentumActiveShoppeResponse = validatePurchaseDetails(purchaseProductRequest, storeProductList, customer);

            if (momentumActiveShoppeResponse.getResultsCode() == 200L) {
                if (!customerHasSufficientPointToPurchase(storeProductList, customer)) {
                    return new MomentumActiveShoppeResponse(INSUFFICIENT_POINTS_CODE, INSUFFICIENT_POINTS_MESSAGE);
                }
                double totalPoints = calculateTotalCost(storeProductList);
                subtractPointsAndUpdateCustomerTotalPoints(customer, totalPoints);
            }
            LOGGER.info("Processed a request in {} ms", System.currentTimeMillis() - start);
            return momentumActiveShoppeResponse;
        } catch (Exception ex) {
            LOGGER.error("Exception happened while processing a purchase", ex);
            return new MomentumActiveShoppeResponse(500L, "Internal server exception");
        }
    }

    @Override
    public MomentumActiveShoppeResponse viewAllProducts() {
        LOGGER.info("Retrieving Products from the store");
        long start = System.currentTimeMillis();
        MomentumActiveShoppeResponse momentumActiveShoppeResponse;
        try {
            List<Product> productList = productDao.findAll();
            momentumActiveShoppeResponse = new MomentumActiveShoppeResponse(200L, "OK");
            momentumActiveShoppeResponse.getProducts().addAll(productList);
            LOGGER.info("Processed a request in {} ms", System.currentTimeMillis() - start);
        } catch (Exception ex) {
            LOGGER.error("Exception happened while retrieving products", ex);
            momentumActiveShoppeResponse = new MomentumActiveShoppeResponse(500L, "Internal server exception");
        }
        return momentumActiveShoppeResponse;
    }

    private void subtractPointsAndUpdateCustomerTotalPoints(Customer customer, double totalPoints) {
        double customerPointsAfterPurchase = customer.getTotalPoints() - totalPoints;
        customer.setTotalPoints(customerPointsAfterPurchase);
        customerDao.save(customer);
    }

    private boolean customerHasSufficientPointToPurchase(List<Product> storeProducts, Customer customer) {
        return !(calculateTotalCost(storeProducts) > customer.getTotalPoints());
    }

    private double calculateTotalCost(List<Product> storeProducts) {
        double totalCost = 0;
        for (Product product : storeProducts) {
            totalCost += product.getCost();
        }

        return totalCost;
    }

    private MomentumActiveShoppeResponse validatePurchaseDetails(PurchaseProductRequest purchaseProductRequest, List<Product> storeProducts, Customer customer) {

        if (ObjectUtils.isEmpty(customer)) {
            return new MomentumActiveShoppeResponse(CUSTOMER_ID_DOES_NOT_EXIST_CODE, CUSTOMER_ID_DOES_NOT_EXIST_MESSAGE);
        }
        if (CollectionUtils.isEmpty(purchaseProductRequest.getProducts())) {
            return new MomentumActiveShoppeResponse(NO_PRODUCTS_WERE_PROVIDED_FOR_PURCHASE_CODE, NO_PRODUCTS_WERE_PROVIDED_FOR_PURCHASE_MESSAGE);
        }
        if (!doesAllProductsExist(purchaseProductRequest.getProducts(), storeProducts)) {
            return new MomentumActiveShoppeResponse(PRODUCT_PROVIDED_DOES_NOT_EXIST_CODE, PRODUCT_PROVIDED_DOES_NOT_EXIST_MESSAGE);
        }

        return new MomentumActiveShoppeResponse(SUCCESSFUL_CODE, SUCCESSFUL_MESSAGE);
    }

    private boolean doesAllProductsExist(List<Integer> products, List<Product> storeProductList) {

        for (Integer product : products) {
            if (!doesProductExist(product, storeProductList)) {
                return false;
            }
        }
        return true;
    }

    private boolean doesProductExist(int code, List<Product> products) {
        for (Product product : products) {
            if (code == product.getCode()) {
                return true;
            }
        }
        return false;
    }

    @Autowired
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
