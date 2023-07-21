package tests;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import reqres.ResourcesList;
import reqres.User;
import reqres.UsersList;

import static io.restassured.RestAssured.*;
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
    public void verifyThatUserExistsTest() {
        String body = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .extract().body().asString();
        UsersList usersList = new Gson().fromJson(body, UsersList.class);
        String firstName = usersList.getData().get(3).getFirstName();
        Assert.assertEquals(firstName, "Byron");
    }

    @Test
    public void checkSingleUserTest(){
        Response response = given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void verifyThatUserNotFoundTest() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NOT_FOUND);
    }

    @Test
    public void checkNumberOfResourcesTest() {
        String body = given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .extract().body().asString();
        ResourcesList resourcesList = new Gson().fromJson(body, ResourcesList.class);
        int numberOfResources = resourcesList.getData().size();
        Assert.assertEquals(numberOfResources, 6);
    }

    @Test
    public void checkSingleResourceTest() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().all()
                .extract().response();
      Assert.assertEquals(response.statusCode(), HTTP_OK);

    }

    @Test
    public void verifyThatResourceNotFoundTest() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NOT_FOUND);
    }

    @Test
    public void checkPutUpdateUserTest() {
        User user = User.builder()
                .name("morphius")
                .job("zion resident")
                .build();
       Response response = given()
                .body(user)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
       Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void checkPatchUpdateUserTest() {
        User user = User.builder()
                .name("morphius")
                .job("zion resident")
                .build();
        Response response = given()
                .body(user)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void checkDeleteUserTest() {
        Response response = given()
                .when()
                .log().all()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NO_CONTENT);
    }

    @Test
    public void checkRegisterNewUserIsSuccessfulTest() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void CheckRegisterIsUnsuccessfulTest() {
        User user = User.builder()
                .email("sydney@fife")
                .build();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
    }

    @Test
    public void checkLoginIsSuccessfulTest() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .log().all()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void checkLoginIsUnsuccessfulTest() {
        User user = User.builder()
                .email("peter@klaven")
                .build();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
    }

    @Test
    public void checkNumberOfDelayedUsersTest() {
        String body = given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log().all()
                .extract().asString();
        UsersList usersList = new Gson().fromJson(body, UsersList.class);
        int numberOfUsers = usersList.getData().size();
        Assert.assertEquals(numberOfUsers, 6);
    }


}
