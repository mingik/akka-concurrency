package investigation

import akka.actor.{Props, ActorSystem, Actor}

/**
 * Created by mintik on 3/20/16.
 */
class BadShakespeareanActor extends Actor {
  def receive = {
    case "Good Morning" => println("Him: Forsooth 'tis the 'morn, but mouneth for thou doest I do!")
    case "You're terrible!" => println("Him: Yup")
  }
}

object BadShakespeareanApp extends App {
  val system = ActorSystem("theater")
  val actor = system.actorOf(Props[BadShakespeareanActor])

  def send(msg: String): Unit = {
    println(s"Me: $msg")
    actor ! msg
    Thread.sleep(100)
  }

  send("Good Morning")
  send("You're terrible!")

  system.shutdown()
}
