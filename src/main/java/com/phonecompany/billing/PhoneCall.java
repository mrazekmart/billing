package com.phonecompany.billing;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class PhoneCall {

    public long phoneNumber;
    private Date startDate;
    private Date endDate;

    public PhoneCall(String _phoneNumber, String _startDate, String _endDate) {
        try {
            this.phoneNumber = Long.parseLong(_phoneNumber);
            startDate = TelephoneBillCalculator.DATE_FORMAT.parse(_startDate);
            endDate = TelephoneBillCalculator.DATE_FORMAT.parse(_endDate);

        } catch (ParseException pE) {
            System.out.println("Parsing Exception");
        } catch (NumberFormatException nfE) {
            System.out.println("Number Format Exception");
        }
    }

    /**
     * This method calcute the price of phoneCall
     *
     * @return price of phoneCall
     */
    public BigDecimal calcuteCallPrice() {
        BigDecimal callPrice = new BigDecimal("0");

        long callLengthInSeconds = (endDate.getTime() - startDate.getTime()) / 1000;
        int callLengthInMinutes = (int) callLengthInSeconds / 60;

        Calendar callTime = Calendar.getInstance();
        callTime.setTime(startDate);

        // for few first minutes until lowest tarif price is reached(5 minutes), check
        // starting time of every minute
        // and add the price to the total sum
        for (int i = 0; i <= callLengthInMinutes; i++) {
            callPrice = callPrice.add(getPricePerMinut(callTime));
            callTime.add(Calendar.MINUTE, 1);
            // after reaching the lowest tarif price, calculate the rest of the price and
            // sum it together
            if (i == TelephoneBillCalculator.MINUTES_TO_LOWEST_TARIF - 1) {
                int remainingMinutes = callLengthInMinutes - (TelephoneBillCalculator.MINUTES_TO_LOWEST_TARIF - 1);
                BigDecimal remainingPrice = new BigDecimal(TelephoneBillCalculator.LOWEST_TARIF_PRICE)
                        .multiply(new BigDecimal(remainingMinutes));
                callPrice = callPrice.add(remainingPrice);
                break;
            }
        }
        return callPrice;
    }
    /**
     * This method returns the current minute tarif price
     *
     * @param cal Calender - the time of the minute
     * @return tarif price
     */
    public BigDecimal getPricePerMinut(Calendar cal) {
        BigDecimal currentPricePerMinut;
        if (cal.get(Calendar.HOUR_OF_DAY) >= TelephoneBillCalculator.BUSINESS_START_HOUR &&
                cal.get(Calendar.HOUR_OF_DAY) < TelephoneBillCalculator.BUSINESS_END_HOUR) {
            currentPricePerMinut = new BigDecimal(TelephoneBillCalculator.BUSINESS_HOURS_PRICE);
        } else {
            currentPricePerMinut = new BigDecimal(TelephoneBillCalculator.BUSINESS_OFF_HOURS_PRICE);
        }
        return currentPricePerMinut;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PhoneCall))
            return false;
        PhoneCall otherPC = (PhoneCall) other;
        if (this.phoneNumber == otherPC.phoneNumber &&
                this.startDate.compareTo(otherPC.startDate) == 0 &&
                this.endDate.compareTo(otherPC.endDate) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, startDate, endDate);
    }
}
