package com.serrodcal.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
public class Booking extends PanacheEntityBase {

    @Id
    public Long id;

    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID bookingRef;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hotelId", referencedColumnName = "hotelId")
    public Hotel hotel;

    @NotNull
    @Temporal(TemporalType.DATE)
    public Date checkinDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    public Date checkoutDate;

    @javax.validation.constraints.Pattern(regexp = "^\\d{16}$", message = "Credit card number must be 16 digits")
    public String creditCard;

    public Booking() {
    }

    public BigDecimal getTotal() {
        return hotel.price.multiply(new BigDecimal(getNights()));
    }

    public int getNights() {
        return (int) (checkoutDate.getTime() - checkinDate.getTime()) / 1000 / 60 / 60 / 24;
    }

    public String getDescription() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return hotel == null ? null : hotel.name +
                ", " + df.format(checkinDate) +
                " to " + df.format(checkoutDate);
    }

}
