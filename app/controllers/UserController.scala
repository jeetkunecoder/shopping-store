package controllers

import javax.inject.Inject
import models.User
import org.slf4j.LoggerFactory
import play.api.libs.json.{JsError, JsPath, JsResult, JsSuccess, JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Result}
import services.UserService

import scala.concurrent.{ExecutionContext, Future}

class UserController @Inject()(cc: ControllerComponents, userService: UserService)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  val logger = LoggerFactory.getLogger(classOf[UserController])

  def read(id: String): Action[AnyContent] = Action.async { implicit request =>
    userService.read(id).map {
      user => Ok(Json.toJson(user))
    }.recover(recoverError)
  }

  def all(): Action[AnyContent] = Action.async { implicit request =>
    userService.readAll().map {
      user => Ok(Json.toJson(user))
    }.recover(recoverError)
  }

  def insert(): Action[AnyContent] = Action.async { implicit request =>
    val jsonString: JsValue = request.body.asJson.getOrElse(throw new IllegalArgumentException)
    val userFromJson: JsResult[User] = Json.fromJson[User](jsonString)

    userFromJson match {
      case JsSuccess(user: User, path: JsPath) =>
        logger.info("Inserting user " + user.id)
        val futureInsert = userService.insert(user)
        futureInsert.map(_ => Ok).recover(recoverError)
      case e @ JsError(_) =>
        logger.error("Error while decoding an user: " + JsError.toJson(e).toString())
        Future.successful(BadRequest)
    }
  }

  def update(id: String): Action[AnyContent] = ???

  def delete(id: String): Action[AnyContent] = Action.async { implicit request =>
    userService.delete(id).map {
      result => Ok(Json.toJson(result))
    }.recover(recoverError)
  }

  def deleteAll(): Action[AnyContent] = Action.async { implicit request =>
    userService.deleteAll().map {
      result => Ok(Json.toJson(result))
    }.recover(recoverError)
  }

  val recoverError: PartialFunction[Throwable, Result] = {
    case e: Exception => InternalServerError("Error while performing a request to the server: " + e)
  }
}
