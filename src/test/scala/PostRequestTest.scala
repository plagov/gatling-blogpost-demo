import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef.{http, _}
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

class PostRequestTest extends Simulation {

  private val url = "https://postman-echo.com/post"
  private val feeder = csv("random_strigs.csv")

  private def httpConfBuilder(envUrl: String): HttpProtocolBuilder = {
    http
      .baseUrl(envUrl)
      .acceptHeader("application/json")
  }

  private val sendPostRequest = scenario("Send POST request to endpoint")
    .feed(feeder)
    .exec(
      http("POST method")
        .post(url)
        .queryParamMap(Map("id" -> "${id}"))
        .check(status is 200, responseTimeInMillis lte 800))

  setUp(sendPostRequest.inject(
    rampUsers(200) during (2 minutes),
    rampUsers(300) during (2 minutes),
    rampUsers(200) during (1 minutes)
  )).protocols(httpConfBuilder(url))
}
