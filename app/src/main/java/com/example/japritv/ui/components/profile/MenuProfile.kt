package com.example.japritv.ui.components.profile


// Necessary imports
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun MenuProfile(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        // Row for each item
        RowItem(
            icon = com.example.japritv.R.drawable.theaters,
            text = "Riwayat Pembelian",
            onClick = {
                navController.navigate("riwayat_pembelian")
            })
        RowItem(
            icon = com.example.japritv.R.drawable.support,
            text = "Layanan Pelanggan",
            onClick = {
                navController.navigate("layanan_pelanggan")
            })
        RowItem(icon = com.example.japritv.R.drawable.settings, text = "Pengaturan", onClick = {
            navController.navigate("pengaturan")
        })
        RowItem(
            icon = com.example.japritv.R.drawable.info_outline,
            text = "Tentang Kami",
            onClick = {
                navController.navigate("tentang_kami")
            })
        RowItem(
            icon = com.example.japritv.R.drawable.language,
            text = "Pengaturan Bahasa",
            extraText = "Indonesia",
            extraTextColor = Color(0xFFFFA500),
            onClick = {
                navController.navigate("pengaturan_bahasa")
            })
        RowItem(
            icon = com.example.japritv.R.drawable.vector__10_,
            text = "Kebijakan Privasi",
            onClick = {
                navController.navigate("kebijakan_privasi")
            })
        RowItem(
            icon = com.example.japritv.R.drawable.lightbulb,
            text = "Marketing Plan",
            onClick = {
                navController.navigate("marketing_plan")
            })
        RowItemWithButton(
            icon = com.example.japritv.R.drawable.trash_outline,
            text = "Bersihkan cache",
            extraText = "5.05 MB",
            extraTextColor = Color(0xFFFFA500),
            onClick = {
                navController.navigate("bersihkan_cache")
            })
    }
}

@Composable
fun RowItem(
    icon: Int,
    text: String,
    extraText: String = "",
    extraTextColor: Color = Color.White,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, fontSize = 16.sp, color = Color.White)
        }
        if (extraText.isNotEmpty()) {
            Text(
                text = extraText,
                fontSize = 16.sp,
                color = extraTextColor,
                fontWeight = FontWeight.Bold
            )
        }
        Image(
            painter = painterResource(id = com.example.japritv.R.drawable.vector__11_),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun RowItemWithButton(
    icon: Int,
    text: String,
    extraText: String = "",
    extraTextColor: Color = Color.White,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, fontSize = 16.sp, color = Color.White)
        }
        if (extraText.isNotEmpty()) {
            Text(
                text = extraText,
                fontSize = 16.sp,
                color = extraTextColor,
                fontWeight = FontWeight.Bold
            )
        }
        // Button to trigger action for "Bersihkan Cache"
        Box(
            modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(Color(0xFF343434)).padding(4.dp).clickable { onClick() },

        ) {
            Text(
                "Bersihkan",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Image(
            painter = painterResource(id = com.example.japritv.R.drawable.vector__11_),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}


@Preview(showBackground = false)
@Composable
fun GeneralPreview() {
    var navController = rememberNavController()
    MenuProfile(navController = navController)
}
