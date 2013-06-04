package controllers

import models._
import play.api._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

//import spray.json._

//import DefaultJsonProtocol._ // !!! IMPORTANT, else `convertTo` and `toJson` won't work
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._

import org.json4s.native.Serialization.{read, write => swrite}

object Application extends Controller {

  import play.api.Play.current
  implicit val formats = native.Serialization.formats(NoTypeHints)
  def index = Action {
    DB.withSession{ implicit session =>
      Ok(views.html.index(Query(Cats).list))
    }
  }

  def get(name:String) = Action {   
    val cat = DB.withSession{ implicit session =>
      Query(Cats).filter(_.name === name).firstOption
    }
    if(cat != None) Ok(swrite(cat)).as("application/json") 
    else NotFound("Cat nane: " + name)
  }
  def list = Action {
    val cats: List[Cat] =    
		DB.withSession{ implicit session =>
	    	Query(Cats).list
	    }    
    Ok(swrite(cats))    
  }
  
  def update = Action { implicit request =>
    val cat = catForm.bindFromRequest.get
    val u = DB.withSession{ implicit session =>
      Query(Cats).filter(_.name === cat.name).update(cat)
    }
    if(u == 1) Ok(swrite(u.toString)).as("application/json") 
    else InternalServerError("Update failed: " + cat)
  }
  val catForm = Form(
    mapping(
      "name" -> text(),
      "color" -> text()
    )(Cat.apply)(Cat.unapply)
  )

  def insert = Action { implicit request =>
    val cat = catForm.bindFromRequest.get
    DB.withSession{ implicit session =>
      Cats.insert(cat)
    }

    Redirect(routes.Application.index)
  }
  
}