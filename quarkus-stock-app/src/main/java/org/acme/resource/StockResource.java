package org.acme.resource;

import org.acme.jms.StockClient;
import org.acme.model.StockItem;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple resource showing the last price.
 */
@Path("/stock")
public class StockResource {
    Map<String, StockItem> items = new HashMap<>();

    public StockResource() {
       StockItem banana = new StockItem("banana", 1.50, 22);
       StockItem apple = new StockItem("apple", 1.20, 254);
       items.put(banana.getName(), banana);
       items.put(apple.getName(), apple);
    }

    @Inject
    StockClient stockClient;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllStock() {
        final GenericEntity<Collection<StockItem>> entity = new GenericEntity<>(items.values()) {};
        return Response.ok(entity).build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStock(StockItem updatedStockItem) {
        System.out.println("updating stock item: " + updatedStockItem);
        items.put(updatedStockItem.getName(), updatedStockItem);
        return Response.ok(updatedStockItem).build();
    }
}
