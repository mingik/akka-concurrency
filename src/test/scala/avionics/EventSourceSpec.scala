package avionics

import akka.actor.{ActorRef, ActorSystem, Actor}
import akka.actor.Actor.Receive
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.{Matchers, BeforeAndAfterAll, WordSpecLike}

/**
 * Created by mintik on 3/21/16.
 */
class TestEventSource
  extends Actor
  with ProductionEventSource {
  override def receive: Receive = eventSourceReceive
}

class EventSourceSpec
  extends TestKit(ActorSystem("EventSourceSpec"))
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {
  import EventSource._
  override def afterAll() { system.shutdown() }
  "EventSource" should {
    "allow us to register a listener" in {
      val real = TestActorRef[TestEventSource].underlyingActor
      real.receive(RegisterListener(testActor))
      real.listeners should contain (testActor)
    }
    "allow us to unregister a listener" in {
      val real = TestActorRef[TestEventSource].underlyingActor
      real.receive(RegisterListener(testActor))
      real.receive(UnregisterListener(testActor))
      real.listeners.size should be (0)
    }
    "send the event to our test actor" in {
      val testA = TestActorRef[TestEventSource]
      testA ! RegisterListener(testActor)
      testA.underlyingActor.sendEvent("Fibonacci")
      expectMsg("Fibonacci")
    }
  }
}
