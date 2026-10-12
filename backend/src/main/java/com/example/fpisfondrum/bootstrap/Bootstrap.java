package com.example.fpisfondrum.bootstrap;

import com.example.fpisfondrum.model.*;
import com.example.fpisfondrum.request.OrderItemRequest;
import com.example.fpisfondrum.request.OrderRequest;
import com.example.fpisfondrum.service.order.OrderService;
import com.example.fpisfondrum.service.wine.WineService;
import com.example.fpisfondrum.service.winestyle.WineStyleService;
import com.example.fpisfondrum.service.winevariety.WineVarietyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final WineService wineService;
    private final WineStyleService wineStyleService;
    private final WineVarietyService wineVarietyService;
    private final OrderService orderService;
    private final Random random = new Random();

    @Override
    public void run(String... args) {

        if (!wineService.findAll().isEmpty()) {
            return;
        }

        //generate initial data
        //Rizling, Pino griđo,Sovinjon Blan, Šardone, Pino noar, Zinfandel, Širaz, Kaberne sovinjon
        List<WineVariety> wineVarieties = List.of(
                new WineVariety("Rizling"),
                new WineVariety("Pino griđo"),
                new WineVariety("Sovinjon Blan"),
                new WineVariety("Sardone"),
                new WineVariety("Pino noar"),
                new WineVariety("Zinfandel"),
                new WineVariety("Siraz"),
                new WineVariety("Kaberne sovinjon")
        );

        wineVarieties.forEach(wineVarietyService::save);

        //Belo, Crveno, Rose
        List<WineStyle> wineStyles = List.of(
                new WineStyle("Belo"),
                new WineStyle("Crveno"),
                new WineStyle("Rose")
        );

        wineStyles.forEach(wineStyleService::save);

        //Create two copies for each combination of variety and style
        AtomicInteger globalCounter = new AtomicInteger();
        wineVarieties.forEach(wineVariety -> wineStyles.forEach(wineStyle -> {
            int random1 = random.nextInt(1, 6);
            int random2 = random.nextInt(1, 6);
            wineService.save(new Wine(String.format("Wine %d", globalCounter.getAndIncrement()), wineStyle, wineVariety, "wine" + random1 + ".jpg", BigDecimal.valueOf(random1 * 100L)));
            wineService.save(new Wine(String.format("Wine %d", globalCounter.getAndIncrement()), wineStyle, wineVariety, "wine" + random2 + ".jpg", BigDecimal.valueOf(random2 * 100L)));
        }));
        List<Wine> wines = wineService.findAll();
        orderService.save(new OrderRequest(
                "Test Customer",
                "customer@example.com",
                List.of(
                        new OrderItemRequest(wines.get(0).getId(), 1),
                        new OrderItemRequest(wines.get(1).getId(), 2),
                        new OrderItemRequest(wines.get(2).getId(), 3)
                )
        ));
    }
}
