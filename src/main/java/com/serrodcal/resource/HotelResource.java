package com.serrodcal.resource;

import com.serrodcal.model.Hotel;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.panache.common.Sort;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.jboss.logging.Logger;

import javax.inject.Singleton;

@Singleton
public class HotelResource {

    private static final Logger log = Logger.getLogger(HotelResource.class);

    @Route(path = "/hotels", methods = HttpMethod.GET, produces = "application/json")
    public void get(RoutingContext rc) {
        log.info("HotelResource.get()");
        Hotel.listAll(Sort.by("name")).subscribe().with(
            result -> rc.response().setStatusCode(HttpResponseStatus.OK.code()).end(Json.encode(result)),
            failure -> rc.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end(failure.getMessage())
        );
    }

    @Route(path = "/hotels/:hotelId", methods = HttpMethod.GET, produces = "application/json")
    public void getDetails(RoutingContext rc, @Param("hotelId") String hotelId) {
        log.info("HotelResource.getDetails(hotelId = "+ hotelId +")");
        Hotel.find("hotelId", hotelId).firstResult().subscribe().with(
                result -> rc.response().setStatusCode(HttpResponseStatus.OK.code()).end(Json.encode(result)),
                failure -> rc.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end(failure.getMessage())
        );
    }

}
