package com.jpmc.realtimepositionsystem.domain;

public class Trade {
    private Integer id;
    private TradeType tradeType;
    private String accountId;
    private String securitySymbol;
    private Integer quantity;

    public Trade(Integer id, TradeType tradeType, String accountId, String securitySymbol, Integer quantity) {
        this.id = id;
        this.tradeType = tradeType;
        this.accountId = accountId;
        this.securitySymbol = securitySymbol;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getSecuritySymbol() {
        return securitySymbol;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "id=" + id + " " + tradeType + " " + accountId + " " + securitySymbol + " " + quantity + "";
    }


}
