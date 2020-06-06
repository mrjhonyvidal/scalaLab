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

  // Refactored to be a more FP using Scala Function Types
  // This functions in particular are called Higher-order functions
  // Higher-order functions either receive functions as parameters or return other functions as result
  def map[B](transformer: A => B): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]

  // This is our concatenate function
  def ++[B >: A](list: MyList[B]): MyList[B]

  // High Order Functions (HOFS)
  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList[A]
  def zipWith[B, C](list: MyList[B], zip:(A,B) => C): MyList[C]
  def fold[B](start: B)(operator: (B, A) => B): B
}

/**
 * We'are going to create an empty list and a non empty list
 */

case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyList[B] = Empty
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty // For the FlatMap we need the Concatenate Function
  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  // HOFS
  def foreach(f: Nothing => Unit): Unit = ()
  def sort(compare: (Nothing, Nothing) => Int) = Empty
  def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
    if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Empty
  def fold[B](start: B)(operator: (B, Nothing) => B): B = start

}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
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
  def filter(predicate: A => Boolean): MyList[A] =
    if (predicate.apply(h)) new Cons(h, t.filter(predicate)) // We use predicate.apply just to highlight it, but could be written predicate(h) only
    else t.filter(predicate)

  /*
   [1,2,3].map(n*2)
    = new Cons(head --> 2 => 1*2 , tail --> [2, 3].map(n*2)
    = new Cons(2, new Cons(4, [3].map(n*2))
    = new Cons(2, new Cons(4, new Cons(6, Empty.map(n*2))
    = new Cons(2, new Cons(4, new Cons(6, Empty))) Our final result
   */
  def map[B](transformer: A  => B): MyList[B] =
    new Cons(transformer.apply(h), t.map(transformer)) // We use predicate.apply just to highlight it, but could be written predicate(h) only

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
  def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer(h) ++ t.flatMap(transformer)

  // HOFS
  def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  def sort(compare: (A, A) => Int): MyList[A] = {
    // TODO refactor insert function to use @tailrec
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if (sortedList.isEmpty) new Cons(x, Empty)
      else if (compare(x, sortedList.head) <= 0) new Cons(x, sortedList)
      else new Cons(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] =
    if (list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else new Cons(zip(h, list.head), t.zipWith(list.tail, zip))

  /**
   *  [1, 2, 3].fold(0)(+) =
   *   [2, 3].fold(1) (+) =
   *   [3].fold(3)(+) =
   *   [].fold(6)
   *   6
  */
  def fold[B](start: B)(operator: (B, A) => B): B = {
    t.fold(operator(start, h))(operator)
  }
}

/**
This is no longer necessary
MyPredicate and MyTransformer was replaced by Scala Function Types
trait MyPredicate[-T] { //MyPredicate is a Function type of T => Boolean - what generic -T and not T?
  def test(elem: T): Boolean
}

trait MyTransformer[-A, B] { // A => B - why Convaliant -A had to be defined as -A not A? review again more in deep
  def transform(elem: A): B
}**/

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
    val cloneListOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
    val anotherListOfIntegers: MyList[Int] = new Cons(4, new Cons(5, Empty))
    val listOfStrings: MyList[String] = new Cons("Hello", new Cons("Scala", Empty))

    println(listOfIntegers.toString)
    println(listOfStrings.toString)

    // Our own .map() implementation using Anonymous Class and Generics
    /**println(listOfIntegers.map(new Function1[Int, Int] {
      // Anonymous Class
      override def apply(elem: Int): Int = elem * 2
    }).toString)**/

    //println(listOfIntegers.map(elem => elem * 2).toString)
    println(listOfIntegers.map( _ * 2 ).toString)

    // Our own .filter() implementation
    /**println(listOfIntegers.filter(new Function1[Int, Boolean]{
      override def apply(elem: Int): Boolean = elem % 2 == 0
    }).toString)**/
    //println(listOfIntegers.filter(elem => elem % 2 ==0).toString)
    println(listOfIntegers.filter( _ % 2 ==0 ).toString)

    println((listOfIntegers ++ anotherListOfIntegers).toString)

    // Our own .flatMap() implementation
    /**println(listOfIntegers.flatMap(new Function[Int, MyList[Int]] {
      override def apply(elem: Int): MyList[Int] = new Cons(elem, new Cons(elem + 1, Empty))
    }))**/

    // _ will not work for this lambda because we use the element two times in the implementation
    println(listOfIntegers.flatMap(elem => new Cons(elem, new Cons(elem + 1, Empty))).toString)

    println(cloneListOfIntegers == listOfIntegers) // Out of box it was implemented equals method applied to the list

    listOfIntegers.foreach(println)
    println(listOfIntegers.sort((x, y) => y - x))

    println(anotherListOfIntegers.zipWith[String, String](listOfStrings, _ + "-" + _))

    println(listOfIntegers.fold(0)(_ + _))
}