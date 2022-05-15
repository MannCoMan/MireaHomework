package ru.mirea.kryazhin.socketconnection

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket


object SocketUtils {
    @Throws(IOException::class)
    fun getReader(s: Socket): BufferedReader {
        return BufferedReader(InputStreamReader(s.getInputStream()))
    }

    @Throws(IOException::class)
    fun getWriter(s: Socket): PrintWriter {
        return PrintWriter(s.getOutputStream(), true)
    }
}