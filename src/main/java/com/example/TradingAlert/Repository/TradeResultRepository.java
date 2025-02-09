package com.example.TradingAlert.Repository;

import com.example.TradingAlert.Dto.TradeResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeResultRepository extends CrudRepository<TradeResult, Long> {
}