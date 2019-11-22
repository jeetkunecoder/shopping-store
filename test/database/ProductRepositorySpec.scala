package database

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatest.Matchers._
import play.api.Application
import models.Product

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ProductRepositorySpec extends PlaySpec with ScalaFutures with GuiceOneAppPerSuite {

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(20, Seconds), interval = Span(100, Millis))

  "The Product Repository" should {
    val cache = Application.instanceCache[ProductRepository]
    val repository = cache(app)

    "Ensure that the tested value doesn't exist on application start" in {
      val futureDelete = repository.delete("AABB11")
      whenReady(Future(futureDelete)) { _ =>
        repository.read("AABB11").futureValue shouldBe empty
      }
    }

    "insert product in DB" in {
      val product = new Product("Guitar", "AABB11", "Epiphone Harmony Korine Guitar", 1500)
      val expected = Seq(product)
      val futureInsert = repository.insert(product)
      whenReady(Future(futureInsert)) { _ =>
        repository.read("AABB11").futureValue shouldBe expected
      }
    }

    "clean-up inserted row on exit" in {
      val futureDelete = repository.delete("AABB11")
      whenReady(Future(futureDelete)) { _ =>
        repository.read("AABB11").futureValue shouldBe empty
      }
    }
  }
}