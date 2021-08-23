package co.idearun.common.exception


class ViewFailure {
    class responseError(msg: String?) : Failure.FeatureFailure(msg)
}
