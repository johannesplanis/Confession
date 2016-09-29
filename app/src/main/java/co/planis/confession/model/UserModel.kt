package co.planis.confession.model

/**
 * Created by janpluta on 28.09.16.
 */
data class UserModel(val userId : Int = 1, val name: String, val avatar : String = "") {
    constructor() : this(name = "")
}