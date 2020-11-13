package com.realtimemap.repo.remote

import java.util.*

class ObservableSocketClient(
    private val socketClient: SocketClient
) : Observable() {

    init {
        socketClient.listen {
            notifyObservers(it)
        }
    }
}
