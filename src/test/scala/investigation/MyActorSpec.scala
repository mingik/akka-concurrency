import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{Props, ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import investigation.MyActor
import investigation.MyActor.{Pong, Ping}
import org.scalatest._

class EventSourceSpec extends TestKit(ActorSystem("EventSourceSpec"))

class TestKitSpec(actorSystem: ActorSystem)
  extends TestKit(actorSystem)
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll

class MyActorSpec
  extends TestKitSpec(ActorSystem("MyActorSpec"))
  with ParallelTestExecution {
  override def afterAll() { system.shutdown() }
  def makeActor(): ActorRef = system.actorOf(Props[MyActor], "MyActor")

  "My Actor" should {
//    "throw if constructed with the wrong name" in {
//      evaluating {
//        // use a generated name
//        val a = system.actorOf(Props[MyActor])
//      } should produce [Exception]
//    }
    "construct without exception" in {
      val a = makeActor()
      // The throw will cause the test to fail
    }
    "respond with a Pong to a Ping" in {
      val a = makeActor()
      //a.tell(Ping, testActor)
      a ! Ping
      expectMsg(Pong)
    }
  }
}
