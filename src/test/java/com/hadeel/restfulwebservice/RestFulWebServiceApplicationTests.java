package com.hadeel.restfulwebservice;

import com.hadeel.restfulwebservice.user.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RestFulWebServiceApplicationTests {

    @Test
    void getUser() {
        when().
                get("/users/1").
                then().
                contentType(JSON).
                body("id", equalTo(1)).
                body("name", equalTo("Hadeel"));
    }

    @Test
    void createUser(){
        RequestSpecification request = given();
        request.header("content-type", MediaType.APPLICATION_JSON_VALUE);
        request.body(new User(1, "mike", new Date()));
        Response response = request.post("/users").andReturn();
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        String location = response.getHeader("location");
        assertTrue(String.format("%s should end with /users/1", location), location.endsWith("/users/1"));
    }

}
