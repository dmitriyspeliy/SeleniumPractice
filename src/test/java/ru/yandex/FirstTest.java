package ru.yandex;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FirstTest {
    @Test
    public void fisrtTest(){

        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page",notNullValue())
                .body("per_page", notNullValue())
                .body("total", notNullValue())
                .body("total_pages", notNullValue())
                .body("data.id",not(hasItem(nullValue())))
                .body("data.first_name",hasItem("Lindsay"));

    }
}
