package avionics

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
 * Created by mintik on 3/23/16.
 */
trait AttendantResponsiveness {
  import scala.concurrent.duration._
  val maxResponseTimeMS: Int
  def responseDuration = scala.util.Random.nextInt(maxResponseTimeMS).millis
}

object FlightAttendant {
  case class GetDrink(drinkname: String)
  case class Drink(drinkname: String)
  def apply() = new FlightAttendant with AttendantResponsiveness {
    val maxResponseTimeMS = 300000
  }
}

class FlightAttendant extends Actor {
  this: AttendantResponsiveness =>
  import FlightAttendant._
  implicit val ec = context.dispatcher
  override def receive: Receive = {
    case GetDrink(drinkname) => context.system.scheduler.scheduleOnce(responseDuration, sender, Drink(drinkname))
  }
}


