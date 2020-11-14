package com.realtimemap.repo.remote

import com.realtimemap.util.Constants
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SocketClientImplTest {

    lateinit var socket:SocketClientImpl

    fun initSocket(){
        val params =  SocketParams(
            ip = Constants.URL,
            port = Constants.PORT,
            authentication = Constants.AUTHENTICATION
        )
        socket = SocketClientImpl(params)
    }

    @Before
    fun setup() = initSocket()


    @Test
    fun `check if socket initialized and update info is available to listen`() {
        initSocket()
        socket.listenToUpdates {

            socket.disconnect()
        }
    }

    @Test
    fun `check if socket initialized and location info is available to listen`() {
        initSocket()
        socket.listenToUserLocation {

            socket.disconnect()
        }
    }

    @Test
    fun `check if validations on data is working`() {
        Assert.assertTrue(socket.isUserList(DummyData.USER_LIST))
        Assert.assertFalse(socket.isUserList("hI FROM ERORVER"))

        Assert.assertFalse(socket.isUpdate(DummyData.USER_LIST))
        Assert.assertTrue(socket.isUpdate(DummyData.UPDATE))

    }



    @Test
    fun `check if text body is parsed correctly`() {
        var textArrays =   socket.purifyText(DummyData.USER_LIST)
        Assert.assertTrue(textArrays.isNotEmpty())
        Assert.assertTrue(textArrays.count { it.isNullOrBlank() } ==0)

        textArrays= socket.purifyText("")

        Assert.assertTrue(textArrays.isEmpty())

    }


}