package database

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application

class ShoppingCartRepositorySpec extends PlaySpec with ScalaFutures with GuiceOneAppPerSuite {

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(20, Seconds), interval = Span(100, Millis))

  "The ShoppingCart Repository" should {
    val cache = Application.instanceCache[ShoppingCartRepository]

    "Insert a product in the DB" in {

    }

  }
}