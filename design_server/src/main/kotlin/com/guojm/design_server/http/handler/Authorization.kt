package com.guojm.design_server.http.handler

import java.util.function.Predicate

/**
 * 配置AuthorizationConfig
 * 每个Config只能代表一种认证类型，如果重复调用方法则会覆盖上一个配置
 * 如调用: hasRole()后调用hasAuthority()则会覆盖hasRole的配置
 * 如果想要表达hasRole and hasAuthority()请使用：
 * hasRole().and().hasAuthority()
 */
interface AuthorizationConfig{
    fun hasRole(vararg roles: String): AuthorizationConfigBuilder

    fun anyRole(vararg roles: String): AuthorizationConfigBuilder

    fun hasAuthority(vararg auths: String): AuthorizationConfigBuilder

    fun anyAuthority(vararg auths: String): AuthorizationConfigBuilder
}

/**
 * 注意结合顺序问题
 * a.and().b.or().c.and().d
 * ((a && b) || c) && d
 */
interface AuthorizationConfigBuilder{
    fun and(): AuthorizationConfig

    fun or(): AuthorizationConfig

    fun end(): AuthorizationConfig
}

interface AuthorizationPredicate: AuthorizationConfig,Predicate<List<String>>

open class AuthorizationResolver: AuthorizationPredicate{
    private var authList = listOf<String>()
    private var authType = AuthorizeType.NOOP

    override fun test(auths: List<String>): Boolean {
        return when(authType){
            AuthorizeType.NOOP-> true
            AuthorizeType.HAS-> authList.all { required-> auths.any { required == it } }
            AuthorizeType.ANY-> authList.any { required-> auths.any { required == it }}
        }
    }

    override fun hasRole(vararg roles: String): AuthorizationConfigBuilder {
        authType = AuthorizeType.HAS
        authList = roles.map { "ROLE_$it" }
        return AuthorizationConfigBuilderImpl(this)
    }

    override fun anyRole(vararg roles: String): AuthorizationConfigBuilder {
        authType = AuthorizeType.ANY
        authList = roles.map { "ROLE_$it" }
        return AuthorizationConfigBuilderImpl(this)
    }

    override fun hasAuthority(vararg auths: String): AuthorizationConfigBuilder {
        authType = AuthorizeType.HAS
        authList = listOf(*auths)
        return AuthorizationConfigBuilderImpl(this)
    }

    override fun anyAuthority(vararg auths: String): AuthorizationConfigBuilder {
        authType = AuthorizeType.ANY
        authList = listOf(*auths)
        return AuthorizationConfigBuilderImpl(this)
    }
}

class AuthorizationConfigBuilderImpl(private var authConfig: AuthorizationPredicate): AuthorizationConfigBuilder{
    override fun and(): AuthorizationConfig {
        return object : AuthorizationResolver(){
            override fun test(auths: List<String>): Boolean {
                return authConfig.test(auths) && super.test(auths)
            }
        }
    }

    override fun or(): AuthorizationConfig {
        return object : AuthorizationResolver(){
            override fun test(auths: List<String>): Boolean {
                return authConfig.test(auths) || super.test(auths)
            }
        }
    }

    override fun end(): AuthorizationConfig {
        return authConfig
    }
}

enum class AuthorizeType(val value: Int){
    NOOP(0),
    HAS(1),
    ANY(2),
}