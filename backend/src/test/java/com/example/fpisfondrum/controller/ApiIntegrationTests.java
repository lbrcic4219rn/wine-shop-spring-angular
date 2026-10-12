package com.example.fpisfondrum.controller;

import com.example.fpisfondrum.model.Wine;
import com.example.fpisfondrum.model.WineStyle;
import com.example.fpisfondrum.model.WineVariety;
import com.example.fpisfondrum.repo.OrderRepo;
import com.example.fpisfondrum.repo.WineRepo;
import com.example.fpisfondrum.repo.WineStyleRepo;
import com.example.fpisfondrum.repo.WineVarietyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WineRepo wineRepo;
    @Autowired
    private WineStyleRepo wineStyleRepo;
    @Autowired
    private WineVarietyRepo wineVarietyRepo;
    @Autowired
    private OrderRepo orderRepo;

    private WineStyle red;
    private WineVariety riesling;
    private Wine whiteRiesling;
    private Wine redRiesling;

    @BeforeEach
    void setUp() {
        WineStyle white = wineStyleRepo.save(new WineStyle("Belo"));
        red = wineStyleRepo.save(new WineStyle("Crveno"));
        riesling = wineVarietyRepo.save(new WineVariety("Rizling"));
        WineVariety shiraz = wineVarietyRepo.save(new WineVariety("Siraz"));

        whiteRiesling = wineRepo.save(new Wine("White Riesling", white, riesling, "wine1.jpg", new BigDecimal("100.00")));
        redRiesling = wineRepo.save(new Wine("Red Riesling", red, riesling, "wine2.jpg", new BigDecimal("250.50")));
        wineRepo.save(new Wine("Red Shiraz", red, shiraz, "wine3.jpg", new BigDecimal("300.00")));
    }

    @Test
    void listsStylesAndVarieties() throws Exception {
        mockMvc.perform(get("/wine-style")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
        mockMvc.perform(get("/wine-variety")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void searchWithoutFiltersReturnsAllWines() throws Exception {
        mockMvc.perform(get("/wine"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.totalElements").value(3));
    }

    @Test
    void searchCombinesStyleAndVarietyFilters() throws Exception {
        mockMvc.perform(get("/wine")
                        .param("styleIds", red.getId().toString())
                        .param("varietyIds", riesling.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("Red Riesling"))
                .andExpect(jsonPath("$.content[0].style.name").value("Crveno"));
    }

    @Test
    void searchIsPaged() throws Exception {
        mockMvc.perform(get("/wine").param("size", "2").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    @Test
    void searchRejectsUnknownSortProperty() throws Exception {
        mockMvc.perform(get("/wine").param("sort", "nope")).andExpect(status().isBadRequest());
    }

    @Test
    void createsOrderWithTotalAndPurchasePrices() throws Exception {
        postOrder("""
                {"customerName": "Ana", "customerEmail": "ana@example.com", "items": [
                  {"wineId": %d, "quantity": 2},
                  {"wineId": %d, "quantity": 1}
                ]}""".formatted(whiteRiesling.getId(), redRiesling.getId()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.total").value(450.50))
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[1].unitPrice").value(250.50));

        assertThat(orderRepo.count()).isEqualTo(1);
    }

    @Test
    void mergesDuplicateWinesIntoOneLine() throws Exception {
        postOrder("""
                {"customerName": "Ana", "customerEmail": "ana@example.com", "items": [
                  {"wineId": %1$d, "quantity": 2},
                  {"wineId": %1$d, "quantity": 3}
                ]}""".formatted(whiteRiesling.getId()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].quantity").value(5))
                .andExpect(jsonPath("$.total").value(500.00));
    }

    @Test
    void rejectsEmptyOrder() throws Exception {
        postOrder("{\"customerName\": \"Ana\", \"customerEmail\": \"ana@example.com\", \"items\": []}")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.items").exists());
    }

    @Test
    void rejectsNonPositiveQuantityAndBadCustomer() throws Exception {
        postOrder("{\"customerName\": \" \", \"customerEmail\": \"not-an-email\", \"items\": [{\"wineId\": %d, \"quantity\": 0}]}"
                .formatted(whiteRiesling.getId()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.customerName").exists())
                .andExpect(jsonPath("$.errors.customerEmail").exists())
                .andExpect(jsonPath("$.errors['items[0].quantity']").exists());

        assertThat(orderRepo.count()).isZero();
    }

    @Test
    void returnsNotFoundForUnknownWine() throws Exception {
        postOrder("{\"customerName\": \"Ana\", \"customerEmail\": \"ana@example.com\", \"items\": [{\"wineId\": 999999, \"quantity\": 1}]}")
                .andExpect(status().isNotFound());
    }

    @Test
    void readsOrdersBack() throws Exception {
        String body = "{\"customerName\": \"Ana\", \"customerEmail\": \"ana@example.com\", \"items\": [{\"wineId\": %d, \"quantity\": 2}]}"
                .formatted(whiteRiesling.getId());
        postOrder(body).andExpect(status().isCreated());
        postOrder(body).andExpect(status().isCreated());
        Long newestId = orderRepo.findAll().stream().map(order -> order.getId()).max(Long::compare).orElseThrow();

        mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].id").value(newestId));

        mockMvc.perform(get("/order/{id}", newestId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Ana"))
                .andExpect(jsonPath("$.total").value(200.00))
                .andExpect(jsonPath("$.items[0].wineName").value("White Riesling"));
    }

    @Test
    void returnsNotFoundForUnknownOrder() throws Exception {
        mockMvc.perform(get("/order/999999")).andExpect(status().isNotFound());
    }

    private ResultActions postOrder(String body) throws Exception {
        return mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON).content(body));
    }
}
