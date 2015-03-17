import thrift.RandomNumberService
import com.twitter.finagle.ThriftMux
import com.twitter.util.{Await, Future}

object ThriftServer {
  def main(args: Array[String]) {

    val randomNumberService = new RandomNumberService[Future] {
      def getRandomInt(): Future[Int] = Future.value(scala.util.Random.nextInt)
    }

    val server = ThriftMux.serveIface("localhost:8080", randomNumberService)
    Await.ready(server)
  }
}


