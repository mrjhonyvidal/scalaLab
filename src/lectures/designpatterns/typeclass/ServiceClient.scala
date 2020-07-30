package lectures.designpatterns.typeclass

import java.util.UUID

/**
 * Typeclass design pattern
 * Polymorphism
 * Interface are the same by definition might change based on type
 * ServiceClient[User]
 * ServiceClient[Conference]
 */
trait ServiceClient[T]{
  def create(e: T)
  def read(id: UUID)
  def update(e: T, id: UUID)
  def delete(id: UUID)
}

sealed class User {
 // Just for example purpose
}

class UserServiceClient extends ServiceClient[User]{

  /**
   * For example only
   * TODO add real project scenario using Akka and sbt
   */
  override def create(e: User): Unit = ???

  override def read(id: UUID): Unit = ???

  override def update(e: User, id: UUID): Unit = ???

  override def delete(id: UUID): Unit = ???
}

