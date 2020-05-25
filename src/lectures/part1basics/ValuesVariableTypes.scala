package lectures.part1basics

object ValuesVariableTypes extends App {

  val x: Int = 42
  println(x)

  // x = 3 val can not be reasigned - VALS ARE IMMUTABLE

  // COMPILER can infer types

  val aString: String = "hello"
  val aBoolean: Boolean = true
  val aChar: Char = 'a'
  val aInt: Int = x
  val aShort: Short = 4613 // Represents in 2 Bytes instead of 4
  val aLong: Long = 414256778L // 8 Bytes
  val aFloat: Float = 2.0f // f indicates compiler that is a Float instead of Double
  val aDouble: Double= 3.14 // We love PI

  // variables
  var aVariable: Int = 4
  // Can be assigned
  aVariable = 5 // Side Effects
}
