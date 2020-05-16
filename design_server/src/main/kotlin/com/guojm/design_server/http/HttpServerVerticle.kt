package com.guojm.design_server.http

import com.guojm.design_server.commons.NotFoundAccountException
import com.guojm.design_server.dao.SystemDao
import com.guojm.design_server.http.handler.*
import com.guojm.design_server.injector
import com.guojm.design_server.ioc.get
import com.guojm.design_server.ioc.getInstance
import com.guojm.design_server.model.UserDetail
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpServer
import io.vertx.ext.auth.User
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HttpServerVerticle : CoroutineVerticle() {
    private lateinit var server: HttpServer

    private lateinit var router: Router
    private lateinit var logger: Logger

    override suspend fun start() {
        router = Router.router(vertx)
        logger = LoggerFactory.getLogger(javaClass)
        server = vertx.createHttpServer()
        configRoute()
        logger.debug("正在启动httpserver")
        try {
            server.requestHandler(router).listenAwait(8080)
            logger.debug("启动成功,端口:${server.actualPort()}")
        } catch (e: Exception) {
            e.printStackTrace()
            logger.debug("启动失败")
        }
    }

    //配置路由
    private fun configRoute() {
        configSysRoute()
        val globalErrorHandler = GlobalErrorHandler()
        //全局配置
        router.routes.forEach { route ->
            route.failureHandler(globalErrorHandler)
        }
    }

    private fun configSysRoute(){
        val systemDao = injector.getInstance(SystemDao::class.java)
        //注册和权限认证
        router.post("/auth/token").handler(PermitAllAuthenticationHandler).handler(injector[LoginHandler::class])
        router.post("/register/:type").handler(PermitAllAuthenticationHandler).handler(injector[RegisterHandler::class])
        //静态资源控制器
        router.get("/static/*").handler(PermitAllAuthenticationHandler).handler(StaticResourceHandler())
        //资源控制器
        router.get("/resources/:id").handler(PermitAllAuthenticationHandler).handler(injector[ResourceHandler::class])

        router.get("/user/detail").handler(AuthenticateHandler).coroutineHandler { event->
            val currentUser: User = event[CURRENT_USER]
            val tokenPayload = currentUser.principal().mapTo(TokenPayload::class.java)
            val fullUser = systemDao.findFullUser(tokenPayload.id)
                ?: throw NotFoundAccountException
            event.response().endJson(UserDetail(
                fullUser.user.id,fullUser.user.username,fullUser.user.name,fullUser.user.email,
                fullUser.user.telephone,fullUser.user.description,fullUser.roles.map { it.name },
                fullUser.authorities.map { it.name }
            ))
        }

    }
}