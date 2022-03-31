package org.acme.model;

import java.io.Serializable;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;

public class StockItem  implements Serializable {

   JsonProvider provider = JsonProvider.provider();
   private String name;
   private double price;
   private int quantity;

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }


   public StockItem(String name, double price, int quantity) {
      this.name = name;
      this.price = price;
      this.quantity = quantity;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   @Override
   public String toString() {
      return "StockItem{" +
            "name='" + name + '\'' +
            ", price=" + price +
            ", quantity=" + quantity +
            '}';
   }

   public String toJsonString() {
      return "{\"name\":\"" + name + "\",\"price\":" + price + ",\"quantity\":" + quantity + "}";
   }

   public JsonObject toJSONObject() {
      JsonObjectBuilder objectBuilder = provider.createObjectBuilder();
      objectBuilder.add("name", name).add("price", price).add("quantity", quantity);
      return objectBuilder.build();
   }
}
