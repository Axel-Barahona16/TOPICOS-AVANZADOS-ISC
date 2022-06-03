package com.example.practica6.interfaz

import android.graphics.Canvas

interface GameLoop {
    var updateRate: Int
    var timeToUpdate: Long
    val shouldUpdate: Boolean
        get() = (System.currentTimeMillis() >= timeToUpdate)

    fun render(canvas: Canvas? = null)
    fun update()
}