package com.realtimemap.repo.remote

import com.realtimemap.util.Constants
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SocketClientTest {

    lateinit var socket: SocketClient

    @Before
    fun setUp() {
        val params = SocketParams(
            ip = Constants.URL,
            port = Constants.PORT,
            authentication = Constants.AUTHENTICATION
        )
        socket = SocketClient.getInstance(params)
    }

    @Test
    fun `check if socket initialized and closed without error`() = runBlocking {
        // Given

        // When

        // Then
        socket.listen {
            assertEquals(false, it.isNullOrBlank())
            assertEquals(
                true,
                it.startsWith("USERLIST") ||
                        it.startsWith("UPDATE")
            )
            socket.disconnect()
        }
    }
}
