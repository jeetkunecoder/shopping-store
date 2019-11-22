package services

import database.ShoppingCartRepository
import javax.inject.Inject
import models.ShoppingCart

import scala.concurrent.{ExecutionContext, Future}

class ShoppingCartService @Inject()(shoppingCartRepository: ShoppingCartRepository)(implicit ex: ExecutionContext)  {

  def read(id: String): Future[Seq[ShoppingCart]] = {
    shoppingCartRepository.read(id)
  }

  def checkout(user: String): Future[Seq[ShoppingCart]] = {
    shoppingCartRepository.checkout(user)
  }

  def readAll(): Future[Seq[ShoppingCart]] = {
    shoppingCartRepository.readAll()
  }

  def insert(shoppingCart: ShoppingCart) = {
    shoppingCartRepository.insert(shoppingCart)
  }

  def update(code: String, shoppingCart: ShoppingCart) = {
    shoppingCartRepository.update(code, shoppingCart)
  }

  def delete(code: String) = {
    shoppingCartRepository.delete(code)
  }

  def deleteAll() = {
    shoppingCartRepository.deleteAll()
  }
}
