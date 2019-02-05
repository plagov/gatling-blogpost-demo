import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class GetDateInfoFromEndpointTest extends Simulation {

  private final val url = "http://date.jsontest.com/"

  private def httpConfBuilder(envUrl: String) = {
    http
      .baseUrl(envUrl)
      .acceptHeader("application/json")
  }

  private val getDateFromEndpoint = scenario("Get date from endpoint")
    .exec(http("GET method")
      .get("/")
      .check(status is 200, responseTimeInMillis lte 500))

  setUp(getDateFromEndpoint.inject(
    rampUsers(200) during(2 minutes)
  ))

}
