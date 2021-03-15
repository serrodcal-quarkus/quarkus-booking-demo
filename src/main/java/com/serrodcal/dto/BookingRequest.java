package com.serrodcal.dto;

import com.ibm.asyncutil.util.Either;
import io.smallrye.mutiny.Uni;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingRequest {

    private String hotelId;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public BookingRequest() {
    }

    @Override
    public String toString() {
        return "BookingRequest hotelId=" + this.hotelId +
                " checkinDate=" + this.checkinDate +
                " checkoutDate=" + this.checkoutDate;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = LocalDate.parse(checkinDate, formatter);
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = LocalDate.parse(checkoutDate, formatter);
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public Uni<String> isValid() {
        if (checkoutDate.isBefore(checkinDate)) {
            return Uni.createFrom().failure(new Exception("Check-out date must be strictly after the checkin date"));
        }
        if (checkinDate.isBefore(LocalDate.now())) {
            return Uni.createFrom().failure(new Exception("Check-in date must be today or after"));
        }

        return Uni.createFrom().item("Valid");
    }

}
