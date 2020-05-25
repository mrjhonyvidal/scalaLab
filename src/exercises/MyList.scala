package exercises

abstract class MyList {

  /**
   * head = first element of the list
   * tail = remainder of the list
   * isEmpty = is this list empty
   * add(int) => new list with this element added
   * toString => a string representation of the list
   */
  def head: Int
  def tail: MyList
  def isEmpty: Boolean
  def add(element: Int): MyList // We return a new list when we want to modify our list
  def printElements: String

  // polymorphic call
  // toString, equals, hash code are methods that are present in Any Ref class
  override def toString: String = "[" + printElements + "]"

}

/**
 * We'are going to create an empty list and a non empty list
 */

object Empty extends MyList {
  def head: Int = throw new NoSuchElementException
  def tail: MyList = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Int): MyList = new Cons(element, Empty)
  def printElements: String = ""
}

class Cons(h: Int, t: MyList) extends MyList {
  //def head: Int = ??? // ??? if we call the method it will throw a NotImplemented Exception
  def head: Int = h
  def tail: MyList = t
  def isEmpty: Boolean = false
  def add(element: Int): MyList = new Cons(element, this) // We return this object as tail
  def printElements: String =
    if(t.isEmpty) "" + h
    else h + " " + t.printElements // Recursively print the elements calling on the tail
}

object ListTest extends App {
  val list = new Cons(1, Empty)

  //Example of LinkedList
  val linkedList = new Cons(1, new Cons(2, new Cons(3, Empty))) // This is Linked List

  println(list.head) // 1
  println(list.add(4).head)
  println(list.isEmpty)
  println(linkedList.tail.head) // 2

  println(list.toString)
  println(linkedList.toString)
}