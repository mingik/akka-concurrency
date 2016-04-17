package avionics

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef}

/**
 * Created by mintik on 3/20/16.
 */
object ControlSurfaces {
  case class StickBack(amount: Float)
  case class StickForward(amount: Float)
}

class ControlSurfaces(altimeter: ActorRef) extends Actor {
  import ControlSurfaces._
  import Altimeter._

  override def receive: Receive = {
    case StickBack(amount) => altimeter ! RateChange(amount)
    case StickForward(amount) => altimeter ! RateChange(-1 * amount)
  }
}
