package com.example.practica6

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.practica6.componentes.Game
import com.example.practica6.componentes.GameView

class GamePong : AppCompatActivity(), View.OnClickListener {

    lateinit var menuDialog: AlertDialog
    var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(GameView(this))

        var actionBar = supportActionBar
        actionBar?.hide()
    }

    private fun crearDialog(viewId: Int = R.layout.end_game){
        menuDialog = AlertDialog.Builder(this).setView(viewId).setCancelable(false).create()
        menuDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onClick(view: View?) {
        if(menuDialog.isShowing){
            menuDialog.dismiss()
        }
        when(view?.id){
            R.id.btnReintentar -> {
                game?.state = Game.STATE.STARTED
            }
            R.id.btnSalir -> {
                finish()
            }
        }
    }
}