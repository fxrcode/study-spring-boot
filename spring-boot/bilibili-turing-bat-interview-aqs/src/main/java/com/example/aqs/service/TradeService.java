package com.example.aqs.service;

import com.example.aqs.current.TuringLock;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TradeService {
    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(TradeService.class);
    //    private final Object SYNC_ROOT = new Object(); // from weinan
//    private ReentrantLock lock;
    private TuringLock sync = new TuringLock();
    private JdbcTemplate jdbcTemplate;

    public String decStockNoLock(long id) {
        // use synchronized, or ReentrantLock (common used lock in Java)
        sync.lock();
        Integer stock;
        List<Map<String, Object>> result = jdbcTemplate
                .queryForList("select stock from shop_order where id = ?", id);
        // check if stock still available
        if (result.isEmpty() || (stock = (Integer) result.get(0).get("stock")) <= 0) {
            // out of stock!
            Logger.error("Out of stock!");
            sync.unlock();
            return "out of stock";
        }
        // decr stock
        stock--;
        jdbcTemplate.update("update shop_order set stock = ? where id = ?", stock, id);
        Logger.info("success purchase, decrement stock, current stock {}", stock);

        sync.unlock();
        return String.format("success purchase, decrement stock, current stock %s", stock);
    }

    public int getStock(long id) {
        Integer stock;
        final List<Map<String, Object>> result = jdbcTemplate
                .queryForList("select stock from shop_order where id = ?", id);
        if (result.isEmpty()) {
            Logger.error("Didn't find the stock for {}", id);
            return -1;
        }
        stock = (Integer) result.get(0).get("stock");
        return stock;
    }
}
