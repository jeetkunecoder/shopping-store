package database

import javax.inject.{Inject, Singleton}
import slick.jdbc.PostgresProfile.api._
import models.Product

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductRepository @Inject()(implicit executionContext: ExecutionContext) {

  class ProductTable(tag: Tag) extends Table[Product](tag, "product") {
    def name = column[String]("name")
    def code = column[String]("code")
    def description = column[String]("description")
    def price = column[Double]("price")
    override def * = (
      name,
      code,
      description,
      price
    ) <> (Product.tupled, Product.unapply)
  }

  val products = TableQuery[ProductTable]

  val db = Database.forConfig("postgres")

  def readAll(): Future[Seq[Product]] = db.run(products.result)

  def insert(product: Product): Future[Int] = db.run(products += product)

  def read(code: String): Future[Seq[Product]] = db.run(products.filter(_.code === code).result)

  def update(id: String, product: Product) = ??? // db.run(properties.update().result)

  private def matchKey(p: ProductTable, product: Product): Rep[Boolean] = {
    p.code === product.code
  }

  def delete(code: String) = db.run(products.filter(_. code === code).delete)

  def deleteAll(): Future[Int] = db.run(products.delete)
}
