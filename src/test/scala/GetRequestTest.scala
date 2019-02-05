import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class GetRequestTest extends Simulation {

  private val url = "https://postman-echo.com/get"
  private val queryParams = Map("foo1" -> "bar1", "foo2" -> "bar2")

  private def httpConfBuilder(envUrl: String) = {
    http
      .baseUrl(envUrl)
      .acceptHeader("application/json")
  }

  private val getDateFromEndpoint = scenario("Get date from endpoint")
    .exec(http("GET method")
      .get("/")
      .queryParamMap(queryParams)
      .check(status is 200, responseTimeInMillis lte 500))

  setUp(getDateFromEndpoint.inject(
    rampUsers(200) during(2 minutes)
  ))

}
