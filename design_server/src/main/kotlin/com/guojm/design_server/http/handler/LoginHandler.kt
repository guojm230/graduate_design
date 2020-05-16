package com.guojm.design_server.http.handler

import com.guojm.design_server.commons.*
import com.guojm.design_server.dao.SystemDao
import com.guojm.design_server.http.VertxScope
import com.guojm.design_server.http.endJson
import com.guojm.design_server.http.security.PasswordEncoder
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * 登录控制器
 */
class LoginHandler @Inject constructor(
    private val rbac: SystemDao,
    private val passwordEncoder: PasswordEncoder,
    private val jwtAuth: JWTAuth
) : AbstractSuspendRouteHandler() {

    override fun preConfigure(context: RoutingContext) {
        context.request().isExpectMultipart = true
    }

    override suspend fun suspendHandle(context: RoutingContext) {
        val request = context.request()
        val response = context.response()
        request.endHandler {
            val username = request.getFormAttribute("username")
            val password = request.getFormAttribute("password")
            notEmptyElse(username, password) {
                context.fail(authenticationException("账号和密码不能为空"))
            }
            launch(context) {
                val ac = rbac.findUserByUsername(username)
                    ?: throw NotFoundAccountException
                if (!passwordEncoder.matches(password, ac.password))
                    throw BadPasswordException
                val fu = rbac.findFullUser(ac.id)!!
                val token = jwtAuth.generateToken(
                    JsonObject.mapFrom(
                        TokenPayload(
                            fu.user.id, fu.user.username,
                            fu.roles.map { it.name },
                            fu.authorities.map { it.name }
                        )
                    ))
                response.endJson(json {
                    obj(
                        "token" to token
                    )
                })
            }
        }
    }
}


