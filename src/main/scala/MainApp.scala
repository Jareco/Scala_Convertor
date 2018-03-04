
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.sun.corba.se.impl.orbutil.closure.Future

import scala.concurrent.Await
import scala.concurrent.duration._

object MainApp extends App {

  import EuroConverter._
  import BitcoinConverter._
  import DollarConverter._

  import com.sun.org.apache.xml.internal.serialize.Printer._


  // Create the 'helloAkka' actor system
  val system: ActorSystem = ActorSystem("helloAkka")


  try {
    // Create the printer actor
    val printer: ActorRef = system.actorOf(Printer.props, "printerActor")

    val bc: ActorRef = system.actorOf(BitcoinConverter.props(printer), "bitcoinConverter")
    val dc: ActorRef = system.actorOf(DollarConverter.props(printer), "dollarConverter")
    val ec: ActorRef = system.actorOf(EuroConverter.props(printer), "euroConverter")


    val bitcoin = BigDecimal("1")
    val dollar = BigDecimal("200")
    val euro = BigDecimal("300")

    bc ! bitcoin2dollar(bitcoin)
    bc ! bitcoin2euro(bitcoin)
    dc ! dollar2euro(dollar)
    dc ! dollar2bitcoin(dollar)
    ec ! euro2dollar(euro)
    ec ! euro2bitcoin(euro)



    implicit val timeout = Timeout(5 seconds)
    val future = ec ? euro2bitcoin2(euro)
    val result = Await.result(future, timeout.duration).asInstanceOf[BigDecimal]
    println("result "+result)

    val f1 = ask(ec, euro2bitcoin2(euro))
    val a = Await.result(f1, 5 seconds).asInstanceOf[BigDecimal]
    println("result2 "+a)


    val future1: concurrent.Future[BigDecimal] = ask(ec, euro2bitcoin2(euro)).mapTo[BigDecimal]
    println("Future1: " + future1.value.get.get)



  } finally {
    system.terminate()
  }


  /*
  sealed abstract class Person
  case class Arbeiter (age: Int, name: String) extends Person
  case class Student (age:Int, name: String, studienrichung:String) extends Person

def test (a:Person)=a match{
  case Arbeiter(age,name)=> age
  case Student (age, name,studienrichung) =>studienrichung
}

  val p1:Person = new Arbeiter(22, "Oleg")
  val p2:Person = new Student(18, "Vasya", "Informatik")

  var output1 = test(p2)
  println(output1)

  case class Auto (age: Int, preis:Double)
  val auto1= new Auto(12, 15000)
  val auto2=new Auto (3, 14000)
  val auto3= new Auto(44, 40000)


  val list = List (auto1,auto2,auto3)
  val testSortBy= list.sortBy(_.age)

  println(testSortBy)

  val testFilter=list.filter(x=>x.preis>14000)
  println (testFilter)
  */




}