package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents,Request,AnyContent}
import models.{Post, Comment}


class PostsController @Inject() (cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport{

  def feed(orderBy:Int) = Action { implicit request =>
    val posts = Post.findAll(orderBy)

    Ok(views.html.posts.postsfeed(posts))
  }

  def show(id: Long) = Action { implicit request =>

    Post.findById(id).map { post =>
      Ok(views.html.posts.postContent(post))
    }.getOrElse(NotFound)
  }
  def create = Action{ implicit request =>
    Ok(views.html.posts.createPost(Post.form))
  }



}
