package database

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatest.Matchers._
import play.api.Application
import models.ShoppingCart

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ShoppingCartRepositorySpec extends PlaySpec with ScalaFutures with GuiceOneAppPerSuite {

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(20, Seconds), interval = Span(100, Millis))

  "The ShoppingCart Repository" should {
    val cache = Application.instanceCache[ShoppingCartRepository]
    val repository = cache(app)

    "Ensure that the tested value doesn't exist on application start" in {
      val futureDelete = repository.delete("123")
      whenReady(Future(futureDelete)) { _ =>
        repository.read("123").futureValue shouldBe empty
      }
    }

    "Insert an item in the cart" in {
      val cart = new ShoppingCart("123", "AABB11", 3)
      val expected = Seq(cart)
      val futureInsert = repository.insert(cart)

      whenReady(Future(futureInsert)) { _ =>
        repository.read("AABB11").futureValue shouldBe expected
      }
    }

    "Perform a checkout" in {
      val cart = new ShoppingCart("123", "AABB11", 3)
      val expected = Seq(cart)
      val futureInsert = repository.insert(cart)

      whenReady(Future(futureInsert)) { _ =>
        repository.checkout("123").futureValue shouldBe expected
      }
    }
  }
}