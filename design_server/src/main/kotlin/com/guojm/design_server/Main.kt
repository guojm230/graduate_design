package com.guojm.design_server

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Key
import com.guojm.design_server.configuration.DEFAULT_RESOURCE_PATH
import com.guojm.design_server.configuration.RESOURCE_PATH_KEY
import com.guojm.design_server.dao.Table
import com.guojm.design_server.http.HttpServerVerticle
import com.guojm.design_server.ioc.MainModule
import com.guojm.design_server.ioc.RepositoryModule
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.kotlin.sqlclient.preparedBatchAwait
import io.vertx.kotlin.sqlclient.preparedQueryAwait
import io.vertx.mysqlclient.MySQLPool
import io.vertx.sqlclient.Pool
import io.vertx.sqlclient.Tuple
import kotlinx.coroutines.runBlocking
import java.util.stream.Collectors

val injector: Injector by lazy(::initInjector)
/**
 * Main方法
 */
fun main(args: Array<String>)  = runBlocking {
    initProperty(args)
    ::injector.get()
    startVertx()
}

fun initProperty(args: Array<String>){
    //Resource文件夹的储存路径
    args.forEachIndexed{index,str->
        if (str == "-r"){
            var path  = try {
                args[index +1];
            } catch (e: Exception){
                DEFAULT_RESOURCE_PATH
            }
            if (!path.endsWith("/"))
                path += "/"
            System.setProperty(RESOURCE_PATH_KEY,path);
            return@forEachIndexed
        }
    }
}

fun initInjector(): Injector{
    return Guice.createInjector(MainModule(),RepositoryModule())
}

//启动vertx
fun startVertx() {
    val vertx = injector.getInstance(Key.get(Vertx::class.java))
    vertx.deployVerticle(HttpServerVerticle::class.java, DeploymentOptions()).setHandler {
        if (it.failed()) {
            throw it.cause()
        }
    }
}