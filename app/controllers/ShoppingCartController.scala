package controllers

import javax.inject.Inject
import models.ShoppingCart
import org.slf4j.LoggerFactory
import play.api.libs.json.{JsError, JsPath, JsResult, JsSuccess, JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Result}
import services.ShoppingCartService

import scala.concurrent.{ExecutionContext, Future}

class ShoppingCartController @Inject()(cc: ControllerComponents, shoppingCartService: ShoppingCartService)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  val logger = LoggerFactory.getLogger(classOf[UserController])

  def read(productCode: String): Action[AnyContent] = Action.async { implicit request =>
    shoppingCartService.read(productCode).map {
      cart => Ok(Json.toJson(cart))
    }.recover(recoverError)
  }

  def checkout(user: String): Action[AnyContent] = Action.async { implicit request =>
    shoppingCartService.checkout(user).map {
      cart => Ok(Json.toJson(cart))
    }.recover(recoverError)
  }

  def all(): Action[AnyContent] = Action.async { implicit request =>
    shoppingCartService.readAll().map {
      cart => Ok(Json.toJson(cart))
    }.recover(recoverError)
  }

  def insert(): Action[AnyContent] = Action.async { implicit request =>
    val jsonString: JsValue = request.body.asJson.getOrElse(throw new IllegalArgumentException)
    val cartFromJson: JsResult[ShoppingCart] = Json.fromJson[ShoppingCart](jsonString)

    cartFromJson match {
      case JsSuccess(shoppingCart: ShoppingCart, path: JsPath) =>
        logger.info("Inserting product " + shoppingCart.productCode)
        val futureInsert = shoppingCartService.insert(shoppingCart)
        futureInsert.map(_ => Ok).recover(recoverError)
      case e @ JsError(_) =>
        logger.error("Error while decoding a shopping cart entry: " + JsError.toJson(e).toString())
        Future.successful(BadRequest)
    }
  }

  def update(id: String): Action[AnyContent] = ???

  def delete(id: String): Action[AnyContent] = Action.async { implicit request =>
    shoppingCartService.delete(id).map {
      result => Ok(Json.toJson(result))
    }.recover(recoverError)
  }

  def deleteAll(): Action[AnyContent] = Action.async { implicit request =>
    shoppingCartService.deleteAll().map {
      result => Ok(Json.toJson(result))
    }.recover(recoverError)
  }

  val recoverError: PartialFunction[Throwable, Result] = {
    case e: Exception => InternalServerError("Error while performing a request to the server: " + e)
  }
}
