package lectures.designpatterns.prototype

import scala.collection.mutable.ArrayBuffer

case class AlertCaseClassPrototype(subElementId: Int, metricMetadataId: Int, metricMetadataHourlyId: Int,
                                   metricValue: Int, elementsEffected: ArrayBuffer[Int]){

}
