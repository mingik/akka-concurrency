package crash

import akka.actor.{Props, ActorLogging, Actor}
import akka.actor.Actor.Receive

import scala.util.Random

/**
 * Created by mintik on 4/16/16.
 */
class MyActor extends Actor with ActorLogging {
  import MyActor._
  val random: Random = new Random()
  override def preStart(): Unit = {
    log.info(s"preStart(): Sending Initialize to itself")
    self ! Initialize
  }

  override def postStop(): Unit = {
    log.info(s"postStop(): about to die due to Exception")
  }

  def postStartInitialization(): Unit = {
    /**
     * Throws RuntimeException at random.
     */
    if (random.nextBoolean()) {
      throw new RuntimeException("expected exception on postStartInitialization stage.")
    } else {
      log.info(s"Finished start init!")
    }
  }

  override def receive: Receive = {
    case Initialize =>
      postStartInitialization()
  }
}

object MyActor {
  case object Initialize
  val props = Props[MyActor]
}
