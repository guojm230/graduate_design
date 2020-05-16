package com.guojm.design_server.http.handler

import com.guojm.design_server.commons.*
import com.guojm.design_server.http.VertxScope
import com.guojm.design_server.injector
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.ext.auth.User
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val CURRENT_USER = "CURRENT_USER"

const val IS_AUTHENTICATED = "IS_AUTHENTICATED"
const val IS_AUTHORIZED = "IS_AUTHORIZED"
const val AUTHENTICATED_TYPE = "AUTHENTICATED_TYPE"
const val ACCESS_TOKEN = "access_token"

interface AuthenticationHandler : Handler<RoutingContext> {
    /**
     * 互斥的四个配置
     */
    fun authenticated(): AuthenticationHandler

    fun permitAll(): AuthenticationHandler

    fun denyAll(): AuthenticationHandler

    fun anonymous(): AuthenticationHandler

    fun authorization(): AuthorizationConfig
}

enum class AuthenticationType(val value: Int, val description: String) {
    UNSET(-1, "未设置"),
    PERMIT_ALL(1, "允许所有用户访问"),
    DENY_ALL(2, "拒绝所有用户访问"),
    ANONYMOUS(3, "只允许匿名访问"),
    AUTHENTICATED(4, "允许认证的用户访问");

    override fun toString(): String {
        return this.description
    }

    companion object {
        fun valueOf(value: Int): AuthenticationType {
            return values().find { value == it.value }
                ?: throw IllegalArgumentException("未找到value:$value 的值")
        }
    }
}

/**
 * 认证token控制器
 */
class AuthenticationHandlerImpl @Inject constructor(val jwtAuth: JWTAuth) :
    AbstractSuspendRouteHandler(), AuthenticationHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val authorizationResolver = AuthorizationResolver()
    private var authenticateType: AuthenticationType = AuthenticationType.UNSET
    override fun authenticated(): AuthenticationHandler {
        ifAllowModify(AuthenticationType.AUTHENTICATED)
        return this
    }

    override fun permitAll(): AuthenticationHandler {
        ifAllowModify(AuthenticationType.PERMIT_ALL)
        return this
    }

    override fun denyAll(): AuthenticationHandler {
        ifAllowModify(AuthenticationType.DENY_ALL)
        return this
    }

    override fun anonymous(): AuthenticationHandler {
        ifAllowModify(AuthenticationType.ANONYMOUS)
        return this
    }

    override fun authorization(): AuthorizationConfig {
        ifAllowModify(AuthenticationType.AUTHENTICATED)
        return authorizationResolver
    }

    override suspend fun suspendHandle(context: RoutingContext) {
        //默认为Authenticated
        if (authenticateType == AuthenticationType.UNSET)
            authenticateType = AuthenticationType.AUTHENTICATED
        context.put(AUTHENTICATED_TYPE, authenticateType.value)
        context.put(IS_AUTHORIZED, false)
        context.put(IS_AUTHENTICATED, false)

        when (authenticateType) {
            AuthenticationType.PERMIT_ALL -> {
                context.put(IS_AUTHENTICATED, true)
                context.next()
            }
            AuthenticationType.DENY_ALL -> throw AuthenticationException
            AuthenticationType.ANONYMOUS -> {
                val asyncResult = authenticate(context)
                if (!asyncResult.succeeded()) {
                    throw authenticationException("资源只允许匿名访问")
                }
                context.put(IS_AUTHENTICATED, true)
                context.next()
            }
            //Authenticated
            else -> {
                val asyncResult = authenticate(context)
                if (asyncResult.succeeded()) {
                    context.put(IS_AUTHENTICATED, true)
                    val user = asyncResult.result()
                    context.put(CURRENT_USER, user)
                    val tokenPayload = user.principal().mapTo(TokenPayload::class.java)
                    logger.debug("${user}认证成功")
                    //检查授权
                    if (authorizationResolver.test(tokenPayload.roles.map { "ROLE_$it" } + tokenPayload.authorities)) {
                        context.put(IS_AUTHORIZED, true)
                        context.next()
                    } else throw AuthorizationException
                } else throw InvalidTokenException
            }
        }
    }

    private suspend fun authenticate(context: RoutingContext) = suspendCoroutine<AsyncResult<User>> { cont ->
        val token = context.request().getParam(ACCESS_TOKEN)

        if (token == null) {
            cont.resumeWithException(AuthenticationException)
            return@suspendCoroutine
        }
        jwtAuth.authenticate(
            jsonObjectOf(
                "jwt" to token
            )
        ) {
            cont.resume(it)
        }
    }

    private fun ifAllowModify(type: AuthenticationType) {
        if (type == this.authenticateType)
            return
        if (authenticateType == AuthenticationType.UNSET) {
            this.authenticateType = type
        } else throw IllegalArgumentException("认证类型已被设置为:$type ;不可修改")
    }
}


data class TokenPayload(
    val id: Int, val username: String,
    val roles: List<String>, val authorities: List<String>
)

val PermitAllAuthenticationHandler = injector.getInstance(AuthenticationHandler::class.java).permitAll()
val AuthenticateHandler = injector.getInstance(AuthenticationHandler::class.java).authenticated()
val AnonymousAuthenticationHandler = injector.getInstance(AuthenticationHandler::class.java).anonymous()
val DenyAllAuthenticationHandler = injector.getInstance(AuthenticationHandler::class.java).denyAll()