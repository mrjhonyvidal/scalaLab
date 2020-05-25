package lectures.part1basics

object Expressions extends App {

  // EXPRESSIONS versus INSTRUCTIONS
  // Everything in Scala is an expression
  // Instructions are executed (think java) - Imperative way - "do something"
  // Expressions are evaluated (Scala) - "Give me the value of something" (Functional way of think)

  val x = 1 + 2
  println(x)

  println(2 + 3 * 4)
  // + - * / & | ^ << >> >>> (right shift with zero extension)

  val aCondition = true
  val aConditionedValue = 5
  println(if(aCondition) 5 else 3) // IF Expression

  // =============================================
  var i = 0
  while(i < 10) {
    println(i)
    i += 1
  }
  // NEVER WRITE THIS AGAIN. NO IMPERATIVE ANY MORE
  // =============================================

  var aVariable = 1
  val aWeirdValue: Unit = (aVariable = 3) // Unit == void
  println(aWeirdValue) // RETURNS () not 3 as aWeirdValue is a Unit

  // SIDE EFFECT EXPRESSIONS: println(), whiles, reassigning

  // Code blocks
  val aCodeBlock = {
    // Code block is an expression. The value of the block is the value of its last expression
    val y = 2
    val z = y + 1

    if ( z > 2) "hello" else "goodbye"
  }

  // =================================
  // QUESTIONS
  // 1. What is the difference between "hello world" vs println("hello word")?
  // First is a STRING, and second is an EXPRESSION (with side effects and also returning a UNIT)

  // What will return the following block?
  val someValue = {
    2 < 3
  }
  println(someValue) // true

  // What will return the following block?
  val someOtherValue = {
    if(someValue) 239 else 296
    42
  }
  println(someOtherValue) // 42
}
