package com.example.ngupta1.parksafe;

/**
 * Created by ngupta1 on 16/10/16.
 */
public class Payment {

    String payment_reference;
    String booking_reference;
    String location;
    String amount;
    String startDate;
    String endDate;
    String payment_time;


    public String getPaymentReference() { return payment_reference;  }

    public String getBookingReference() { return booking_reference;  }

    public String getLocationName() { return location;  }

    public String getStartDate() { return startDate;  }

    public String getEndDate() { return endDate;  }

    public String getPaymentTime() { return payment_time;  }

    public String getAmount() { return amount;  }


    public void setPaymentReference(String payment_reference){ this.payment_reference = payment_reference;  }

    public void setBookingReference(String booking_reference){ this.booking_reference = booking_reference;  }

    public void setLocation(String location){
        this.location = location;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public void setPaymentTime(String payment_time){
        this.payment_time = payment_time;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

}
