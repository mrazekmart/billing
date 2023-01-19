package com.phonecompany.billing;

import java.math.BigDecimal;

public class TelephoneBill implements TelephoneBillCalculator {
    public TelephoneBill() {
    }

    /**
     * This method returns a price of a phoneLog
     *
     * @param phoneLog phone log
     * @return price
     */
    public BigDecimal calculate(String phoneLog) {

        PhoneLog phoneLogInstance = new PhoneLog(phoneLog);
        phoneLogInstance.parsePhoneLog();

        return phoneLogInstance.getPhoneLogPrice();
    }
}
