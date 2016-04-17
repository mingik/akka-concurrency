package avionics

/**
 * Created by mintik on 3/23/16.
 */

import akka.actor.{Actor, ActorRef, Props}

trait AttendantCreationPolicy {
  val numberOfAttendants: Int = 8
  def createAttendant: Actor = FlightAttendant()
}

//trait LeadFlightAttendantProvider {
//  def newLeadFlightAttendant: Actor = LeadFlightAttendant()
//}

object LeadFlightAttendant {
  case object GetFlightAttendant
  case class Attendant(a: ActorRef)
  def apply(attendantNames: List[String]) = new LeadFlightAttendant(attendantNames) with AttendantCreationPolicy
}

class LeadFlightAttendant(val attendantNames: List[String]) extends Actor {
  this: AttendantCreationPolicy =>
  import LeadFlightAttendant._
  override def preStart(): Unit = {
    // create children actors based off configuration
    attendantNames take numberOfAttendants foreach { name => context.actorOf(Props(createAttendant), name) }
  }
  def randomAttendant(): ActorRef = { context.children.take(scala.util.Random.nextInt(numberOfAttendants) + 1).last }
  override def receive: Receive = {
    case GetFlightAttendant => sender ! Attendant(randomAttendant())
    case m => randomAttendant() forward m
  }
}

