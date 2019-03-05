import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder

class PostRequestTest extends Simulation {

  private val url = "https://postman-echo.com/get"
  private val feeder = csv("random_strigs.csv")

  private def httpConfBuilder(envUrl: String): HttpProtocolBuilder = {
    http
      .baseUrl(envUrl)
      .acceptHeader("application/json")
  }

  private val sendPostRequest = scenario("Send POST request to endpoint")
    .exec(http("POST method").post(url))


}
