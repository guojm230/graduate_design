package com.guojm.design_server.ioc

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scopes
import com.guojm.design_server.dao.ResourceDao
import com.guojm.design_server.dao.SystemDao
import com.guojm.design_server.dao.impl.ResourceDaoImpl
import com.guojm.design_server.dao.impl.SystemDaoImpl
import io.vertx.core.Vertx
import io.vertx.kotlin.mysqlclient.mySQLConnectOptionsOf
import io.vertx.kotlin.sqlclient.poolOptionsOf
import io.vertx.mysqlclient.MySQLPool
import io.vertx.sqlclient.Pool
import javax.inject.Singleton

class RepositoryModule : AbstractModule() {

    override fun configure() {
        bind(SystemDao::class.java).to(SystemDaoImpl::class.java).`in`(Scopes.SINGLETON)
        bind(ResourceDao::class.java).to(ResourceDaoImpl::class.java).`in`(Scopes.SINGLETON)
    }

    @Provides
    @Singleton
    fun mysqlPoolProvider(vertx: Vertx): Pool {
        return MySQLPool.pool(
            vertx, mySQLConnectOptionsOf(
                database = "design_platform",
                user = "root",
                password = "gjm970206",
                host = "localhost"
            ), poolOptionsOf()
        )
    }

}