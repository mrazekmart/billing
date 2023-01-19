package com.phonecompany.billing;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PhoneLogTest extends TestCase {
    public PhoneLogTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(PhoneLogTest.class);
    }

    public void testParsePhoneLog() {
        String phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57,420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00";
        PhoneLog phoneLogInstance = new PhoneLog(phoneLog);
        phoneLogInstance.parsePhoneLog();

        PhoneCall phoneCallFirst = new PhoneCall("420774577453", "13-01-2020 18:10:15", "13-01-2020 18:12:57");
        PhoneCall phoneCallSecond = new PhoneCall("420776562353", "18-01-2020 08:59:20", "18-01-2020 09:10:00");
        List<PhoneCall> phoneCallExpectedList = new ArrayList<PhoneCall>();
        phoneCallExpectedList.add(phoneCallFirst);
        phoneCallExpectedList.add(phoneCallSecond);

        int i = 0;
        boolean listAreEqual = true;
        for (PhoneCall phoneCall : phoneCallExpectedList) {

            if (!phoneCall.equals(phoneLogInstance.phoneCalls.get(i))) {
                listAreEqual = false;
                break;
            }
            i++;
        }
        Assert.assertEquals(true, listAreEqual);
    }

    public void testGetPhoneNumberForFree() {
        String phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57,420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00";
        String phoneLog2 = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57,420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57,420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00";
        PhoneLog phoneLogInstance = new PhoneLog(phoneLog);
        PhoneLog phoneLogInstance2 = new PhoneLog(phoneLog2);
        phoneLogInstance.parsePhoneLog();
        phoneLogInstance2.parsePhoneLog();

        Long numberForFree1 = phoneLogInstance.getPhoneNumberForFree();
        Long numberForFree2 = phoneLogInstance2.getPhoneNumberForFree();
        Long expectedNumberForFree1 = 420776562353L;
        Long expectedNumberForFree2 = 420774577453L;

        Assert.assertEquals(numberForFree1, expectedNumberForFree1);
        Assert.assertEquals(numberForFree2, expectedNumberForFree2);
    }

    public void testGetPhoneLogPrice() {
        String phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57,420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00";
        PhoneLog phoneLogInstance = new PhoneLog(phoneLog);
        phoneLogInstance.parsePhoneLog();
        BigDecimal phoneLogPrice = phoneLogInstance.getPhoneLogPrice();
        BigDecimal expectedPrice = new BigDecimal("1.5");
        Assert.assertEquals(expectedPrice, phoneLogPrice);
    }
}
