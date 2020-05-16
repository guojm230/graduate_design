package com.guojm.design_server.dao

import com.guojm.design_server.model.Resource

interface ResourceDao {
    suspend fun findResource(id: Int): Resource?

    suspend fun findResources(ids: Array<Int>): List<Resource>

    suspend fun insert(resource: Resource): Int

    suspend fun delete(id: Int): Boolean
}