package models

import play.api.libs.json.{Json, Writes}

case class Product(name: String, code: String, description: String, price: Double)

object Product {
  implicit val writes: Writes[Product] = Json.writes[Product]
  def tupled: ((String, String, String, Double)) => Product = (Product.apply _).tupled
}