package com.phonecompany.billing;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Assert;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PhoneCallTest extends TestCase {
    public PhoneCallTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(PhoneCallTest.class);
    }

    public void testCalculateCallPrice() {
        String phoceCallVoice = "420774577453,13-01-2020 15:59:15,13-01-2020 16:03:57";
        String[] phoneCallDate = phoceCallVoice.split(",");
        PhoneCall phoneCall = new PhoneCall(phoneCallDate[0], phoneCallDate[1], phoneCallDate[2]);
        BigDecimal phoneCallPrice = phoneCall.calcuteCallPrice();
        BigDecimal expectedCallPrice = new BigDecimal("3.0");
        Assert.assertEquals(expectedCallPrice, phoneCallPrice);
    }

    public void testGetPricePerMinut() {

        String phoceCallVoice = "420774577453,13-01-2020 15:59:15,13-01-2020 16:03:57";
        String[] phoneCallDate = phoceCallVoice.split(",");
        PhoneCall phoneCall = new PhoneCall(phoneCallDate[0], phoneCallDate[1], phoneCallDate[2]);

        Calendar cal1 = Calendar.getInstance();
        cal1.set(2023, 1, 1, 8, 33, 25);
        BigDecimal expectedMinutePrice = new BigDecimal("1.0");
        BigDecimal minutePrice = phoneCall.getPricePerMinut(cal1);
        Assert.assertEquals(expectedMinutePrice, minutePrice);

        cal1.set(2023, 1, 1, 16, 0, 0);
        expectedMinutePrice = new BigDecimal("0.5");
        minutePrice = phoneCall.getPricePerMinut(cal1);
        Assert.assertEquals(expectedMinutePrice, minutePrice);
    }
}
