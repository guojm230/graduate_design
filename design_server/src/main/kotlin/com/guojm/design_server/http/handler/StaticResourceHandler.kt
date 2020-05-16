package com.guojm.design_server.http.handler

import com.guojm.design_server.commons.InvalidParamException
import com.guojm.design_server.commons.NotFoundException
import com.guojm.design_server.commons.invalidParamException
import com.guojm.design_server.commons.notFoundException
import com.guojm.design_server.dao.ResourceDao
import com.guojm.design_server.http.ResourceManager
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.await
import io.vertx.sqlclient.Pool
import java.io.File
import java.net.URL
import javax.inject.Inject

class StaticResourceHandler : AbstractSuspendRouteHandler(){
    override suspend fun suspendHandle(context: RoutingContext) {
        val path = context.request().path().substringAfter("/")
        val url = this::class.java.classLoader.getResource(path)?:
                throw notFoundException("文件不存在:$path")
        val buffer = context.vertx().fileSystem().readFile(url.path).await()
        context.response().apply {
            putHeader("Content-Type",ResourceManager.resolveMIME(url.path))
            end(buffer)
        }
    }
}

class ResourceHandler @Inject constructor(private val rdao: ResourceDao) : AbstractSuspendRouteHandler(){
    override suspend fun suspendHandle(context: RoutingContext) {
        val id = try {
            context.request().getParam("id").toInt()
        } catch (e: Exception) {
            throw invalidParamException("resourceId格式错误")
        }
        val resource = rdao.findResource(id) ?: throw notFoundException("资源不存在")
        context.response().apply {

        }
    }
}
