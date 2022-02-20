package com.jpmc.realtimepositionsystem;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jpmc.realtimepositionsystem.domain.Position;
import com.jpmc.realtimepositionsystem.service.TradeExecutionService;
import com.jpmc.realtimepositionsystem.domain.TradeType;

public class PositionBookTest {

	private Map<String, Position> accountPositionsMap;
	private TradeExecutionService executionService;

	@Before
	public void init() {
		accountPositionsMap = new HashMap<String, Position>();
		executionService = new TradeExecutionService();
	}


	//Example 1: buying securities
	@Test
	public void buySecurityForSameAccount() throws Exception {
		accountPositionsMap = executionService.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		assertEquals("ACC1", accountPositionsMap.get("ACC1").getAccount().toString());
		assertEquals(new Integer(12), accountPositionsMap.get("ACC1").getCurrentQuantity());
	}

	//Example 2: buying different securities
	@Test
	public void buyMultipleSecuritiesForSameAccount() throws Exception {
		accountPositionsMap = executionService.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = executionService.executeTrade(2, TradeType.BUY, "ACC1", "SEC1", 12);
		assertEquals("ACC1", accountPositionsMap.get("ACC1").getAccount().toString());
		assertEquals(new Integer(24), accountPositionsMap.get("ACC1").getCurrentQuantity());
	}


	//Example 3: buying and selling securities
	@Test
	public void buyAndSellSecuritiesForSameAccount() throws Exception {
		accountPositionsMap = executionService.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = executionService.executeTrade(2, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = executionService.executeTrade(3, TradeType.SELL, "ACC1", "SEC1", 12);
		assertEquals("ACC1", accountPositionsMap.get("ACC1").getAccount().toString());
		assertEquals(new Integer(12), accountPositionsMap.get("ACC1").getCurrentQuantity());
	}

	@Test
	public void buySecuritiesForMultipleAccount() throws Exception {
		accountPositionsMap = executionService.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = executionService.executeTrade(2, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = executionService.executeTrade(1, TradeType.BUY, "ACC2", "SEC2", 20);
		accountPositionsMap = executionService.executeTrade(2, TradeType.BUY, "ACC2", "SEC1", 12);
		assertEquals(new Integer(24), accountPositionsMap.get("ACC1").getCurrentQuantity());
	}

	@Test
	public void buyAndSellSecuritiesForMultipleAccount() throws Exception {
		accountPositionsMap = executionService.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = executionService.executeTrade(2, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = executionService.executeTrade(3, TradeType.SELL, "ACC1", "SEC1", 12);
		accountPositionsMap = executionService.executeTrade(4, TradeType.BUY, "ACC2", "SEC234", 30);
		assertEquals("ACC1", accountPositionsMap.get("ACC1").getAccount().toString());
		assertEquals("ACC2", accountPositionsMap.get("ACC2").getAccount().toString());
		assertEquals(new Integer(12), accountPositionsMap.get("ACC1").getCurrentQuantity());
		assertEquals(new Integer(30), accountPositionsMap.get("ACC2").getCurrentQuantity());
	}

	//Example 4: cancelling events
	@Test
	public void buyAndCancelSecuritiesThenBuySecuritiesForSameAccount() throws Exception {
		accountPositionsMap = executionService.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 100);
		accountPositionsMap = executionService.executeTrade(2, TradeType.CANCEL, "ACC1", "SEC1", 0);
		accountPositionsMap = executionService.executeTrade(3, TradeType.BUY, "ACC1", "SEC1", 5);
		assertEquals(new Integer(5), accountPositionsMap.get("ACC1").getCurrentQuantity());
	}

	//Example 5: error events
	@Test
	public void sellSecuritiesWhichDoesNotExist() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			executionService.executeTrade(1, TradeType.SELL, "ACC1", "SEC1", 12);
		});
		assertEquals("Security not found", exception.getMessage());
	}

	@Test
	public void buySecurityWithDuplicateId() throws Exception {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			executionService.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
			executionService.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		});
		assertEquals("Invalid  ID: 1. It already exists!", exception.getMessage());
	}

}
