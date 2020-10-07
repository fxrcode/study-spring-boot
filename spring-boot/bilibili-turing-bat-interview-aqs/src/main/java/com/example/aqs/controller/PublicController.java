package com.example.aqs.controller;


import com.example.aqs.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order")
@RestController
public class PublicController {

    @Autowired
    private TradeService tradeService;

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    public String order(long id){
        return tradeService.decStockNoLock(id);
    }

    @GetMapping("/getStock")
    @ResponseStatus(HttpStatus.OK)
    public int getStock(long id) {
        return tradeService.getStock(id);
    }
}
