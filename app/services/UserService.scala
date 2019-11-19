package services

import javax.inject.Inject
import models.User
import database.UserRepository

import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(userRepository: UserRepository)(implicit ex: ExecutionContext) {

  def read(id: String): Future[Seq[User]] = {
    userRepository.read(id)
  }

  def readAll(): Future[Seq[User]] = {
    userRepository.readAll()
  }

  def insert(user: User) = {
    userRepository.insert(user)
  }

  def update(id: String, user: User) = {
    userRepository.update(id, user)
  }

  def delete(id: String) = {
    userRepository.delete(id)
  }

  def deleteAll() = {
    userRepository.deleteAll()
  }
}

