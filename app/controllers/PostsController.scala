package controllers

import javax.inject.Inject
import play.api.mvc.{MessagesRequest, _}
import models.{Comment, Post, PostData, User,Preferences,UserSession}
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
      BadRequest(views.html.posts.createPost(formWithErrors,date)).
      flashing(("error" -> "Please correct the errors in the form."))
    }
    val successFunction = { postData: PostData =>
      Post.add(Post(id = Post.genId,author = UserSession.SESSION_USERNAME_KEY,title =postData.title,content = postData.content,date = getDate))
      val message = "Successfully added post " + postData.title
      Redirect(routes.PostsController.feed(Preferences.ORDER)).
        flashing("success" -> message)
    }
    val formValidationResult = PostData.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

  def createComment = Action { implicit request: MessagesRequest[AnyContent] =>

    val errorFunction = { formWithErrors: Form[Comment] =>
      BadRequest(views.html.posts.postContent(post,formWithErrors))
      .flashing("error" -> "There was a problem posting the comment")
    }

    val successFunction = { comment: Comment =>
      Post.addComment(post.id,comment.response)
      val message = "Successfully added comment"
      Redirect(routes.PostsController.show(post.id))
      .flashing("success" -> message)
    }

    val formValidationResult = Comment.form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

  def likePost(id: Int) =Action { implicit request: MessagesRequest[AnyContent] =>
    if(UserSession.liked.contains(id)){
      Redirect(routes.PostsController.show(id)).
        flashing("error" -> "You have already voted")

    }else{
      post.likes +=1
      UserSession.liked += id
      Redirect(routes.PostsController.show(id)).
        flashing("success" -> "Liked")
    }

  }
  def dislikePost(id: Int) =Action { implicit request: MessagesRequest[AnyContent] =>
    if(UserSession.liked.contains(id)){
      Redirect(routes.PostsController.show(id)).
        flashing("error" -> "You have already voted")
    }else{
      post.dislikes +=1
      UserSession.liked += id
      Redirect(routes.PostsController.show(id)).
        flashing("success" -> "Disliked")
    }
  }

}
