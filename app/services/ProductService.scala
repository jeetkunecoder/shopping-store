package services

import database.ProductRepository
import javax.inject.Inject
import models.Product

import scala.concurrent.{ExecutionContext, Future}

class ProductService @Inject()(productRepository: ProductRepository)(implicit ex: ExecutionContext) {

  def read(id: String): Future[Seq[Product]] = {
    productRepository.read(id)
  }

  def readAll(): Future[Seq[Product]] = {
    productRepository.readAll()
  }

  def insert(product: Product) = {
    productRepository.insert(product)
  }

  def update(code: String, product: Product) = {
    productRepository.update(code, product)
  }

  def delete(code: String) = {
    productRepository.delete(code)
  }

  def deleteAll() = {
    productRepository.deleteAll()
  }
}
