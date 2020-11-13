package com.realtimemap.repo.remote

import com.realtimemap.base.SingletonHolder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class SocketClient private constructor(socketParams: SocketParams) {

    private var socket: Socket = Socket(socketParams.ip, socketParams.port)
    private var out: PrintWriter = PrintWriter(socket.getOutputStream(), true)
    private var `in`: BufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))

    init {
        out.println(socketParams.authentication)
    }

    fun listen(f: (String) -> Unit) {
        while (!socket.isClosed) {
            `in`.readLine()?.let { f.invoke(it) }
        }
    }

    fun disconnect() {
        socket.close()
        out.close()
        `in`.close()
    }

    companion object Holder : SingletonHolder<SocketClient, SocketParams>(::SocketClient)
}
