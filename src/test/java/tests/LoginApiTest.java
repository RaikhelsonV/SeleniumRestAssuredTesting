package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginApiTest {
    private static final String LOGIN_ENDPOINT = "/api/Auth/login";
    private static final String PRODUCT_ENDPOINT = "/api/Products";
    private String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://productstoresystem-production.up.railway.app";
    }

    @Test
    public void testSuccessfulLogin() {
        String requestBody = "{\"email\": \"ad@ad\", \"password\": \"ad@ad\"}";
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(LOGIN_ENDPOINT);

        token = response.jsonPath().getString("token");

        Assert.assertEquals(response.getStatusCode(), 200, "Login failed, status code is not 200");
        Assert.assertTrue(response.getBody().asString().contains("token"), "Response body doesn't contain expected value");

    }

    @Test
    public void testInvalidPassword() {
        String requestBody = "{\"email\": \"ad@ad\", \"password\": \"ad@ad\"}";
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(LOGIN_ENDPOINT);

        Assert.assertEquals(response.getStatusCode(), 401, "Expected 401 Unauthorized for wrong password");
    }

    @Test
    public void testInvalidEmail() {
        String requestBody = "{\"email\": \"val@val6\", \"password\": \"123\"}";
        Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody).post(LOGIN_ENDPOINT);
        Assert.assertEquals(response.getStatusCode(), 401, "Expected 401 Unauthorized for wrong email");
    }

    @Test
    public void testEmptyEmail() {
        String requestBody = "{\"email\": \"\", \"password\": \"123\"}";
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(LOGIN_ENDPOINT);

        Assert.assertEquals(response.getStatusCode(), 401, "Expected 401 Unauthorized for empty email");
    }

    @Test(dependsOnMethods = "testSuccessfulLogin")
    public void testGetProducts() {
        Assert.assertNotNull(token, "Token should not be null");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer" + token)
                .get(PRODUCT_ENDPOINT);

        Assert.assertEquals(response.getStatusCode(), 200, "Failed to get products, status code is not 200");
        Assert.assertTrue(response.jsonPath().getList("products").isEmpty(), "Products array is not empty");
    }

    @Test(dependsOnMethods = "testSuccessfulLogin")
    public void testAddProduct() {
        String requestBody = "{\"name\": \"Banana\", \"desc\": \"From Israel\", \"price\": 100}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .post(PRODUCT_ENDPOINT);

        Assert.assertEquals(response.getStatusCode(), 201, "Failed to add product, status code is not 201");
        Assert.assertEquals(response.jsonPath().getString("name"), "Banana");
    }


}
