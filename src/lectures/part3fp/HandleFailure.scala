package lectures.part3fp

import scala.util.{Failure, Random, Success, Try}

object HandleFailure extends App{

  /**
   * Exceptions are handled inside try-catch blocks
   * try {
   * }catch{
   *  case: _: IOException => handle IOException
   *  case: _: Exception => // handle other Exception
   * }
   *
   * Problem: multiple, nested try's make the code hard to follow
   * We can't chain multiple operations prone to failure
   *
   * Scala Try is a wrapper for a computation that might fail or not
   * sealed abstract class Try[+T]
   * case class Failure[+T](t: Throwable) extends Try[T]
   * case class Success[+T](value: T) extends Try[T]
   *
   * - Avoid runtime crashes due to uncaught exceptions
   * - Avoid an endless amout of try-catches
   *
   * Functional Way of dealing with failure
   *  map, flatMap, filter
   *  orElse
   *  others: fold, collect, toList, conversion to Options
   *
   *  If you design a method to return a (some type) but may throw an exception
   *  return a Try[that type] instead
   */
    val aSuccess = Success(3)
    val aFailure = Failure(new RuntimeException("SUPER FAILURE"))

    println(aSuccess)
    println(aFailure)

    def unsafeMethod(): String = throw new RuntimeException("NO STRING FOR YOU BUSTER")

    // Try objects via the apply method
    // Avoid crashing the system
    val potentialFailure = Try(unsafeMethod())
    println(potentialFailure)

    // syntax sugar
    val anotherPotentialFailure = Try {
      // code that might throw
    }

    // utilities that give us Try, isSucess the execution of the function inside Try()
    println(potentialFailure.isSuccess)

    // orElse
    // This is how you use unsafe APIs
    def backupMethod(): String = "A valid result"
    val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))
    println(fallbackTry)

    // IF you design the API
    def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
    def betterBackupMethod(): Try[String] = Success("A valid result")
    val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()

    // map, filter, flatMap
    println(aSuccess.map(_ * 2))
    println(aSuccess.flatMap(x => Success(x*10)))
    println(aSuccess.filter(_ > 10)) // tu rns Success into a Failure

  /**
   * EXERCISE
   * TODO: as all exercises, take them to Exercises folder, with references to this lecture with folders: exercise and solution
   * GIVEN THE FOLLOWING API with Connection and HttpService which may throw an exception
   * Get the html page from the connection, print it to the console, call renderHtml
   * Handle these operations safely
   */
  val host = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random =  new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...BINGO...</html>"
      else throw new RuntimeException("Connection interrupted")
    }

    // Solution - safe API
    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())
    def getConnection(host: String, port: String): Connection = {
      if(random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")
    }
    // solution - safe API
    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  //Get the html page from the connection, print it to the console, call renderHtml
  val possibleConnection = HttpService.getSafeConnection(host, port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("/home"))
  possibleHTML.foreach(renderHTML)

  // EDI-Parser :-) We want to know everything
  // What if we need to handle or throw the exception content to the end user / system to take actions over it
  // use match on function defined as Try[T]
  possibleHTML match {
    case Success(i) => possibleHTML.foreach(renderHTML)
    case Failure(s) => println(s"Failed reason: $s")
  }

  // Bellow code using CASE PATTERN MATCH
  HttpService.getSafeConnection(host, port)
    .flatMap(connection => connection.getSafe("/home")) match {
    case Success(i) => possibleHTML.foreach(renderHTML)
    case Failure(s) => println(s"Failed reason: $s")
  }

  // shorthand version
  HttpService.getSafeConnection(host, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  // for-comprehension version
  for {
    connection <- HttpService.getSafeConnection(host, port)
    html <- connection.getSafe("/home")
  } renderHTML(html)

  // The code above replaces the following code
  // IMPERATIVE WAY - THE ONE WE'RE AVOIDING USING FUNCTIONAL PROGRAMMING
  try {
    val connection = HttpService.getConnection(host, port)
    try {
      val page = connection.get("/home")
      renderHTML(page)
    } catch {
      case _: Exception => println("Got a RuntimeException on Connection Exception")
    }
  } catch {
    case _: Throwable => println("Got some ackward exception")
  }
}
