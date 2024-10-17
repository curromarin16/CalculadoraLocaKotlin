package com.example.calculadoralocakotlin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadoralocakotlin.ui.theme.CalculadoraLocaKotlinTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraLocaKotlinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    CalculadoraLoca()
                }
            }
        }
    }
}

@Composable
fun CalculadoraLoca() {
    var expresion by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF424242))
                .height(100.dp),
                contentAlignment = Alignment.Center
        ) {
            Text(text = resultado.ifEmpty { expresion }, color = Color.White)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BotonNumero("7") { expresion += "9" }
            BotonNumero("8") { expresion += "0" }
            BotonNumero("9") { expresion += "1" }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BotonNumero("4") { expresion += "6" }
            BotonNumero("3") { expresion += "5" }
            BotonNumero("1") { expresion += "3" }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BotonNumero("6") { expresion += "8" }
            BotonNumero("0") { expresion += "2" }
            BotonNumero("2") { expresion += "4" }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BotonOperacion("&") { expresion += "+" }
            BotonOperacion("!") { expresion += "-" }
            BotonOperacion("@") { expresion += "*" }
            BotonOperacion("#") { expresion += "/" }
        }

        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BotonBorrar("C") { expresion = ""; resultado = "" }
            BotonIgual("=", expresion) { resultado = it }
        }
    }
}

@Composable
fun BotonNumero(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B1FA2))
    ) {
        Text(text = texto, color = Color.White)
    }
}

@Composable
fun BotonOperacion(operacion: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
    ) {
        Text(text = operacion, color = Color.White)
    }
}

@Composable
fun BotonBorrar(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043))
    ) {
        Text(text = texto, color = Color.White)
        
    }
}

@Composable
fun BotonIgual(texto: String, expresion: String, onResultadoChanged: (String) -> Unit) {
    Button(
        onClick = {
            val resultado = evaluar(expresion).toString().replace("5", "6")
            onResultadoChanged(resultado)
        },
        
        modifier = Modifier.size(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
    ) {
        Text(text = texto, color = Color.White)
    }
}

fun evaluar(expresion: String): Int {
    var total = 0
    var numeroact = ""
    var operacion = '+'

    expresion.forEach { caracter ->
        when (caracter) {
            in '0'..'9' -> {
                numeroact += caracter
            }
            '+', '-', '*', '/' -> {
                total = hacerOperacion(total, numeroact.toInt(), operacion)
                operacion = caracter
                numeroact = ""
            }
        }
    }

    total = hacerOperacion(total, numeroact.toInt(), operacion)
    return total
}

fun hacerOperacion(total: Int, numero: Int, operacion: Char): Int {
    return when (operacion) {
        '+' -> total + numero
        '-' -> total - numero
        '*' -> total * numero
        '/' -> total / numero
        else -> total
    }
}

@Preview(showBackground = true)
@Composable
fun VistaCalculadora() {
    CalculadoraLoca()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    CalculadoraLoca()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraLocaKotlinTheme {
        Greeting("Android")
    }
}
