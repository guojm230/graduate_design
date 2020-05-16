package com.guojm.design_server.commons

import com.google.protobuf.Api

open class NoFullTraceException(msg: String = "",cause: Throwable? = null)
    : RuntimeException(msg,cause,false,false){
    constructor(cause: Throwable): this(cause.message?:"",cause)
}

/**
 * 表示API错误的异常，必须指名错误原因
 */
open class ApiException(val code: StatusCode,override val message: String = code.msg): NoFullTraceException(message)

object AuthenticationException: ApiException(StatusCode.AUTHENTICATION_FAILED)

object AuthorizationException: ApiException(StatusCode.AUTHORIZATION_FAILED)

object InvalidParamException: ApiException(StatusCode.INVALID_PARAM)

object NotFoundAccountException: ApiException(StatusCode.NOT_FOUND_ACCOUNT)

object BadPasswordException: ApiException(StatusCode.BAD_PASSWORD)

object InvalidTokenException: ApiException(StatusCode.INVALID_TOKEN)

object NotFoundException: ApiException(StatusCode.NOT_FOUND)

fun invalidParamException(msg: String) = ApiException(StatusCode.INVALID_PARAM,msg)

fun authorizationException(msg: String) = ApiException(StatusCode.AUTHORIZATION_FAILED,msg)

fun authenticationException(msg: String) = ApiException(StatusCode.AUTHENTICATION_FAILED,msg)

fun notFindAccountException(msg: String) = ApiException(StatusCode.NOT_FOUND_ACCOUNT,msg)

fun badPasswordException(msg: String) = ApiException(StatusCode.BAD_PASSWORD,msg)

fun invalidTokenException(msg: String) = ApiException(StatusCode.INVALID_TOKEN,msg)

fun notFoundException(msg: String) = ApiException(StatusCode.NOT_FOUND,msg)