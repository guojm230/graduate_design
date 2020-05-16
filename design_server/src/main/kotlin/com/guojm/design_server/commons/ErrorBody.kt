package com.guojm.design_server.commons

import com.fasterxml.jackson.annotation.JsonIgnore

class ErrorBody(@JsonIgnore val statusCode: StatusCode, val msg: String=statusCode.msg, val data:Any? = null) {
    val code: Int = statusCode.value
    constructor(cause: ApiException) : this(cause.code, cause.message,null)
}
