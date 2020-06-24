package lectures.part3fp

object Options extends App {

  /**
   * NOTES
   *
   *  An Option is a wrapper for a value that might be present or not
   *  sealed abstract class Option[+A]
   *  case class Some[+A](x: A) extends Option [A]
   *  case object None extends Option[Nothing] ---> None is a singleton for absent values
   *
   *  map uses options on its basic get operations, prefer it over apply.
   *  val map = Map("key" -> "value)
   *  map.get("key") // Some(value)
   *  map.get("other") // None
   *  val numbers = List(1,2,3)
   *  list.headOption // Some (1)
   *  list.find(_ % 2 == 0) // Some(2)
   */
  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None
  println(myFirstOption)

  // unsafe APIs
  def unsafeMethod(): String = null
  val result = Some(null) // WRONG

  val resultWithOption = Option(unsafeMethod()) // Some or None
  println(resultWithOption)

  // Chained methods
  def backupMethod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  // DESIGN unsafe APIs --> Option is used on the methods definitions
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")
  val betterChainResult = betterUnsafeMethod() orElse betterBackupMethod()

  // Functions on Options
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) // UNSAFE - DO NOT USE THIS

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(x => x > 10))
  println(myFirstOption.flatMap(x => Option(x * 10)))
}
