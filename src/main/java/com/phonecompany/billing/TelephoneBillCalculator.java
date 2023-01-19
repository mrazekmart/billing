package com.phonecompany.billing;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public interface TelephoneBillCalculator {

    public static final int MINUTES_TO_LOWEST_TARIF = 5;

    public static final String BUSINESS_HOURS_PRICE = "1.0";
    public static final String BUSINESS_OFF_HOURS_PRICE = "0.5";
    public static final String LOWEST_TARIF_PRICE = "0.2";

    public static final int BUSINESS_START_HOUR = 8;
    public static final int BUSINESS_END_HOUR = 16;

    public static final boolean IS_PROMO_ACTION = true;

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    BigDecimal calculate(String phoneLog);

}
