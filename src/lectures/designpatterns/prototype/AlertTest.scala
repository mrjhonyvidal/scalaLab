package lectures.designpatterns.prototype

import scala.collection.mutable.ArrayBuffer

object AlertTest extends App{
  var list:ArrayBuffer[Int] = ArrayBuffer[Int](100,200,300,400)

  var alert1:AlertCaseClassPrototype = AlertCaseClassPrototype(1111,2222,3333,4444,list);

  var alert2:AlertCaseClassPrototype =alert1.copy(elementsEffected=alert1.elementsEffected.clone());

  alert2.elementsEffected +=800;

  println(alert1)
  println(alert2)
}
