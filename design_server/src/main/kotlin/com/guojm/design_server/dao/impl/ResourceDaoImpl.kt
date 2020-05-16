package com.guojm.design_server.dao.impl

import com.guojm.design_server.dao.ResourceDao
import com.guojm.design_server.dao.Table
import com.guojm.design_server.model.Resource
import io.vertx.kotlin.sqlclient.preparedQueryAwait
import io.vertx.mysqlclient.MySQLClient
import io.vertx.sqlclient.Pool
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet
import io.vertx.sqlclient.Tuple
import java.util.function.Function
import javax.inject.Inject

class ResourceDaoImpl @Inject constructor(private val pool: Pool): ResourceDao {

    private val rowMapper : (Row)->Resource = {
        Resource(it.getInteger("id"),
            it.getString("name"),
            it.getString("fileName"),
            it.getString("path"),
            it.getString("suffix")
        )
    }

    override suspend fun findResource(id: Int): Resource? {
        val sql = "SELECT * FROM ${Table.RESOURCES} WHERE id = ?"
        val result = pool.preparedQueryAwait(sql, Tuple.of(id))
        if (result.size() == 0)
            return null
        return rowMapper(result.first())
    }

    override suspend fun findResources(ids: Array<Int>): List<Resource> {
        if (ids.isEmpty())
            return emptyList()
        val sql = "SELECT * FROM ${Table.RESOURCES} WHERE id IN (${ids.joinToString(",")})"
        val result = pool.preparedQueryAwait(sql)
        return result.map(rowMapper)
    }

    override suspend fun insert(resource: Resource): Int {
        val sql = """
            INSERT INTO ${Table.RESOURCES} VALUES(?,?,?,?,?)
        """.trimIndent()
        val result = pool.preparedQueryAwait(sql, tupleOf(resource))
        return result.property(MySQLClient.LAST_INSERTED_ID).toInt()
    }

    override suspend fun delete(id: Int): Boolean {
        val sql = """DELETE FROM ${Table.RESOURCES} WHERE id = $id"""
        val result = pool.preparedQueryAwait(sql)
        return result.rowCount() > 0
    }

    private fun tupleOf(r: Resource) = Tuple.of(r.id,r.name,r.fileName,r.path,r.suffix)
}