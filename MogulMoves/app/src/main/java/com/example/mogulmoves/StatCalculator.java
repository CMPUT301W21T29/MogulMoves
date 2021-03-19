package com.example.mogulmoves;

import java.util.Arrays;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Class to calculate various statistics.
 */
public class StatCalculator {

    /**
     * Calculates the median value of an array of floats.
     *
     * @return the median value
     */
    public static float getMedian(float[] values) {

        Arrays.sort(values);

        if (values.length == 0) {
            return 0;
        }

        if (values.length % 2 == 1) {
            return values[values.length / 2];

        } else {
            return (values[values.length / 2 - 1] + values[values.length / 2]) / 2;

        }
    }

    /**
     * Calculates the quartiles of an array of floats.
     *
     * @return a 2 element array representing the 1st and 3rd quartiles
     */
    public static float[] getQuartiles(float[] values) {

        Arrays.sort(values);

        float[] quartiles = {0, 0};

        if(values.length == 0) {
            return quartiles;
        }

        quartiles[0] = getMedian(Arrays.copyOfRange(values, 0, values.length / 2 + 1));
        quartiles[1] = getMedian(Arrays.copyOfRange(values, values.length / 2, values.length));

        return quartiles;
    }

    /**
     * Calculates the mean value of an array of floats.
     *
     * @return the mean value
     */
    public static float getMean(float[] values) {

        float sum = 0;

        for(float value: values) {
            sum += value;
        }

        return sum / values.length;

    }

    /**
     * Calculates the standard deviation of an array of floats.
     *
     * @return the standard deviation
     */
    public static float getStdDev(float[] values) {

        float sum = 0;
        float mean = getMean(values);

        for(float value: values) {
            sum += pow(value - mean, 2.0);
        }

        return (float) sqrt(sum / values.length);

    }
}
