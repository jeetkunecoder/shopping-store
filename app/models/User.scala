package models

import play.api.libs.json.{Json, Reads, Writes}

case class User(id: String, name: String, bankAccount: String, email: String)

object User {
  implicit val writes: Writes[User] = Json.writes[User]
  implicit val reads: Reads[User] = Json.reads[User]
  def tupled: ((String, String, String, String)) => User = (User.apply _).tupled
}
