package controllers

import javax.inject.Inject
import play.api.mvc.{MessagesRequest, _}
import models.{Comment, Post, PostData, User,Preferences}
import models.Utils.DateUtil._
import play.api.data.Form

class PostsController @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  def feed(_orderBy:Int) = Action { implicit request =>
    Preferences.ORDER = _orderBy
    val posts = Post.findAll(Preferences.ORDER)
    implicit var user: Option[User] = None
    Ok(views.html.posts.postsfeed(posts))
  }

  private  var post: Post = _
  def show(id: Int) = Action { implicit request: MessagesRequest[AnyContent] =>
    Post.findById(id).map { _post =>
      post = _post
      Ok(views.html.posts.postContent(post,Comment.form))
    }.getOrElse(NotFound)
  }
  def create = Action{ implicit request: MessagesRequest[AnyContent] =>
    val date = getDateAsString(getDate)
    Ok(views.html.posts.createPost(PostData.form,date))
  }

  def savePost = Action { implicit request: MessagesRequest[AnyContent] =>

    val errorFunction = { formWithErrors: Form[PostData] =>
      val date = getDateAsString(getDate)
      print(formWithErrors)
      BadRequest(views.html.posts.createPost(formWithErrors,date))
    }
    val successFunction = { postData: PostData =>
      Post.add(Post(id = Post.genId,title =postData.title,content = postData.content,date = getDate))
      Redirect(routes.PostsController.feed(Preferences.ORDER))
    }
    val formValidationResult = PostData.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

  def createComment = Action { implicit request: MessagesRequest[AnyContent] =>

    val errorFunction = { formWithErrors: Form[Comment] =>
      BadRequest(views.html.posts.postContent(post,formWithErrors))
    }

    val successFunction = { comment: Comment =>
      Post.addComment(post.id,comment.response)
      Redirect(routes.PostsController.show(post.id))
    }

    val formValidationResult = Comment.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }
}
