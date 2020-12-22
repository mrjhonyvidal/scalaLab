package lectures.designpatterns.prototype

import scala.collection.mutable.ArrayBuffer

class AlertConstuctorClonablePrototype extends Cloneable {

  var subElementId: Int = 0
  var metricMetadataId: Int = 0
  var metricMetadataHourlyId: Int = 0
  var metricValue: Int = 0;
  var elementsEffected: ArrayBuffer[Int] = ArrayBuffer[Int]();

  def this(subElementId: Int, metricMetadataId: Int, metricMetadataHourlyId: Int,
           metricValue: Int, elementsEffected: ArrayBuffer[Int]) {
    this;
    this.subElementId = subElementId;
    this.metricMetadataId = metricMetadataId;
    this.metricMetadataHourlyId = metricMetadataHourlyId;
    this.metricValue = metricValue;
    this.elementsEffected = elementsEffected;

  }

  //Copy Constructor Approach
  def this(copyAlert: AlertConstuctorClonablePrototype) {
    this(copyAlert.subElementId, copyAlert.metricMetadataId, copyAlert.metricMetadataHourlyId, copyAlert.metricValue, copyAlert.elementsEffected.clone());
  }

  //cloneable approach
  def myclone: AlertConstuctorClonablePrototype = {
    println("clone called");
    var newAlert: AlertConstuctorClonablePrototype = this.clone.asInstanceOf[AlertConstuctorClonablePrototype]
    newAlert.elementsEffected = this.elementsEffected.clone();
    newAlert;
  }
}