package com.realtimemap

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.realtimemap.repo.remote.SocketClient
import com.realtimemap.repo.remote.SocketParams
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun sendMessage(msg: String) {
        val handler = Handler()
        val thread = Thread(
            Runnable {
                try {
                    val params = SocketParams(
                        ip = "***REMOVED***",
                        port = ***REMOVED***,
                        authentication = "***REMOVED***"
                    )
                    SocketClient.getInstance(params)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        )
        thread.start()
    }
}
