import akka.actor.{Actor, ActorLogging, ActorRef, Props}


object DollarConverter {
  def props(printerActor: ActorRef): Props = Props(new DollarConverter(printerActor))

  case class dollar2bitcoin(dollar: BigDecimal)

  case class dollar2euro(dollar: BigDecimal)

}


class DollarConverter(printerActor: ActorRef) extends Actor with ActorLogging {

  import DollarConverter._
  import Printer._

  def receive: Receive = {
    case dollar2bitcoin(dollar: BigDecimal) =>
      var dollarBitcoin = dollar / 6500
      printerActor ! PrintCurrency(dollarBitcoin)

    case dollar2euro(dollar: BigDecimal) =>
      var dollarEuro = dollar * 0.85
      printerActor ! PrintCurrency(dollarEuro)


  }
}
