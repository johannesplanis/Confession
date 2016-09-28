package co.planis.confession.model

/**
 * Created by janpluta on 28.09.16.
 */
data class CommentModel(val id : Int, val text : String, val commenter : UserModel, val likes : Int, val postedDate : String) {
}