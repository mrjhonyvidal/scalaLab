package exercises

/**
 * Convariant Generic List
 * @tparam A
 */
abstract class MyList[+A] {

  /**
   * head = first element of the list
   * tail = remainder of the list
   * isEmpty = is this list empty
   * add(int) => new list with this element added
   * toString => a string representation of the list
   */
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B] // We return a new list when we want to modify our list
  def printElements: String

  // polymorphic call
  // toString, equals, hash code are methods that are present in Any Ref class
  override def toString: String = "[" + printElements + "]"

  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]
  def filter(predicate: MyPredicate[A]): MyList[A]

  // This is our concatenate function
  def ++[B >: A](list: MyList[B]): MyList[B]
}

/**
 * We'are going to create an empty list and a non empty list
 */

object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  def printElements: String = ""

  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty // For the FlatMap we need the Concatenate Function
  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  //def head: Int = ??? // ??? if we call the method it will throw a NotImplemented Exception
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons(element, this) // We return this object as tail
  def printElements: String =
    if(t.isEmpty) "" + h
    else h + " " + t.printElements // Recursively print the elements calling on the tail

  /*
   [1,2,3].filter(n % 2 == 0) =
    [2,3].filter(n % 2 == 0) =
      = new Const(2, [3].filter(n % 2 == 0)
      = new Cons(2, Empty.filter( n % 2 == 0)
      = new Const(2, Empty) Our final result
   */
  def filter(predicate: MyPredicate[A]): MyList[A] =
    if (predicate.test(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)

  /*
   [1,2,3].map(n*2)
    = new Cons(head --> 2 => 1*2 , tail --> [2, 3].map(n*2)
    = new Cons(2, new Cons(4, [3].map(n*2))
    = new Cons(2, new Cons(4, new Cons(6, Empty.map(n*2))
    = new Cons(2, new Cons(4, new Cons(6, Empty))) Our final result
   */
  def map[B](transformer: MyTransformer[A, B]): MyList[B] =
    new Cons(transformer.transform(h), t.map(transformer))

  /*
   if I concatenate [1,2] ++ [3,4,5]
    = new Cons(1, [2] ++ [2,3,5]
    = new Cons(1, new Cons(2, Empty ++ [3,4,5)))
    = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
   */
  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)

  /*
  [1, 2].flatMap(n => [n, n+1])
    = [1, 2] ++ [2].flatMap(n => [n, n+1])
    = [1, 2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ Empty
    = [1,2,2,3]
   */
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] =
    transformer.transform(h) ++ t.flatMap(transformer)
}

trait MyPredicate[-T] {
  def test(elem: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(elem: A): B
}

object ListTest extends App {

  /**
   * val list = new Cons(1, Empty)
   * Example of LinkedList
   * val linkedList = new Cons(1, new Cons(2, new Cons(3, Empty))) // This is Linked List
   *
   * println(list.head) // 1
   * println(list.add(4).head)
   * println(list.isEmpty)
   * println(linkedList.tail.head) // 2
   *
   * println(list.toString)
   * println(linkedList.toString)
   */
    val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
    val anotherListOfIntegers: MyList[Int] = new Cons(4, new Cons(5, Empty))
    val listOfStrings: MyList[String] = new Cons("Hello", new Cons("Scala", Empty))

    println(listOfIntegers.toString)
    println(listOfStrings.toString)

    // Our own .map() implementation using Anonymous Class and Generics
    println(listOfIntegers.map(new MyTransformer[Int, Int] {
      // Anonymous Class
      override def transform(elem: Int): Int = elem * 2
    }).toString)

    // Our own .filter() implementation
    println(listOfIntegers.filter(new MyPredicate[Int]{
      override def test(elem: Int): Boolean = elem % 2 == 0
    }).toString)

    println((listOfIntegers ++ anotherListOfIntegers).toString)

    // Our own .flatMap() implementation
    println(listOfIntegers.flatMap(new MyTransformer[Int, MyList[Int]] {
      override def transform(elem: Int): MyList[Int] = new Cons(elem, new Cons(elem + 1, Empty))
    }))
}