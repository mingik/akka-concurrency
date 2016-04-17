package investigation

import akka.actor.Actor
import akka.actor.Actor.Receive
import investigation.MyActor.{Pong, Ping}

/**
 * Created by mintik on 3/22/16.
 */
class MyActor extends Actor {
  override def receive: Receive = {
    case "Hello" => println("Hi")
    case 42 => println("I don't know the question." + "Go ask the Earch Mark II.")
    case s: String => println(s"You sent me a string: $s")
    case Ping => sender ! Pong
    case Pong => sender ! Ping
  }
}

object MyActor {
  case object Ping
  case object Pong
}
