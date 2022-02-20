package com.jpmc.realtimepositionsystem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jpmc.realtimepositionsystem.tradeentities.Account;
import com.jpmc.realtimepositionsystem.tradeentities.Position;
import com.jpmc.realtimepositionsystem.tradeentities.Trade;
import com.jpmc.realtimepositionsystem.tradeentities.TradeType;

public class PositionService {

    // Accountid, Position
    private Map<String, Position> positionMap = new HashMap<>();
    private Position position;


    public Map<String, Position> addToPositions(String accountId, Trade trade) throws Exception {

        if (this.positionMap.get(accountId) == null) {
            this.positionMap.put(accountId, new Position(new Account(accountId)));
        }
        position = this.positionMap.get(accountId);

        if (trade.getTradeType() == TradeType.BUY) {
            buyTrade(trade);
        }
        if (trade.getTradeType() == TradeType.SELL || trade.getTradeType() == TradeType.CANCEL) {
            sellOrCancelTrade(trade);
        }
        Collections.unmodifiableMap(positionMap);
        return positionMap;

    }

    private void buyTrade(Trade trade) throws Exception {
        String securitySymbol = trade.getSecuritySymbol();
        // New security
        if (position.getTrades(securitySymbol) == null) {
            position.setTrades(securitySymbol, new ArrayList<>());
            position.setSecurityPosition(securitySymbol, 0);
        }
        updateQuantity(trade);
    }

    private void sellOrCancelTrade(Trade trade) throws Exception {
        String securitySymbol = trade.getSecuritySymbol();
        // New security
        if (position.getTrades(securitySymbol) == null || position.getTrades(securitySymbol).isEmpty()) {
            throw new IllegalArgumentException("Security not found");
        }
        updateQuantity(trade);
    }

    private void updateQuantity(Trade trade) throws Exception {

        String securitySymbol = trade.getSecuritySymbol();
        if (trade.getTradeType() == TradeType.BUY) {
            //validating duplication
            if (position.getTrades(securitySymbol).stream().filter(t -> t.getId().equals(trade.getId())).findFirst().isPresent())
                throw new IllegalArgumentException("Invalid  ID: " + trade.getId() + ". It already exists!");
            position.setCurrentQuantity(position.getSecurityPosition(securitySymbol) + trade.getQuantity());
        } else if (trade.getTradeType() == TradeType.SELL) {
            position.setCurrentQuantity(position.getSecurityPosition(securitySymbol) - trade.getQuantity());
        } else if (trade.getTradeType() == TradeType.CANCEL) {
            Trade lastTrade = position.getTrades(securitySymbol).get(position.getTrades(securitySymbol).size() - 1);
            if (lastTrade.getTradeType() != TradeType.BUY) {
                throw new Exception("Trade cancellation failed");
            }
            position.setCurrentQuantity(position.getSecurityPosition(securitySymbol) - lastTrade.getQuantity());
        }
        position.setSecurityPosition(securitySymbol, position.getCurrentQuantity());
        position.addTrade(securitySymbol,trade);
    }

}
