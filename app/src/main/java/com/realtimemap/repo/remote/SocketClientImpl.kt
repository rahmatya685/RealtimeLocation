package com.realtimemap.repo.remote

import com.realtimemap.util.Constants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import javax.inject.Inject

class SocketClientImpl @Inject constructor(
    socketParams: SocketParams
) : SocketClient {

    private lateinit var socket: Socket
    private lateinit var out: PrintWriter
    private lateinit var `in`: BufferedReader

    init {
        GlobalScope.launch {
            socket = Socket(socketParams.ip, socketParams.port)
            out = PrintWriter(socket.getOutputStream(), true)
            `in` = BufferedReader(InputStreamReader(socket.getInputStream()))
            out.println(socketParams.authentication)
        }
    }

    override fun listenToUpdates(userLocationsUpdate: (List<String>) -> Unit) {
        while (isSocketValid()) {
            `in`.readLine()?.let { raw ->
                val purified = purifyText(raw)
                if (isUpdate(raw))
                    userLocationsUpdate.invoke(purified)
            }
        }
    }

    override fun listenToUserLocation(userLocations: (List<String>) -> Unit) {
        while (isSocketValid()) {
            `in`.readLine()?.let { raw ->
                val purified = purifyText(raw)
                if (isUserList(raw))
                    userLocations.invoke(purified)
            }
        }
    }

    fun isSocketValid(): Boolean = ::socket.isInitialized && !socket.isClosed


    fun isUserList(text: String): Boolean = text.startsWith(Constants.KEY_USER_LIST)
    fun isUpdate(text: String): Boolean = text.startsWith(Constants.KEY_UPDATE)

    fun purifyText(text: String): List<String> {
        val refinedText = text.replace(Constants.KEY_USER_LIST, "")
            .replace(Constants.KEY_UPDATE, "")
        return refinedText.split(";").filter { !it.isNullOrBlank() }
    }

    override fun disconnect() {
        socket.close()
        out.close()
        `in`.close()
    }

}
