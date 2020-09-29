package lectures.advanced

object AdvancedFunctionalProgramming extends App {

  val aFunction = (x: Int) => x + 1 // Function1[Int, Int] === Int => Int
  val aFussyFunction = (x: Int) =>
    if(x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } // Total Function

  // {1, 2, 5} => Int
  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } // partial function value (everything inside {}, including "{")

  println(aPartialFunction(2))
  //println(aPartialFunction(55423))

  // Partial Function Utilities
  println(aPartialFunction.isDefinedAt(67)) // returns false

  // lift
  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(2)) // Some(56)
  print(lifted(98)) // now returns None instead of break

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2))
  println(pfChain(45))

  // PF extend normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOFs accept partial function as well
  val aMappedList = List(1,2,3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  } // --> {} is partial function, map is a High Order Function

  println(aMappedList)

  /**
   * Note: PartialFunction can only have ONE parameter type
   *
   * Exercises
   *
   * 1- construct a PF instance yourself (anonymous class)
   * 2- dumb chatbot as a PF
   */

  // 1
  val aManualFussyFunction = new PartialFunction[Int, Int] {
    override def apply(x: Int): Int = x match {
      case 1 => 42
      case 2 => 65
      case 3 => 999
    }

    override def isDefinedAt(x: Int): Boolean =
      x == 1 || x == 2 || x == 5
  }

  // 2
  val chatbot: PartialFunction[String, String] = {
    case "hello" => "Hi, my name is HAL9000"
    case "goodbye" => "Once you start talking to me, there is no return, human!"
    case "call mom" => "Unable to find your phone without a credit card"
  }

  // Stream of lines written in the console: scala.io.Source.stdin.getLines()
  //scala.io.Source.stdin.getLines().foreach(line => println("chatbot says:" + chatbot(line))
  scala.io.Source.stdin.getLines().map(chatbot).foreach(println)
  // stream.HOF.foreach(applyTheValueOfCasePatternedMached)

}
