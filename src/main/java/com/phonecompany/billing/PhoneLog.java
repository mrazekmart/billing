package com.phonecompany.billing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map.Entry;

public class PhoneLog {

    public String phoneLog;
    public List<PhoneCall> phoneCalls;

    public PhoneLog(String phoneLog) {
        this.phoneLog = phoneLog;
        phoneCalls = new ArrayList<PhoneCall>();
    }

    /**
     * This method parses phoneLog into List of PhoneCall Objects
     *
     */
    public void parsePhoneLog() {
        String[] splitPhoneLog = this.phoneLog.split(",");
        for (int i = 0; i < splitPhoneLog.length; i += 3) {
            PhoneCall phoneCall = new PhoneCall(splitPhoneLog[i], splitPhoneLog[i + 1], splitPhoneLog[i + 2]);
            phoneCalls.add(phoneCall);
        }
    }

    /**
     * This method calculates the price of the PhoneLog
     *
     * @return price
     */
    public BigDecimal getPhoneLogPrice() {
        BigDecimal finalPrice = new BigDecimal(0);

        Long calledNumberForFree = 0L;
        // if there is promo action, get number called the most to make it free of
        // charge
        if (TelephoneBillCalculator.IS_PROMO_ACTION) {
            calledNumberForFree = getPhoneNumberForFree();
        }
        // iterate through all phonecalls
        for (PhoneCall phoneCall : phoneCalls) {
            // if phonecall number is marked as free, skip
            if (phoneCall.phoneNumber == calledNumberForFree)
                continue;
            // sum the price of the log
            finalPrice = finalPrice.add(phoneCall.calcuteCallPrice());
        }
        return finalPrice;
    }

    /**
     * This method finds the number which was called the most, if multiple numbers -
     * pick the one with highest value
     *
     * @return number which is free of charge
     */
    public Long getPhoneNumberForFree() {

        HashMap<Long, Integer> phoneNumberCalledCount = new HashMap<>();

        // highest number of how many times a number was called
        int highestValue = 1;

        // saving how many times every number was called
        for (PhoneCall phoneCall : phoneCalls) {
            int numberCalled = 1;
            if (phoneNumberCalledCount.containsKey(phoneCall.phoneNumber)) {
                numberCalled = phoneNumberCalledCount.get(phoneCall.phoneNumber) + 1;
                if (numberCalled > highestValue)
                    highestValue = numberCalled;
            }
            phoneNumberCalledCount.put(phoneCall.phoneNumber, numberCalled);
        }

        // list of mostCalledNumbers
        List<Long> mostCalledNumbers = new ArrayList<>();
        for (Entry<Long, Integer> entry : phoneNumberCalledCount.entrySet()) {
            if (entry.getValue() == highestValue) {
                mostCalledNumbers.add(entry.getKey());
            }
        }

        // if there is more than one, picking the number with highest value
        long highestPhoneNumber = Long.MIN_VALUE;
        for (Long calledNumber : mostCalledNumbers) {
            if (calledNumber > highestPhoneNumber) {
                highestPhoneNumber = calledNumber;
            }
        }

        return highestPhoneNumber;
    }
}
