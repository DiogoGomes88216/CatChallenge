package com.example.catchallenge.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.catchallenge.data.local.db.BreedDatabase
import com.example.catchallenge.data.local.entities.RemoteKeys
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class RemoteKeysDaoTest {

    private lateinit var database: BreedDatabase
    private lateinit var dao: RemoteKeysDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BreedDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = database.remoteKeysDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAll() = runTest {
        val remoteKey1 = RemoteKeys (
            id = "test1",
            prevKey = 0,
            nextKey = 2,
        )
        val remoteKey2 = RemoteKeys (
            id = "test2",
            prevKey = 0,
            nextKey = 2,
        )
        val remoteKeys = listOf(remoteKey1, remoteKey2)
        dao.insertAll(remoteKeys)
        val result1 = dao.getRemoteKeysBy("test1")
        val result2 = dao.getRemoteKeysBy("test2")

        assertEquals(remoteKey1, result1)
        assertEquals(remoteKey2, result2)
    }

    @Test
    fun clearRemoteKeys() = runTest{
        val remoteKey = RemoteKeys (
            id = "test1",
            prevKey = 0,
            nextKey = 2,
        )
        dao.insertAll(listOf( remoteKey))

        dao.clearRemoteKeys()
        val result = dao.getRemoteKeysBy("test1")
        assertEquals(null, result)
    }
}