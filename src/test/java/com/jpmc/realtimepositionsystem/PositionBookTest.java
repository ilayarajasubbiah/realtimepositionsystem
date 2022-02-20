package com.jpmc.realtimepositionsystem;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jpmc.realtimepositionsystem.tradeentities.Position;
import com.jpmc.realtimepositionsystem.service.TradeExecutionService;
import com.jpmc.realtimepositionsystem.tradeentities.TradeType;

public class PositionBookTest {

	private Map<String, Position> accountPositionsMap;
	private TradeExecutionService execution;

	@Before
	public void init() {
		accountPositionsMap = new HashMap<String, Position>();
		execution = new TradeExecutionService();
	}

	@Test
	public void buySecuritiesForSameAccount() throws Exception {
		accountPositionsMap = execution.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		assertEquals("ACC1", accountPositionsMap.get("ACC1").getAccount().toString());
		assertEquals(new Integer(12), accountPositionsMap.get("ACC1").getNewQuantity());
	}

	@Test
	public void buySecuritiesThenAgainBuySecuritiesForSameAccount() throws Exception {
		accountPositionsMap = execution.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = execution.executeTrade(2, TradeType.BUY, "ACC1", "SEC1", 12);
		assertEquals("ACC1", accountPositionsMap.get("ACC1").getAccount().toString());
		assertEquals(new Integer(24), accountPositionsMap.get("ACC1").getNewQuantity());
	}

	@Test
	public void buySecuritiesThenSellAndUpdateSecuritiesForSameAccount() throws Exception {
		accountPositionsMap = execution.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = execution.executeTrade(2, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = execution.executeTrade(3, TradeType.SELL, "ACC1", "SEC1", 12);
		assertEquals("ACC1", accountPositionsMap.get("ACC1").getAccount().toString());
		assertEquals(new Integer(12), accountPositionsMap.get("ACC1").getNewQuantity());
	}

	@Test
	public void buySecuritiesThenAgainBuySecuritiesForMultipleAccount() throws Exception {
		accountPositionsMap = execution.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = execution.executeTrade(2, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = execution.executeTrade(1, TradeType.BUY, "ACC2", "SEC2", 20);
		accountPositionsMap = execution.executeTrade(2, TradeType.BUY, "ACC2", "SEC1", 12);

		assertEquals(new Integer(24), accountPositionsMap.get("ACC1").getNewQuantity());
	}

	
	@Test
	public void buySecuritiesThenSellAndUpdateSecuritiesForMultipleAccount() throws Exception {
		accountPositionsMap = execution.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = execution.executeTrade(2, TradeType.BUY, "ACC1", "SEC1", 12);
		accountPositionsMap = execution.executeTrade(3, TradeType.SELL, "ACC1", "SEC1", 12);
		accountPositionsMap = execution.executeTrade(4, TradeType.BUY, "ACC2", "SEC234", 30);
		assertEquals("ACC1", accountPositionsMap.get("ACC1").getAccount().toString());
		assertEquals("ACC2", accountPositionsMap.get("ACC2").getAccount().toString());
		assertEquals(new Integer(12), accountPositionsMap.get("ACC1").getNewQuantity());
		assertEquals(new Integer(30), accountPositionsMap.get("ACC2").getNewQuantity());

	}

	@Test
	public void sellSecuritiesWhichDoesNotExist() {

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			execution.executeTrade(1, TradeType.SELL, "ACC1", "SEC1", 12);
		});
		assertEquals("Security not found", exception.getMessage());

	}

	@Test
	public void buySecurityWithDuplicateId() throws Exception {

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			execution.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
			execution.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 12);
		});
		assertEquals("Invalid  ID: 1. It already exists!", exception.getMessage());

	}

	
	@Test
	public void buyNewSecuritiesCancelItAndBuyForSameAccount() throws Exception {
		accountPositionsMap = execution.executeTrade(1, TradeType.BUY, "ACC1", "SEC1", 100);
		accountPositionsMap = execution.executeTrade(2, TradeType.CANCEL, "ACC1", "SEC1", 0);
		accountPositionsMap = execution.executeTrade(3, TradeType.BUY, "ACC1", "SEC1", 5);
		assertEquals(new Integer(5), accountPositionsMap.get("ACC1").getNewQuantity());
	}

}
