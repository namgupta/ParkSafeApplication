package com.example.ngupta1.parksafe;

/**
 * Created by ngupta1 on 16/10/16.
 */
public class Location {

    String booking_reference;
    String location_name;
    String endDate;
    String booking_status;
    String booking_time;
    String startDate;
    String pendingAmount;
    String paidTill;
    String dueDate;
    String rent;

    String address;
    String reference;

    public String getBookingReference() { return booking_reference;  }

    public String getLocationName() { return location_name;  }

    public String getStartdDate() { return startDate;  }

    public String getEndDate() { return endDate;  }

    public String getRent() { return rent;  }

    public String getBookingTime() { return booking_time;  }

    public String getBookingStatus() { return booking_status;  }

    public String getPendingAmount() { return pendingAmount;  }

    public String getPaidTill() { return paidTill;  }

    public String getDueDate() { return dueDate;  }




    public String getAddress() { return address;  }

    public String getReference() { return reference;  }

    public void setAddress(String address){ this.address = address;  }

    public void setReference(String reference){ this.reference = reference;  }




    public void setBookingReference(String booking_reference){ this.booking_reference = booking_reference;  }

    public void setLocationName(String location_name){
        this.location_name = location_name;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public void setRent(String rent){
        this.rent = rent;
    }

    public void setBookingTime(String booking_time){
        this.booking_time = booking_time;
    }

    public void setBookingStatus(String booking_status){
        this.booking_status = booking_status;
    }

    public void setPendingAmount(String pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public void setPaidTill(String paidTill) {
        this.paidTill = paidTill;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
