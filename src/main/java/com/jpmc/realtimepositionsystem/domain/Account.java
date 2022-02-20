package com.jpmc.realtimepositionsystem.domain;

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
