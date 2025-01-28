package com.example.myapplicationujian.ui.View


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplicationujian.R
import com.example.myapplicationujian.ui.Navigasi.DestinasiNavigasi

object DestinasiSplashView : DestinasiNavigasi {
    override val route = "Spalsh"
    override val titleRes = "Utama"
}
@Composable
fun SplashView(
    navigatetoSpalash: () -> Unit,
)


{

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.log),
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )
        Spacer(
            modifier = Modifier.padding(16.dp),

        )
        Button(
            onClick = {navigatetoSpalash()},
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD6ED17), // Warna latar belakang
                contentColor = Color.Black // Warna teks
            )

        ) {
            Text("Mulai")
        }
    }
}