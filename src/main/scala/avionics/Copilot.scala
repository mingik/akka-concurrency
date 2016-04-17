package avionics

import akka.actor.{Props, ActorRef, Actor}
import akka.actor.Actor.Receive
import akka.util.Timeout

import scala.concurrent.Future

/**
 * Created by mintik on 3/23/16.
 */
class Copilot extends Actor {
  import Pilots._
  import scala.concurrent.duration._
  var controls: ActorRef = context.system.deadLetters
  var pilot: AnyRef = context.system.deadLetters
  var autopilot: AnyRef = context.system.deadLetters
  val pilotName = context.system.settings.config.getString("akka.avionics.flightcrew.pilotName")
  implicit val timeout: Timeout = Timeout.durationToTimeout(5 seconds)
  override def receive: Receive = {
    case ReadyToGo =>
      pilot = context.system.actorSelection("../" + pilotName)
      autopilot = context.actorSelection("../Autopilot")
  }
}
