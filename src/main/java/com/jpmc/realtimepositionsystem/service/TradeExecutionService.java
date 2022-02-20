package com.jpmc.realtimepositionsystem.service;

import java.util.Map;

import com.jpmc.realtimepositionsystem.domain.Position;
import com.jpmc.realtimepositionsystem.domain.Trade;
import com.jpmc.realtimepositionsystem.domain.TradeType;

public class TradeExecutionService {

    private PositionService positionService = new PositionService();

    public Map<String, Position> executeTrade(Integer tradeId, TradeType tradeType, String accountCode, String securityCode, Integer quantity) throws Exception {
        Trade trade = new Trade(tradeId, tradeType, accountCode, securityCode, quantity);
        return positionService.addToPositions(accountCode, trade);
    }

}
