package com.jpmc.realtimepositionsystem.tradeentities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Position {
	private Account account;
	private Integer newQuantity;
	// Security Quantity
	private Map<String, Integer> securityPosition = new HashMap<String, Integer>();
	// Security trade map
	private Map<String, List<Trade>> trades = new HashMap<>();


	public  List<Trade> getTrades(String securitySymbol) {
		return this.trades.get(securitySymbol);
	}

	public void setTrades(String securitySymbol, List<Trade> trades) {
		this.trades.put(securitySymbol, new ArrayList<>());
	}

	public Integer getSecurityPosition(String securitySymbol) {
		return this.securityPosition.get(securitySymbol) ;
	}

	public void setSecurityPosition(String securitySymbol, Integer quantity) {
		this.securityPosition.put(securitySymbol,quantity);
	}

	public Position(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public Integer getNewQuantity() {
		return newQuantity;
	}

	public void setNewQuantity(Integer newQuantity) {
		this.newQuantity = newQuantity;
	}

	@Override
	public String toString() {
		return newQuantity + " " + trades + " ";
	}

}
