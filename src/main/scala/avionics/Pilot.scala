package avionics

import akka.actor.{ActorSelection, ActorRef, Actor}
import akka.actor.Actor.Receive
import akka.util.Timeout

import scala.concurrent.Future
import scala.util.Failure

/**
 * Created by mintik on 3/23/16.
 */
object Pilots {
  case object ReadyToGo
  case object RelinquishControl
}

class Pilot extends Actor {
  import Pilots._
  import Plane._
  import scala.concurrent.duration._
  var controls: AnyRef = context.system.deadLetters
  var copilot: AnyRef = context.system.deadLetters
  //var autopilot: AnyRef = context.system.deadLetters
  val copilotName = context.system.settings.config.getString("akka.avionics.flightcrew.copilotName")
  implicit val timeout: Timeout = Timeout.durationToTimeout(5 seconds)
  override def receive: Receive = {
    case ReadyToGo =>
      context.parent ! GiveMeControl
      copilot = context.actorSelection("../"+copilotName)
      //autopilot = context.actorSelection("../Autopilot")
    case Controls(controlSurfaces) => controls = controlSurfaces
  }
}
