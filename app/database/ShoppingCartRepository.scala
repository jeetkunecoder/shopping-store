package database

import javax.inject.{Inject, Singleton}
import slick.jdbc.PostgresProfile.api._
import models.ShoppingCart
import slick.lifted.Tag

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShoppingCartRepository @Inject()(implicit executionContext: ExecutionContext) {

  class ShoppingCartTable(tag: Tag) extends Table[ShoppingCart](tag, "shopping_cart") {
    def user = column[String]("user")
    def productCode = column[String]("product_code")
    def quantity = column[Int]("quantity")
    override def * = (
      user,
      productCode,
      quantity
    ) <> (ShoppingCart.tupled, ShoppingCart.unapply)
  }

  val carts = TableQuery[ShoppingCartTable]

  val db = Database.forConfig("postgres")

  def readAll(): Future[Seq[ShoppingCart]] = db.run(carts.result)

  def insert(shoppingCart: ShoppingCart): Future[Int] = db.run(carts += shoppingCart)

  def read(productCode: String): Future[Seq[ShoppingCart]] = db.run(carts.filter(_.productCode === productCode).result)

  def checkout(user: String): Future[Seq[ShoppingCart]] = db.run(carts.filter(_.user === user).result)

  def update(code: String, shoppingCart: ShoppingCart) = ??? // db.run(properties.update().result)

  private def matchKey(s: ShoppingCartTable, shoppingCart: ShoppingCart): Rep[Boolean] = {
    s.productCode === shoppingCart.productCode
  }

  def delete(productCode: String) = db.run(carts.filter(_.productCode === productCode).delete)

  def deleteAll(): Future[Int] = db.run(carts.delete)
}
