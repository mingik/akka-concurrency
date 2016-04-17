package crash

import akka.actor.SupervisorStrategy.{Escalate, Restart, Stop}
import akka.actor._
import akka.actor.Actor.Receive
import crash.SupervisorActor.Init

/**
 * Created by mintik on 4/16/16.
 */
class SupervisorActor extends Actor with ActorLogging {
  override final val supervisorStrategy = OneForOneStrategy() {
    case _: ActorInitializationException => Stop
    case _: ActorKilledException => Stop
    case _: Exception => Restart
    case _ => Escalate
  }
  override def receive: Receive = {
    case Init => {
      log.info(s"Creating child actor")
      val child: ActorRef = context.actorOf(MyActor.props)
    }
  }
}

object SupervisorActor {
  case object Init
  val props = Props[SupervisorActor]
}