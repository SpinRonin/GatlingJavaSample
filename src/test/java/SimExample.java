import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SimExample extends Simulation {

    /**
     * Класс с симуляцией нагрузки
     */

    //Users
    private final String usernameAdmin = "calm-manx@example.com";
    private final String password = "Helpless-Walrus-2";

    //Получение токена
//    GetToken token = new GetToken();
//    String tokenAdmin = token.getToken(usernameAdmin, password);


    //Блок с методами и объектами для нагрузки
    {
        //Здесь храним базовый УРЛ
        HttpProtocolBuilder httpProtocol = http
                .baseUrl("https://pokeapi.co/api/v2")
                .inferHtmlResources();

        //Сценарий нагрузочного тестирования
        ScenarioBuilder scn = scenario("Scenario")
                //Добавление фидера CSV для вариации данных
//            .feed(csv("src/test/resources/feedData.csv").circular())
                .exec(http("GetAbility")
                        .get("/pokemon/pikachu")
                        //Добавление токена в запрос
//                        .header("Authorization", "Bearer " + tokenCik)
                        .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                        //Добавление тела запроса
//                        .body(StringBody(
//                                "Some Json example"
//                        )).asJson()
                        .check(status().is(200))
                        .check(jsonPath("$.abilities[1].ability").saveAs("ability")))
                .exec(session -> {
                    System.out.println((String) session.get("ability"));
                    return session;
                });


        //Профиль нагрузки
        setUp(scn.injectOpen(stressPeakUsers(1).during(1))).protocols(httpProtocol);
    }
}
