package database

import javax.inject.{Inject, Singleton}
import slick.jdbc.PostgresProfile.api._
import models.User

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(implicit executionContext: ExecutionContext) {

  class UserTable(tag: Tag) extends Table[User](tag, "user") {
    def id = column[String]("id")
    def name = column[String]("name")
    def bankAccount = column[String]("bank_account")
    def email = column[String]("email")
    override def * = (
      id,
      name,
      bankAccount,
      email
    ) <> (User.tupled, User.unapply)

  }

  val users = TableQuery[UserTable]

  val db = Database.forConfig("postgres")

  def readAll(): Future[Seq[User]] = db.run(users.result)

  def insert(user: User): Future[Int] = db.run(users += user)

  def read(id: String): Future[Seq[User]] = db.run(users.filter(_.id === id).result)

  def update(id: String, user: User) = ???

  private def matchKey(u: UserTable, user: User): Rep[Boolean] = {
    u.id === user.id
  }

  def delete(id: String) = db.run(users.filter(_.id === id).delete)

  def deleteAll(): Future[Int] = db.run(users.delete)
}

