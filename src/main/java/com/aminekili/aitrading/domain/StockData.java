package com.aminekili.aitrading.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockData {

    private double minute;

    private double wap;
    private double tradeCount;
    private double volume;

    private double tesla3;
    private double tesla6;
    private double tesla9;

    private int decision; // 1 = buy, -1 = sell, 0 = hold
    private int execute; // 1 = execute, 0 = don't execute

}
