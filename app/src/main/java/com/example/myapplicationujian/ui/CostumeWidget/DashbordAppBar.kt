package com.example.myapplicationujian.ui.CostumeWidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplicationujian.R

@Composable
fun MenuButton(
    onPasienClick: () -> Unit,
    onTerapisClick: () -> Unit,
    onJenisTerapiClick: () -> Unit,
    onSesiTerapiClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = Color(0xFFD6ED17), // Custom color #D6ED17FF
                shape = RoundedCornerShape(24.dp)
            )
            .padding(8.dp)
    ) {
        // Tombol Pasien
        IconButton(onClick = onPasienClick) {
            Icon(
                painter = painterResource(id = R.drawable.pasien), // Replace with your drawable resource
                contentDescription = "Pasien",
                tint = Color.Black // Customize icon color if needed
            )
        }

        // Tombol Terapis
        IconButton(onClick = onTerapisClick) {
            Icon(
                painter = painterResource(id = R.drawable.terapis), // Replace with your drawable resource
                contentDescription = "Terapis",
                tint = Color.Black
            )
        }

        // Tombol Jenis Terapi
        IconButton(onClick = onJenisTerapiClick) {
            Icon(
                painter = painterResource(id = R.drawable.jenisterapi), // Replace with your drawable resource
                contentDescription = "Jenis Terapi",
                tint = Color.Black
            )
        }

        // Tombol Sesi Terapi
        IconButton(onClick = onSesiTerapiClick) {
            Icon(
                painter = painterResource(id = R.drawable.sesiterapis), // Replace with your drawable resource
                contentDescription = "Sesi Terapi",
                tint = Color.Black
            )
        }
    }
}
