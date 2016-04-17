package avionics

import akka.actor.{ActorRef, Props, ActorLogging, Actor}
import avionics.EventSource.RegisterListener

/**
 * Created by mintik on 3/20/16.
 */
object Plane {
  case object GiveMeControl
  case class Controls(controls: ActorRef)
}

class Plane extends Actor with ActorLogging {
  import Altimeter._
  import Plane._
  import scala.collection.JavaConverters._

  val cfgstr = "akka.avionics.flightcrew"
  val altimeter: ActorRef = context.actorOf(Props(Altimeter()), "Altimeter")
  val controls: ActorRef = context.actorOf(Props(new ControlSurfaces(altimeter)), "ControlSurfaces")
  val config = context.system.settings.config
  val pilot = context.actorOf(Props[Pilot], config.getString(s"$cfgstr.pilotName"))
  val copilot = context.actorOf(Props[Copilot], config.getString(s"$cfgstr.copilotName"))
  val autoPilot = context.actorOf(Props[Autopilot], "Autopilot")
  val flightAttendant = context.actorOf(
    Props(LeadFlightAttendant(context.system.settings.config.getStringList("akka.avionics.flightcrew.attendantNames").asScala.toList)),
    config.getString(s"$cfgstr.leadAttendantName"))

  override def receive: Receive = {
    case GiveMeControl =>
      log info("Plane giving control.")
      sender ! Controls(controls)

    case AltitudeUpdate(altitude) =>
      log info(s"Altitude is now: $altitude")
  }

  override def preStart(): Unit = {
    altimeter ! RegisterListener(self)
    List(pilot, copilot) foreach { _ ! Pilots.ReadyToGo }
  }
}
