package com.phonecompany.billing;

import java.math.BigDecimal;

import org.junit.Assert;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TelephoneBillTest
        extends TestCase {
    public TelephoneBillTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TelephoneBillTest.class);
    }

    public void testCalculate() {
        TelephoneBill telephoneBill = new TelephoneBill();

        String phoneLog = "420774577453,13-01-2020 15:59:15,13-01-2020 16:03:57,420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57,420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00";
        BigDecimal billPrice = telephoneBill.calculate(phoneLog);
        BigDecimal expectedPrice = new BigDecimal("6.2");
        Assert.assertEquals(expectedPrice, billPrice);
    }
}
