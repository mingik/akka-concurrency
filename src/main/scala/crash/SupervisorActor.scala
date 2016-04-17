package crash

import akka.actor.SupervisorStrategy.{Escalate, Restart, Stop}
import akka.actor._
import akka.actor.Actor.Receive
import crash.SupervisorActor.{Init, Status}

/**
 * Created by mintik on 4/16/16.
 */
class SupervisorActor extends Actor with ActorLogging {
  var childrenMap: Map[ActorPath, ActorRef] = Map()

  override final val supervisorStrategy = OneForOneStrategy() {
    case _: ActorInitializationException => Stop
    case _: ActorKilledException => Stop
    case _: Exception => {
      log.info("Received Exception from child, restarting it...")
      Restart
    }
    case _ => Escalate
  }
  override def receive: Receive = {
    case Init => {
      log.info(s"Creating child actor.")

      /**
       * Creates MyActor actor as a child.
       * If it throws an exception,
       */
      val child = context.actorOf(MyActor.props)
      childrenMap = childrenMap + (child.path -> child)
    }

    case Status => {
      log.info(s"Current children map: ${childrenMap}")
    }
  }
}

object SupervisorActor {
  case object Init
  case object Status
  val props = Props[SupervisorActor]
}