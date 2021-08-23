package co.idearun.common.exception


sealed class Failure(msg: String?) {

    val msgRes=msg

    object NetworkConnection : Failure("")
    object ServerError : Failure("")
    object Bad_Request_Error : Failure("")
    object UNAUTHORIZED_Error  : Failure("")
    object Forbiden : Failure("")
    object Exception : Failure("")

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(msg:String?): Failure(msg)
}