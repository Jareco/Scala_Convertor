
import akka.actor.{Actor, ActorLogging, ActorRef, Props}


object Printer {
  def props: Props = Props[Printer]

  final case class Print(message: String)

  case class PrintCurrency(currency: BigDecimal)

}


class Printer extends Actor with ActorLogging {

  import Printer._

  def receive = {
    case Print(message: String) => println(s" $message ")
    case PrintCurrency(currency: BigDecimal) => println(s" $currency ")
    case _ => println("TODO")

  }
}