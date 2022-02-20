package com.jpmc;

import com.jpmc.realtimepositionsystem.service.TradeExecutionService;
import com.jpmc.realtimepositionsystem.tradeentities.TradeType;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        // TODO Auto-generated method stub
        TradeExecutionService execution = new TradeExecutionService();

        System.out.println(execution.executeTrade(21, TradeType.BUY, "ACC1", "SEC1", 100));
        //System.out.println(execution.executeTrade(21, TradeType.CANCEL, "ACC1", "SEC1", 0));
        System.out.println(execution.executeTrade(22, TradeType.BUY, "ACC1", "SEC1", 5));
        //System.out.println(execution.executeTrade(4, TradeType.BUY, "ACC1", "SEC1", 20));

    }

}
