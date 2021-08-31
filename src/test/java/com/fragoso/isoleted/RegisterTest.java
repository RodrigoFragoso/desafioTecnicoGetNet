package com.fragoso.isoleted;

import com.fragoso.config.Configurations;
import com.fragoso.factory.RegisterDataFactory;
import com.fragoso.pojo.Register;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;

public class RegisterTest {
    @Before
    public void setUp() throws IOException{
        Configurations configurations = ConfigFactory.create(Configurations.class);
        baseURI = configurations.baseUri();
    }

    @Test
    public void testRegisterUser() throws IOException {
        Register register = RegisterDataFactory.registerUser("eve.holt@reqres.in", "pistol");
        given()
            .contentType(ContentType.JSON)
            .body(register)
        .when()
            .post("/register")
        .then()
            .assertThat()
                .statusCode(200);
    }

    @Test
    public void testRegisterUserNotExist() throws IOException {
        Register register = RegisterDataFactory.registerUser("rpf.info@gmail.com", "teste@123");
        given()
            .contentType(ContentType.JSON)
            .body(register)
        .when()
            .post("/register")
        .then()
            .assertThat()
                .statusCode(400);
    }

    @Test
    public void testRegisterUserEmptyEmail() throws IOException {
        Register register = RegisterDataFactory.registerUser("", "pistol");
        given()
            .contentType(ContentType.JSON)
            .body(register)
        .when()
            .post("/register")
        .then()
            .assertThat()
                .statusCode(400);
    }

    @Test
    public void testRegisterUserEmptyPassword() throws IOException {
        Register register = RegisterDataFactory.registerUser("eve.holt@reqres.in", "");
        given()
            .contentType(ContentType.JSON)
            .body(register)
        .when()
            .post("/register")
        .then()
            .assertThat()
                .statusCode(400);
    }

}
