package com.guojm.design_server.dao.impl

import com.guojm.design_server.commons.ApiException
import com.guojm.design_server.commons.StatusCode
import com.guojm.design_server.dao.SystemDao
import com.guojm.design_server.dao.Table
import com.guojm.design_server.model.*
import io.vertx.kotlin.sqlclient.beginAwait
import io.vertx.kotlin.sqlclient.commitAwait
import io.vertx.kotlin.sqlclient.preparedQueryAwait
import io.vertx.kotlin.sqlclient.rollbackAwait
import io.vertx.mysqlclient.MySQLClient
import io.vertx.sqlclient.Pool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.Tuple
import java.time.LocalDateTime
import javax.inject.Inject

class SystemDaoImpl @Inject constructor(val pool: Pool) : SystemDao {
    val userMapper: (Row) -> User = { row ->
        User(
            row.getInteger("id"),
            row.getString("username"),
            row.getString("password"),
            row.getString("name"),
            row.getString("email"),
            row.getString("telephone"),
            row.getString("description"),
            row.getBoolean("enable"),
            row.getLocalDateTime("create_time"),
            row.getLocalDateTime("update_time")
        )
    }

    val roleMapper: (Row) -> Role = {
        Role(
            it.getInteger("id"),
            it.getString("name"),
            it.getString("description"),
            it.getBoolean("enable")
        )
    }

    val authorityMapper: (Row) -> Authority = {
        Authority(
            it.getInteger("id"),
            it.getString("name"),
            it.getString("description"),
            it.getBoolean("enable")
        )
    }

    override suspend fun findUser(id: Int): User? {
        return findOne(Table.SYS_USER, "id", id, userMapper)
    }

    override suspend fun findUserByUsername(username: String): User? {
        return findOne(Table.SYS_USER, "username", username, userMapper)
    }

    override suspend fun findRole(id: Int): Role? {
        return findOne(Table.SYS_ROLE, "id", id, roleMapper)
    }

    override suspend fun findRoleByName(name: String): Role? {
        return findOne(Table.SYS_ROLE, "name", name, roleMapper)
    }

    override suspend fun findAuthority(id: Int): Authority? {
        return findOne(Table.SYS_AUTHORITY, "id", id, authorityMapper)
    }

    override suspend fun findAuthoritiesByName(name: String): Authority? {
        return findOne(Table.SYS_AUTHORITY, "name", name, authorityMapper)
    }

    override suspend fun findFullUser(userId: Int): FullUser? {
        val account = findUser(userId) ?: return null
        val roleList = findRolesByUserId(userId)
        val authorities = findAuthoritiesByRoleIds(roleList.map(Role::id))
        return FullUser(account, roleList, authorities)
    }

    override suspend fun findRolesByUserId(userId: Int): List<Role> {
        val sql = """
            SELECT * FROM ${Table.SYS_ROLE} WHERE id IN (
                SELECT role_id FROM ${Table.SYS_USER_ROLES} WHERE user_id = ?
            )
        """.trimIndent()
        return pool.preparedQueryAwait(sql, Tuple.of(userId)).map(roleMapper)
    }

    override suspend fun findAuthoritiesByRoleIds(roleIds: List<Int>): List<Authority> {
        if (roleIds.isEmpty())
            return emptyList()
        val sql = """
            SELECT * FROM ${Table.SYS_AUTHORITY} WHERE id IN (
                SELECT authority_id FROM ${Table.SYS_ROLE_AUTHORITIES} WHERE role_id IN (${roleIds.joinToString(",")})
            )
        """.trimIndent()
        val rowSet = pool.preparedQueryAwait(sql)
        return rowSet.map(authorityMapper)
    }

    override suspend fun registerUser(rc: RegisterUser): Int {
        val exixtUser = findUserByUsername(rc.username)
        if (exixtUser != null) throw ApiException(StatusCode.INVALID_PARAM, "账户已经存在")
        val insertUser = """
            INSERT INTO ${Table.SYS_USER}
            (username,password,name,email,telephone,description,enable,create_time,update_time) 
            VALUES(?,?,?,?,?,?,?,?,?)
        """.trimIndent()
        val insertRoles = "INSERT INTO ${Table.SYS_USER_ROLES} VALUES(?,?)"
        val transaction = pool.beginAwait()
        try {
            val result = transaction.preparedQueryAwait(
                insertUser,
                Tuple.of(rc.username, rc.password,
                    rc.name,rc.email,rc.telephone,rc.description,rc.enable,
                    LocalDateTime.now(), LocalDateTime.now())
            )
            val userId = result.property(MySQLClient.LAST_INSERTED_ID)
            if (rc.roleIds.isNotEmpty()) {
                transaction.preparedBatch(insertRoles, rc.roleIds.map {
                    Tuple.of(userId,it)
                })
            }
            transaction.commitAwait()
            return userId.toInt()
        } catch (e: Exception){
            transaction.rollbackAwait()
            throw e
        }
    }

    private suspend fun <T> findOne(
        tName: Table,
        pName: String, value: Any,
        mapper: (Row) -> T?
    ): T? {
        val setRow = pool.preparedQueryAwait("SELECT * FROM $tName WHERE $pName = ?", Tuple.of(value))
        return if (setRow.size() > 0) {
            mapper(setRow.first())
        } else null
    }
}