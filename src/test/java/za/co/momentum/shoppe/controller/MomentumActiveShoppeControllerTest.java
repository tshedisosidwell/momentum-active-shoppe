package za.co.momentum.shoppe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import za.co.momentum.shoppe.model.MomentumActiveShoppeResponse;
import za.co.momentum.shoppe.model.PurchaseProductRequest;
import za.co.momentum.shoppe.service.MomentumActiveShoppeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static za.co.momentum.shoppe.common.CommonFactory.createProducts;

@SpringBootTest
@AutoConfigureMockMvc
public class MomentumActiveShoppeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MomentumActiveShoppeService momentumActiveShoppeService;

    @Test
    public void testViewAllProducts() throws Exception {
        when(momentumActiveShoppeService.viewAllProducts()).thenReturn(createProducts());
        mockMvc.perform(MockMvcRequestBuilders.get("/momentum-active-shoppe/getProducts")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultsMessage").value("OK"));
    }

    @Test
    public void testPurchaseProducts() throws Exception {
        when(momentumActiveShoppeService.productPurchase(any(PurchaseProductRequest.class)))
                .thenReturn(new MomentumActiveShoppeResponse(200L, "OK"));
        mockMvc.perform(MockMvcRequestBuilders.post("/momentum-active-shoppe/purchaseProducts")
                .content(createJsonRequest())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultsCode").value(200));
    }

    private String createJsonRequest() throws JsonProcessingException {
        PurchaseProductRequest purchaseProductRequest = new PurchaseProductRequest();
        purchaseProductRequest.setCustomerId("T20");
        purchaseProductRequest.getProducts().add(270);
        purchaseProductRequest.getProducts().add(280);

        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(purchaseProductRequest);
    }
}