package com.example.TradingAlert.Repository;

import com.example.TradingAlert.Dto.TradeStock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends CrudRepository<TradeStock, Long> {
}