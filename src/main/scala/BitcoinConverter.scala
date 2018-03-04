import akka.actor.{Actor, ActorLogging, ActorRef, Props}


object BitcoinConverter {
  def props(printerActor: ActorRef): Props = Props(new BitcoinConverter(printerActor))

  case class bitcoin2euro(bitcoin: BigDecimal)

  case class bitcoin2dollar(bitcoin: BigDecimal)

}


class BitcoinConverter(printerActor: ActorRef) extends Actor with ActorLogging {

  import BitcoinConverter._
  import Printer._

  def receive: Receive = {
    case bitcoin2euro(bitcoin: BigDecimal) =>
      var bitcoinEuro = bitcoin * 5551
      printerActor ! PrintCurrency(bitcoinEuro)

    case bitcoin2dollar(bitcoin: BigDecimal) =>
      var bicoinDollar = bitcoin * 6500
      printerActor ! PrintCurrency(bicoinDollar)


  }
}
