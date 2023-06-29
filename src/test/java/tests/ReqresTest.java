package tests;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import reqres.User;
import reqres.UsersList;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.net.HttpURLConnection.*;

public class ReqresTest {
    @Test
    public void postCreateUserTest() {
        User user = User.builder()
                .name("morphius")
                .job("leader")
                .build();
        Response response = given()
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_CREATED);
    }

    @Test
    public void getListUsersTest() {
        String body = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .extract().body().asString();
        UsersList usersList = new Gson().fromJson(body, UsersList.class);
        String firstName = usersList.getData().get(3).getFirstName();
        System.out.println(firstName);
    }

    @Test
    public void getSingleUserTest(){
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

    @Test
    public void getSingleUserNotFoundTest() {
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    public void getListResourceTest() {
        given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

    @Test
    public void getSingleResourceTest() {
        given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

    @Test
    public void getSingleResourceNotFoundTest() {
        given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    public void putUpdateUserTest() {
        User user = User.builder()
                .name("morphius")
                .job("zion resident")
                .build();
        given()
                .body(user)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

    @Test
    public void patchUpdateUserTest() {
        User user = User.builder()
                .name("morphius")
                .job("zion resident")
                .build();
        given()
                .body(user)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

    @Test
    public void deleteUserTest() {
        given()
                .when()
                .log().all()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(HTTP_NO_CONTENT);
    }

    @Test
    public void postRegisterIsSuccessful() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

    @Test
    public void postRegisterIsUnsuccessfulTest() {
        User user = User.builder()
                .email("sydney@fife")
                .build();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    public void postLoginIsSuccessful() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .log().all()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

    @Test
    public void postLoginIsUnsuccessfulTest() {
        User user = User.builder()
                .email("peter@klaven")
                .build();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    public void getDelayedResponseTest() {
        given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }


}
