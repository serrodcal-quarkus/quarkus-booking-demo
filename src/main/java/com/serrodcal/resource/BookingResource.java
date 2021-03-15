package com.serrodcal.resource;

import com.serrodcal.dto.BookingRequest;
import com.serrodcal.model.Booking;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.jboss.logging.Logger;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class BookingResource {

    private static final Logger log = Logger.getLogger(BookingResource.class);

    @Route(path = "/bookings/hotel/:id", methods = HttpMethod.GET, produces = "application/json")
    public void allForHotel(RoutingContext rc, @Param("id") String id) {
        log.info("BookingResource.allForHotel(id = "+ id +")");
        Booking.stream("hotelId", id)
            .collect().asList()
            .subscribe().with(
                result -> rc.response().setStatusCode(HttpResponseStatus.OK.code()).end(Json.encode(result)),
                failure -> rc.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end(failure.getMessage())
            );
    }

    @Route(path = "/bookings/hotel/:hotelId/counter", methods = HttpMethod.GET, produces = "application/json")
    public void counterForHotel(RoutingContext rc, @Param("hotelId") String hotelId) {
        log.info("BookingResource.counterForHotel(hotelId = "+ hotelId +")");
        Booking.count("hotelId",hotelId).subscribe().with(
            result -> rc.response().setStatusCode(HttpResponseStatus.OK.code()).end(Json.encode(result)),
            failure -> rc.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end(failure.getMessage())
        );
    }

    @Route(path = "/bookings/ref/:bookingRef", methods = HttpMethod.GET, produces = "application/json")
    public void findByReference(RoutingContext rc, @Param("bookingRef") String bookingRef) {
        log.info("BookingResource.findByReference(bookingRef = "+ bookingRef +")");
        Booking.find("bookingRef = ?1", bookingRef).firstResult().subscribe().with(
            result -> rc.response().setStatusCode(HttpResponseStatus.OK.code()).end(Json.encode(result)),
            failure -> rc.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end(failure.getMessage())
        );
    }

    @Route(path = "/bookingService/hotel/:hotelId", methods = HttpMethod.POST, consumes = "application/json", produces = "application/json")
    public void create(RoutingContext rc, @Param("hotelId") String hotelId, @Body BookingRequest bookingRequest) {
        log.info("BookingResource.create(hotelId = "+ hotelId +")");
        bookingRequest.isValid().subscribe().with(
            result -> rc.response().setStatusCode(HttpResponseStatus.OK.code()).end(result),
            failure -> rc.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end(failure.getMessage())
        );
    }

}
