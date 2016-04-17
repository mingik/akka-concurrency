package avionics

import akka.actor.{ActorRef, Props, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import avionics.Plane.Controls
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by mintik on 3/20/16.
 */
object Avionics extends App {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val timeout = Timeout(5.seconds)
  val system = ActorSystem("PlanceSimulation")
  val plane = system.actorOf(Props[Plane], "Plane")

  val controls: Controls = Await.result((plane ? Plane.GiveMeControl).mapTo[Controls], 5.seconds)

  system.scheduler.scheduleOnce(200.millis) {
    controls.controls ! ControlSurfaces.StickBack(1f)
  }

  system.scheduler.scheduleOnce(1.seconds) {
    controls.controls ! ControlSurfaces.StickBack(0f)
  }

  system.scheduler.scheduleOnce(3.seconds) {
    controls.controls ! ControlSurfaces.StickBack(0.5f)
  }

  system.scheduler.scheduleOnce(4.seconds) {
    controls.controls ! ControlSurfaces.StickBack(0f)
  }

  system.scheduler.scheduleOnce(5.seconds) {
    system.shutdown
  }
}
