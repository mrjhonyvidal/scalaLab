package lectures.designpatterns.prototype

import scala.collection.mutable.ArrayBuffer

object AlertTestUsingConstructorPrototpye extends App{

  var list:ArrayBuffer[Int] = ArrayBuffer[Int](100,200,300,400)
  var alert3:AlertConstuctorClonablePrototype = new AlertConstuctorClonablePrototype(1111,2222,3333,4444,list);
  var alert4:AlertConstuctorClonablePrototype = new AlertConstuctorClonablePrototype(alert3);

  alert4.elementsEffected +=666;
  alert3.elementsEffected.foreach(print(_))

  println();
  alert4.elementsEffected.foreach(print(_))

  var alert5:AlertConstuctorClonablePrototype = new AlertConstuctorClonablePrototype(1111,2222,3333,4444,list);
  var alert6:AlertConstuctorClonablePrototype = alert5.myclone;

  alert6.elementsEffected +=666;
  alert5.elementsEffected.foreach(print(_))

  println();
  alert6.elementsEffected.foreach(print(_))
}
