package tests;

import com.google.gson.Gson;
import head_hunter.VacanciesList;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class HeadHunter {

    @Test
    public void getVacanciesTest() {
    given()
            .when()
            .get("https://api.hh.ru/vacancies?text=QA automation")
            .then()
            .log().all()
            .statusCode(HTTP_OK);
    }

    @Test
    public void vacancyTest() {
       String body = given()
                .when()
                .get("https://api.hh.ru/vacancies?text=QA automation")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .extract().body().asString();

        VacanciesList vacanciesList = new Gson().fromJson(body, VacanciesList.class);
        int salaryTo = vacanciesList.getItems().get(3).getSalary().getTo();

        System.out.println(salaryTo);
    }
}
