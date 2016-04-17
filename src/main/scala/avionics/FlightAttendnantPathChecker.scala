package avionics

import akka.actor.{Props, ActorSystem}

/**
 * Created by mintik on 3/23/16.
 */
object FlightAttendnantPathChecker extends App {
  import scala.collection.JavaConverters._
  val system = ActorSystem("PlaneSimulation")
  val lead = system.actorOf(
    Props(new LeadFlightAttendant(system.settings.config.getStringList("akka.avionics.flightcrew.attendantNames").asScala.toList) with AttendantCreationPolicy),
    system.settings.config.getString("akka.avionics.flightcrew.leadAttendantName"))
  Thread.sleep(2000)
  system.shutdown()
}
