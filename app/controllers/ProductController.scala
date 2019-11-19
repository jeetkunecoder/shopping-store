package controllers

import javax.inject.Inject
import models.Product
import org.slf4j.LoggerFactory
import play.api.libs.json.{JsError, JsPath, JsResult, JsSuccess, JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Result}
import services.ProductService

import scala.concurrent.{ExecutionContext, Future}

class ProductController @Inject()(cc: ControllerComponents, productService: ProductService)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  val logger = LoggerFactory.getLogger(classOf[UserController])

  def read(id: String): Action[AnyContent] = Action.async { implicit request =>
    productService.read(id).map {
      user => Ok(Json.toJson  (user))
    }.recover(recoverError)
  }

  def all(): Action[AnyContent] = Action.async { implicit request =>
    productService.readAll().map {
      user => Ok(Json.toJson(user))
    }.recover(recoverError)
  }

  def insert(): Action[AnyContent] = Action.async { implicit request =>
    val jsonString: JsValue = request.body.asJson.getOrElse(throw new IllegalArgumentException)
    val productFromJson: JsResult[Product] = Json.fromJson[Product](jsonString)

    productFromJson match {
      case JsSuccess(product: Product, path: JsPath) =>
        logger.info("Inserting product " + product.name)
        val futureInsert = productService.insert(product)
        futureInsert.map(_ => Ok).recover(recoverError)
      case e @ JsError(_) =>
        logger.error("Error while decoding an user: " + JsError.toJson(e).toString())
        Future.successful(BadRequest)
    }
  }

  def update(id: String): Action[AnyContent] = ???
  //    Action.async { implicit request =>
  //    propertyService.update(id).map {
  //      result => Ok(Json.toJson(result))
  //    }.recover(recoverError)
  //  }

  def delete(id: String): Action[AnyContent] = Action.async { implicit request =>
    productService.delete(id).map {
      result => Ok(Json.toJson(result))
    }.recover(recoverError)
  }

  def deleteAll(): Action[AnyContent] = Action.async { implicit request =>
    productService.deleteAll().map {
      result => Ok(Json.toJson(result))
    }.recover(recoverError)
  }

  val recoverError: PartialFunction[Throwable, Result] = {
    case e: Exception => InternalServerError("Error while performing a request to the server: " + e)
  }
}
