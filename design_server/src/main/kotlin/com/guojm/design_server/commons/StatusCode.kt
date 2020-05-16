package com.guojm.design_server.commons

import java.lang.IllegalArgumentException

/**
 * 表示结果的code，基于HttpStatus扩展3位，便于和HttpStatus转换
 */
enum class StatusCode(val value: Int,val msg: String) {
    SUCCESS(200000,"OK"),

    //授权
    AUTHENTICATION_FAILED(401000,"认证失败"),
    NOT_FOUND_ACCOUNT(401001,"账户不存在"),
    BAD_PASSWORD(401002,"密码错误"),
    DISABLE_ACCOUNT(401003,"账户被冻结"),
    INVALID_TOKEN(401004,"无效token"),

    AUTHORIZATION_FAILED(401100,"授权失败"),
    FORBID_ACCESS(403000,"禁止访问"),
    INVALID_PARAM(403001,"无效参数"),

    NOT_FOUND(404000,"未找到资源"),

    SERVER_ERROR(500000,"服务器异常");

    fun convertToHttp(): Int = value/1000

    companion object{
        fun valueOf(value: Int): StatusCode{
            return values().find { it.value == value }
                ?: throw IllegalArgumentException("未找到值为${value}的StatusCode")
        }
    }
}