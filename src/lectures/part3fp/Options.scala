package lectures.part3fp

import scala.util.Random

object Options extends App {

  /**
   * NOTES
   *
   * Use Options to avoid endless amount of null-related assertions
   * avoid runtime crashes due to NullPointerExceptions(NPEs)/Boogeyman
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

  // for-comprehension
  /**
   * Exercise
   */
  val config: Map[String, String] = Map(
    // fetched from elsewhere
    "host" -> "176.45.36.1",
    "port" -> "90"
  )

  class Connection {
    def connect = "Connected" // connect to some server
  }
  object Connection {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  // try to establish a connection, if so - print the connect method
  val host = config.get("host")
  val port = config.get("port")

  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h, p))) /// ---> returns a Option[Connection]
  /**
   * Imperative way
   * Logic here is that the val connection = x.flatmap... replace the below logic
   * if (h ! = null
   *  if (p != null)
   *    return Connection.apply(h,p)
   *  return null
   */

  val connectionStatus = connection.map(c => c.connect)
  /**
   * Imperative way
   * if (c != null)
   *  return c.connect
   * return null
    */

  println(connectionStatus)
  /// if (connectionStatus == null) println(None) else print (Some(connectionStatus.get))

  connectionStatus.foreach(println)
  /**
   * Imperative way
   * if (status != null )
   *  println(status)
   */

  /// Another solution is the chained calls solution
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
    .foreach(println)

  // for-comprehension
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  forConnectionStatus.foreach(println)
}
