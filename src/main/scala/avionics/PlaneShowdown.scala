package avionics

/**
 * Created by mintik on 3/22/16.
 */
object PlaneShowdown extends App {
  import akka.actor.{Actor, ActorSystem, Props}

  val system = ActorSystem("PlaneSimulation")

  val a = system.actorOf(Props(new Actor {
    def receive = Actor.emptyBehavior
  }))

  println(a.path)
  println(a.path.elements.mkString("/","/",""))
  println(a.path.name)

  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global
  system.scheduler.scheduleOnce(5.seconds) {
    system.shutdown
  }
}
