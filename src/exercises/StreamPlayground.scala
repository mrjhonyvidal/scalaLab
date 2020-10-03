package exercises

/**
 * Playground to experiment best practices and patterns with Stream
 * Exercise: implement a lazily evaluated, single linked STREAM of elements
 *
 * Functionality:
 * naturals = MyStream.from(1)(x => x + 1) = stream of natural numbers (potentially infinite!)
 * naturals.take(100).foreach(println) // lazilly evaluated stream of the first 100 naturals (finite stream)
 * naturals.foreach(println) // will crash - infinite !
 * naturals.map(_ * 2) // stream of all even numbers (potentially infinite)
 */

abstract class MyStream[+A] {
  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] //prepend operator
  def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] // concatenate two streams

  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]
  def take(n: Int): MyStream[A] // takes the first n elements out of this stream
  def takeAsList(n: Int): List[A]
}

object EmptyStream extends MyStream[Nothing] {
  override def isEmpty: Boolean = ???

  override def head: Nothing = ???

  override def tail: MyStream[Nothing] = ???

  override def #::[B >: Nothing](element: B): MyStream[B] = ???

  override def ++[B >: Nothing](anotherStream: MyStream[B]): MyStream[B] = ???

  override def foreach(f: Nothing => Unit): Unit = ???

  override def map[B](f: Nothing => B): MyStream[B] = ???

  override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = ???

  override def filter(predicate: Nothing => Boolean): MyStream[Nothing] = ???

  override def take(n: Int): MyStream[Nothing] = ???

  override def takeAsList(n: Int): List[Nothing] = ???
}

// companion object
object MyStream {
  // from Factory Method
  def from[A](start: A)(generator: A => A): MyStream[A] = ???
}

object StreamPlayground extends App {
  // Work in progress
}
