package test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._
import org.json4s.native.Serialization.{read, write => swrite}
import models._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {

  implicit val formats = native.Serialization.formats(NoTypeHints)
  
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
    	//println(contentType(res))
    	contentType(res) must beSome.which(_ == "application/json")    	
    	contentAsString(res) must equalTo ("\"1\"")
    }
    
    "update data failed" in new WithApplication {
    	val res = route(FakeRequest(POST, "/update?name=test1123&color=test12")).get
    	status(res) must equalTo(INTERNAL_SERVER_ERROR)
    	//println(contentType(res))
    	//contentType(res) must beSome.which(_ == "application/json")    	
    }
    
    "get valid data by name" in new WithApplication {
      val res = route(FakeRequest(GET, "/get?name=test1")).get
      
      status(res) must equalTo(OK)      
      contentType(res) must beSome.which(_ == "application/json")
      
      val s = contentAsString(res)
      val cat =  read[Cat](s)
      cat.name must equalTo("test1")
      cat.color must equalTo("test12")
    }
    
    "get invalid data by name" in new WithApplication {
      val res = route(FakeRequest(GET, "/get?name=test1134232323")).get 
      status(res) must equalTo(NOT_FOUND)
    }
  }
}
