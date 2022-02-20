package com.jpmc.realtimepositionsystem.tradeentities;

public class Account {
    private String accountId;

    public Account(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "" + accountId + "";
    }

}
