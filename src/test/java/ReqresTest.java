import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

public class ReqresTest {

    @Test
    @DisplayName("Test case, when single resource is found")
    public void singleResourceTest() {
        given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2))
                .body("support.url", is("https://reqres.in/#support-heading"));
    }

    @Test
    @DisplayName("Test case, when single resource is not found")
    public void singleResourceNotFoundTest() {
        String response = given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404)
                .extract().response().asString();

        assertThat(response).isEqualTo("{}");
    }

    @Test
    @DisplayName("Test successful user registration")
    public void registerSuccessfulTest() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"," +
                        "\"password\": \"pistol\"}")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Test deletion of user")
    public void deleteUserTest() {
        String response = given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
                .extract().response().asString();

        assertThat(response).isEqualTo("");
    }

    @Test
    @DisplayName("Test updating of user's data")
    public void putUserTest() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"morpheus\"," +
                        "\"job\": \"zion resident\"}")
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));

    }

    @Test
    @DisplayName("Test patching of user's data")
    public void patchUserTest() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"morpheus\"," +
                        "\"job\": \"zion resident\"}")
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }
}
