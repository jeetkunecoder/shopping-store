package models

import play.api.libs.json.{Json, Writes}

case class ShoppingCart(user: String, productCode: String, quantity: Int) extends CartKey

abstract class CartKey {
  def user: String
  def productCode: String
}

case class ProductInCart(user: String, productCode: String) extends CartKey

object ShoppingCart {
  implicit val writes: Writes[ShoppingCart] = Json.writes[ShoppingCart]
  def tupled: ((String, String, Int)) => ShoppingCart = (ShoppingCart.apply _).tupled
}