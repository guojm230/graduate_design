package com.guojm.design_server.model

import com.guojm.design_server.configuration.RESOURCE_PATH_KEY
import java.io.File
import java.time.LocalDateTime

data class User(
    val id: Int,
    val username: String,
    var password: String,
    val name: String,
    val email: String,
    val telephone: String,
    val description: String,
    var enable: Boolean,
    var createTime: LocalDateTime,
    var updateTime: LocalDateTime
)

data class Role(
    val id: Int,
    val name: String,
    val description: String,
    val enable: Boolean
)

data class Authority(
    val id: Int,
    val name: String,
    val description: String,
    val enable: Boolean
)

data class FullUser(val user: User,
                    val roles: List<Role>,
                    val authorities:List<Authority>)

data class RegisterUser(
                        val username: String,
                        val password: String,
                        val name: String,
                        val email: String,
                        val telephone: String,
                        val description: String,
                        var enable: Boolean,
                        var roleIds: List<Int> = emptyList(),
                        var id:Int? = null
)

//供客户端使用的UserDetail
data class UserDetail(
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val telephone: String,
    val description: String,
    val roles: List<String>,
    val authorities: List<String>
)

//文件
data class Resource(val id: Int,
                    val name: String,
                    val fileName: String,
                    val path: String,
                    val suffix: String
){
    val url: String
        get(){
            var p = path
            if (path.startsWith("/"))
                p = path.substring(1)
            p = System.getProperty(RESOURCE_PATH_KEY) + path
            if (!path.endsWith("/")){
                p += "/"
            }
            return path+fileName
        }
}