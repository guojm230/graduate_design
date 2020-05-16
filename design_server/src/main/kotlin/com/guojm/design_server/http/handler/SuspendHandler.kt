package com.guojm.design_server.http.handler

import com.guojm.design_server.http.VertxScope
import com.guojm.design_server.http.getDefaultVertx
import io.vertx.core.Context
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * 抛出的异常会直接将路由失败，然后进入failureHandler
 */
abstract class AbstractSuspendRouteHandler: Handler<RoutingContext>{

    protected val exceptionHandler: ((CoroutineContext, Throwable, RoutingContext) -> Unit)
        get() = { _, throwable, routingContext -> routingContext.fail(throwable);}

    override fun handle(event: RoutingContext) {
        preConfigure(event)
        launch(event){
            suspendHandle(event)
        }
    }

    /**
     * 执行一些需要立即做的配置
     */
    protected open fun preConfigure(context: RoutingContext){}

    abstract suspend fun suspendHandle(context: RoutingContext)

    fun launch(context: RoutingContext, block: suspend CoroutineScope.()->Unit){
        VertxScope.launch (coroutineExceptionHandler(context)){
            block()
        }
    }

    private fun coroutineExceptionHandler(context: RoutingContext)
            = CoroutineExceptionHandler{c,th-> exceptionHandler(c,th,context) }
}
