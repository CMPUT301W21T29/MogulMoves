package com.example.mogulmoves;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatsTest {

    @Test
    public void statsTest() {

        float[] values = {1, 3, 4, 5, 7, 7, 10, 24};

        assertEquals(7.625, StatCalculator.getMean(values), 0.001);
        assertEquals(6, StatCalculator.getMedian(values), 0.001);
        assertEquals(3.5, StatCalculator.getQuartiles(values)[0], 0.001);
        assertEquals(8.5, StatCalculator.getQuartiles(values)[1], 0.001);
        assertEquals(6.707, StatCalculator.getStdDev(values), 0.001);

    }

}
