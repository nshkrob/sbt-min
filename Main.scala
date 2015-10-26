package niks.code

import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.http._
import com.twitter.finagle.http.service._
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future
import java.net.InetSocketAddress

/**
 * This example demonstrates a sophisticated HTTP server that handles exceptions
 * and performs authorization via a shared secret. The exception handling and
 * authorization code are written as Filters, thus isolating these aspects from
 * the main service (here called "Respond") for better code organization.
 */
object HttpServer {
  /**
   * A simple Filter that catches exceptions and converts them to appropriate
   * HTTP responses.
   */
  class HandleExceptions extends SimpleFilter[Request, Response] {
    def apply(request: Request, service: Service[Request, Response]) = {

      // `handle` asynchronously handles exceptions.
      service(request) handle { case error =>
        val statusCode = error match {
          case _: IllegalArgumentException =>
            Status.Forbidden
          case _ =>
            Status.InternalServerError
        }
        val errorResponse = Response(Version.Http11, statusCode)
        errorResponse.contentString = error.getStackTraceString

        errorResponse
      }
    }
  }

  /**
   * A simple Filter that checks that the request is valid by inspecting the
   * "Authorization" header.
   */
  class Authorize extends SimpleFilter[Request, Response] {
    def apply(request: Request, continue: Service[Request, Response]) = {
      if (Some("open sesame") == request.headerMap.get(Fields.Authorization)) {
        continue(request)
      } else {
        Future.exception(new IllegalArgumentException("You don't know the secret"))
      }
    }
  }

  /**
   * The service itself. Simply echos back "hello world"
   */
  class Hello extends Service[Request, Response] {
    def apply(request: Request) = {
      val response = Response(Version.Http11, Status.Ok)
      response.contentString = "hello world"
      Future.value(response)
    }
  }

  class CloseService extends Service[Request, Response] {
    private[this] var closeFn: Option[() => Future[Unit]] = None
    def setClose(fn: () => Future[Unit]) : Unit = synchronized {
      closeFn = Some(fn)
    }
    def apply(request: Request): Future[Response] = {
      closeFn match {
        case None =>
          val response = Response(Status.PreconditionFailed)
          response.contentString = "service not set. Call setService first."
          Future.value(response)

        case Some(fn) =>
          fn().map { _ =>
            val response = new Response.Ok
            response.contentString = "bye"
            response
          }
      }
    }
  }

  def main(args: Array[String]) {
    val handleExceptions = new HandleExceptions
    val authorize = new Authorize
    val respond = new Hello

    // compose the Filters and Service together:
    val myService: Service[Request, Response] =
      handleExceptions andThen
        //authorize andThen
        respond

    myService.close()

    val closer = new CloseService()

    val svc = RoutingService.byPath {
      case "/search.json" => new Hello()
      case "/hello" => new Hello()
      case "/close" => closer
    }

    val server: Server = ServerBuilder()
      .codec(Http())
      .bindTo(new InetSocketAddress(8080))
      .name("httpserver")
      .build(svc)

    println("HERE")
    closer.setClose(server.close)
  }
}

