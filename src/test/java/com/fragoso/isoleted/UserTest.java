package com.fragoso.isoleted;

import com.fragoso.config.Configurations;
import com.fragoso.factory.LoginDataFactory;
import com.fragoso.factory.UserDataFactory;
import com.fragoso.pojo.Login;
import com.fragoso.pojo.User;
import com.github.javafaker.Faker;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Locale;

public class UserTest {
    Faker faker = new Faker(new Locale("pt-BR"));
    String name = faker.name().firstName();
    String job = faker.job().title();
    Integer id = 1;
    private String token;

    @Before
    public void setUp() throws IOException {
        Configurations configurations = ConfigFactory.create(Configurations.class);
        baseURI = configurations.baseUri();
        String email = "eve.holt@reqres.in";
        String password = "cityslicka";

        Login getToken = LoginDataFactory.identity(email, password);
        this.token = given()
            .contentType(ContentType.JSON)
            .body(getToken)
        .when()
            .post("/login")
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("$", hasKey("token"))
            .extract()
                .path("token");
    }

    @Test
    public void testCreateUser() throws IOException {
        User newUser = UserDataFactory.createUser();
        given()
            .contentType(ContentType.JSON)
            .header("Authorization", token)
        .when()
            .body(newUser)
            .post("/users")
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("$", hasKey("id"))
                .body("$", hasKey("createdAt"))
                .body("name", equalTo("Rodrigo Fragoso"))
                .body("job", equalTo("QA Engineer"));
    }

    @Test
    public void testCreateUserRandom() throws IOException {
        User newUserRandom = UserDataFactory.createUserRandom(name, job);
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(newUserRandom)
            .post("/users")
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("$", hasKey("id"))
                .body("$", hasKey("createdAt"))
                .body("name", equalTo(name))
                .body("job", equalTo(job));
    }

    @Test
    public void testCreateDuplicateUser() throws IOException {
        User userDuplicate = UserDataFactory.createUserRandom("Rodrigo Fragoso", "QA Engineer");
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(userDuplicate)
            .post("/users")
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("$", hasKey("id"))
                .body("$", hasKey("createdAt"))
                .body("name", equalTo("Rodrigo Fragoso"))
                .body("job", equalTo("QA Engineer"));
    }

    @Test
    public void testCreateUserEmptyName() throws IOException {
        User user = UserDataFactory.createUserEmptyName("", job);
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(user)
            .post("/users")
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("$", hasKey("id"))
                .body("$", hasKey("createdAt"))
                .body("name", equalTo(""))
                .body("job", equalTo(job));
    }

    @Test
    public void testCreateUserEmptyJob() throws IOException {
        User user = UserDataFactory.createUserEmptyJob(name, "");
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(user)
            .post("/users")
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("$", hasKey("id"))
                .body("$", hasKey("createdAt"))
                .body("name", equalTo(name))
                .body("job", equalTo(""));
    }

    @Test
    public void testCreateUserEmptyBody() throws IOException {
        given()
            .contentType(ContentType.JSON)
        .when()
            .body("")
            .post("/users")
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("$", hasKey("id"))
                .body("$", hasKey("createdAt"));
    }

    @Test
    public void testUpdateUser() throws IOException {
        User user = UserDataFactory.updateUser();
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(user)
            .put("/users/"+id)
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("Rodrigo Fragoso"))
                .body("job", equalTo("Test Engineer"))
                .body("$", hasKey("updatedAt"));
    }

    @Test
    public void testListUserById() throws IOException {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/users/"+id)
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", equalTo(1))
                .body("data", hasKey("email"))
                .body("data", hasKey("first_name"))
                .body("data", hasKey("last_name"))
                .body("data.avatar", containsString(".jpg"));
    }

    @Test
    public void testListUserByInvalidId() throws IOException {
        Integer id = 23;
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/users/"+id)
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("isEmpty()", Matchers.is(true));
    }

    @Test
        public void testDeleteUser() throws IOException {
        given()
            .contentType(ContentType.JSON)
        .when()
            .delete("/users/"+id)
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
