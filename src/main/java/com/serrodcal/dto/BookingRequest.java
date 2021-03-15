package com.serrodcal.dto;

import io.smallrye.mutiny.Uni;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingRequest {

    private String hotelId;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
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
        this.checkinDate = LocalDateTime.parse(checkinDate, formatter);
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = LocalDateTime.parse(checkoutDate, formatter);
    }

    public LocalDateTime getCheckinDate() {
        return checkinDate;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public Uni<String> isValid() {
        if (checkoutDate.isBefore(checkinDate)) {
            return Uni.createFrom().failure(new Exception("Check-out date must be strictly after the checkin date"));
        }
        if (checkinDate.isBefore(LocalDateTime.now())) {
            return Uni.createFrom().failure(new Exception("Check-in date must be today or after"));
        }

        return Uni.createFrom().item("Valid");
    }

}
