package com.example.tiempoviewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.tiempoviewbinding.databinding.ActivityMainBinding

// con el viewbinding no se debe inicializar los miembros de layout por debajo de la declaracion del binding... se deben usar directamente en donde se vayan a utilizar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var juegoEmpezado = false
    private var puntos = 0
    private lateinit var cuentaAtras:CountDownTimer
    private var tiempoInicialmilis:Long = 20000
    private var intervaloTiempo: Long = 1000
    private var tiempoRestante = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnPresioname.setOnClickListener { aumentarPuntuacion() }
        resetar()
    }

    private fun aumentarPuntuacion(){
        if (!juegoEmpezado) {empezarJuego()}
        puntos++

        val nuevosPuntos = getString(R.string.puntuacion,puntos)
        binding.tvPuntos.text = nuevosPuntos
    }

    private fun resetar(){
        puntos = 0

        val puntosIniciales = getString(R.string.puntuacion,puntos)
        binding.tvPuntos.text = puntosIniciales
        val tiempoInicial = getString(R.string.tiempo,20)
        binding.tvTiempo.text = tiempoInicial

        cuentaAtras = object : CountDownTimer(tiempoInicialmilis,intervaloTiempo){  // algoritmo que permite la cuenta atras
            override fun onTick(millisUntilFinished: Long) {
                tiempoRestante = millisUntilFinished.toInt() / 1000

                val tiempoAvanzando = getString(R.string.tiempo,tiempoRestante)
                binding.tvTiempo.text = tiempoAvanzando
            }

            override fun onFinish() {
                terminarJuego()
            }
        }
        juegoEmpezado = false
    }

    private fun empezarJuego(){
        cuentaAtras.start()
        juegoEmpezado = true
    }
    private fun terminarJuego(){
        Toast.makeText(this,getString(R.string.juegoTerminado,puntos),Toast.LENGTH_LONG).show()
        resetar()
    }
}
