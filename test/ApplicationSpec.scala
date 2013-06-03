package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication {
      route(FakeRequest(GET, "/boum")) must beNone
    }

    "render the index page" in new WithApplication {
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain ("kitty cat")
    }
    
    "update the data" in new WithApplication {
    	val res = route(FakeRequest(POST, "/update?name=test1&color=test12")).get
    	status(res) must equalTo(OK)
    	println(contentType(res))
    	contentType(res) must beSome.which(_ == "application/json")    	
    	contentAsString(res) must equalTo ("\"1\"")
    }
  }
}
