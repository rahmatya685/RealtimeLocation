package com.realtimemap.repo.remote

import com.realtimemap.util.Constants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import javax.inject.Inject
import kotlin.properties.Delegates

class SocketClientImpl @Inject constructor(
    socketParams: SocketParams
) : SocketClient {

    private lateinit var socket: Socket
    private lateinit var out: PrintWriter
    private lateinit var `in`: BufferedReader
    private lateinit var userLocations: (List<String>) -> Unit
    private lateinit var userLocationsUpdate: (String) -> Unit
    private var data by Delegates.observable("", { _, _, raw ->
        if (isUpdate(raw) && ::userLocationsUpdate.isInitialized){
            val cleanText =raw.replace(Constants.KEY_UPDATE,"").trim()
            userLocationsUpdate.invoke(cleanText )
        }
        if (isUserList(raw) && ::userLocations.isInitialized) {
            val purified = purifyText(raw)
            userLocations.invoke(purified)
        }

    })

    init {
        GlobalScope.launch {
            socket = Socket(socketParams.ip, socketParams.port)
            out = PrintWriter(socket.getOutputStream(), true)
            `in` = BufferedReader(InputStreamReader(socket.getInputStream()))
            out.println(socketParams.authentication)
        }
    }

    override fun listenToUpdates(userLocationsUpdate: (String) -> Unit) {
        this.userLocationsUpdate = userLocationsUpdate
        initListener()
    }

    override fun listenToUserLocation(userLocations: (List<String>) -> Unit) {
        this.userLocations = userLocations
        initListener()
    }

    private fun initListener() {
        while (isSocketValid()) {
            `in`.readLine()?.let { raw ->
                data = raw
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
