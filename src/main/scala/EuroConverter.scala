import akka.actor.{Actor, ActorLogging, ActorRef, Props}

import scala.concurrent.Future


object EuroConverter {
  def props(printerActor: ActorRef): Props = Props(new EuroConverter(printerActor))

  case class euro2bitcoin(euro: BigDecimal)

  case class euro2dollar(euro: BigDecimal)

  case class euro2bitcoin2(euro:BigDecimal)

}


class EuroConverter(printerActor: ActorRef) extends Actor with ActorLogging {
  import context.dispatcher


  import EuroConverter._
  import Printer._

  def receive: Receive= {
    case euro2bitcoin(euro: BigDecimal) =>
      var euroBitcoin = euro / 5551
      printerActor ! PrintCurrency(euroBitcoin)

    case euro2dollar(euro: BigDecimal) =>
      var euroDollar = euro * 1.18
      printerActor ! PrintCurrency(euroDollar)

    case euro2bitcoin2(euro:BigDecimal)=>
      var euroBitcoin2=euro/5551
      sender()!euroBitcoin2

  }
}
