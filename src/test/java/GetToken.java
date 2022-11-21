import io.restassured.RestAssured;

public class GetToken {

    protected String getToken(String username, String password){

        //УРЛ авторизации
        String authUrl = "https://authorization-server.com";
        int authPort = 8080;

        String client_id = "rrandomId"; //Идентификатор пользователя для Auth2.0
        String client_secret = "randomSecret"; //секретка получения токена в сервисе авторизации
        return RestAssured.given()
                .baseUri(authUrl)
                .port(authPort)
                .formParam("grant_type", "password")
                .formParam("client_id", client_id)
                .formParam("client_secret", client_secret)
                .formParam("username", username)
                .formParam("password", password)
                .post("/token")
                .thenReturn()
                .body().jsonPath().getString("access_token"); //Вынимает String из Json ответа на запрос
    }

}
