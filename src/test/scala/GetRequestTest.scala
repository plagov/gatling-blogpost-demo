import io.gatling.core.Predef._
import io.gatling.http.Predef.{http, _}
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

class GetRequestTest extends Simulation {

  private val url = "https://postman-echo.com/get"
  private val queryParams = Map("foo1" -> "bar1", "foo2" -> "bar2")

  private def httpConfBuilder(envUrl: String): HttpProtocolBuilder = {
    http
      .baseUrl(envUrl)
      .acceptHeader("application/json")
  }

  private val sendGetRequest = scenario("Send GET request to endpoint")
    .exec(http("GET method")
      .get("/")
      .queryParamMap(queryParams)
      .check(status is 200, responseTimeInMillis lte 500))

  setUp(sendGetRequest.inject(
    rampUsers(200) during(2 minutes),
    rampUsers(300) during (2 minutes),
    rampUsers(200) during(1 minutes)
  )).protocols(httpConfBuilder(url))

}
