package crash

import akka.actor.{Props, ActorSystem}

/**
 * Created by mintik on 4/16/16.
 */
object CrashingApp extends App {
  val system = ActorSystem()
  val supervisorActor = system.actorOf(SupervisorActor.props)
  supervisorActor ! SupervisorActor.Init
  system.awaitTermination()
}
