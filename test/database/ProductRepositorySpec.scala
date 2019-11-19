package database

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application

class ProductRepositorySpec extends PlaySpec with ScalaFutures with GuiceOneAppPerSuite {

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(20, Seconds), interval = Span(100, Millis))

  "The Product Repository" should {
    val cache = Application.instanceCache[ProductRepository]

    "Insert a product in the DB" in {

    }

    "Update a product in the DB" in {

    }

    "Get a product from the DB" in {

    }

    "Delete a product from the DB" in {

    }
  }
}