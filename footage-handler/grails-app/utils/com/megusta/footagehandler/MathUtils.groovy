package com.megusta.footagehandler

/**
 * Provides various math operations.
 */
class MathUtils {

    /**
     * Rounds number by round half up tie-breaking rule.
     *
     * @param number number to round
     * @param precision round precision
     * @return rounded number
     */
    public static double round(double number, int precision) {

        return new BigDecimal(String.valueOf(number)).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
