package org.acme.resource;

import org.acme.jms.PriceConsumer;
import org.acme.model.StockItem;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple resource showing the last price.
 */
@Path("/stock")
public class StockResource {

    @Inject
    PriceConsumer consumer;

    @GET
    @Path("last")
    @Produces(MediaType.TEXT_PLAIN)
    public String last() {
        return consumer.getLastPrice();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllStock() {
        List<StockItem> stockItems = new ArrayList<>();
        stockItems.add(new StockItem("banana", 1.50, 22));
        stockItems.add(new StockItem("apple", 1.20, 254));
        final GenericEntity<List<StockItem>> entity = new GenericEntity<List<StockItem>>(stockItems) {};
        return Response.ok(entity).build();
    }
}
