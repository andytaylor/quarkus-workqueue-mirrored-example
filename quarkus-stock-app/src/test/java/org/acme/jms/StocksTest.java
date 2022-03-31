package org.acme.jms;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.smallrye.common.constraint.Assert;
import org.acme.model.StockItem;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

import javax.json.JsonObject;
import java.util.logging.Logger;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
public class StocksTest {

    private Logger logger = Logger.getLogger(StocksTest.class.getName());

    @Test
    public void getStock() throws Exception {
        assertTrue(Wait.waitFor(() -> {
            return RestAssured.given().when().get("/stock/all").getStatusCode() == 200;
        }, 10000, 25), "Stock didn't became available in allotted time");

        RestAssured.given()
                .when().get("/stock/all")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("[{\"name\":\"banana\",\"price\":1.5,\"quantity\":222},{\"name\":\"apple\",\"price\":1.2,\"quantity\":254}]"));
    }

    @Test
    public void updateStock() throws Exception {
        StockItem stockItem = new StockItem("banana", 1.50, 222);
        JsonObject requestParams = stockItem.toJSONObject();
        logger.info(stockItem.toJsonString());
        Response response = RestAssured
              .given()
              .header("Content-type", "application/json")
              .and()
              .body(stockItem.toJsonString())
              .when()
              .put("/stock/update/")
              .then()
              .extract().response();

        int statusCode = response.getStatusCode();
        logger.info(response.getStatusCode() + ":" + response.getStatusLine());
        Assert.assertTrue(statusCode == 200);
    }
}
