package com.fragoso.isoleted;

import com.fragoso.config.Configurations;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasKey;

import com.fragoso.factory.LoginDataFactory;
import com.fragoso.pojo.Login;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class LoginTest {
    @Before
    public void setUp() throws IOException {
        Configurations configurations = ConfigFactory.create(Configurations.class);
        baseURI = configurations.baseUri();
    }

    @Test
    public void testLoginSucess(){
        Login getToken = LoginDataFactory.identity("eve.holt@reqres.in", "cityslicka");
        given()
            .contentType(ContentType.JSON)
            .body(getToken)
        .when()
            .post("/login")
        .then()
            .assertThat()
                .statusCode(200)
                .body("$", hasKey("token"))
            .extract()
                .path("token");
    }

    @Test
    public void testLoginInvalidEmail(){
        Login getToken = LoginDataFactory.identity("invalidEmail@invalid.com", "cityslicka");
        given()
            .contentType(ContentType.JSON)
            .body(getToken)
        .when()
            .post("/login")
        .then()
            .assertThat()
                .statusCode(400)
                .body("error", equalToIgnoringCase("user not found"));
    }

    @Test
    public void testLoginEmptyPassword(){
        Login getToken = LoginDataFactory.identity("eve.holt@reqres.in", "");
        given()
            .contentType(ContentType.JSON)
            .body(getToken)
        .when()
            .post("/login")
        .then()
            .assertThat()
                .statusCode(400)
                .body("error", equalToIgnoringCase("Missing password"));
    }
}
