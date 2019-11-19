package database

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatest.Matchers._
import play.api.Application
import models.User

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserRepositorySpec extends PlaySpec with ScalaFutures with GuiceOneAppPerSuite {

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(20, Seconds), interval = Span(100, Millis))

  "The User Repository" should {
    val cache = Application.instanceCache[UserRepository]
    val repository = cache(app)

    "Ensure that the tested value doesn't exist on application start" in {
      val futureDelete = repository.delete("userId")
      whenReady(Future(futureDelete)) { _ =>
        repository.read("userId").futureValue shouldBe empty
      }
    }

    "Insert a user in the DB" in {
      val user = new User("123", "Joe Garcia", "123456789", "jgarcia@gmail.com")
      val expected = Seq(user)
      val futureInsert = repository.insert(user)

      whenReady(Future(futureInsert)) { _ =>
        repository.read("userId").futureValue shouldBe expected
      }
    }

    "Update an user in the DB" in {

    }

    "Get a user from the DB" in {

    }

    "Delete an user from the DB" in {

    }
  }
}
