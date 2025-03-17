package com.example.japritv.ui.components.payment


// Necessary imports
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import com.example.japritv.R
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.japritv.ui.theme.JapriTvTheme
import com.example.japritv.viewmodel.PaymentCategory



// Main Composable function
@Composable
fun ExpandableList(category: PaymentCategory, isInitiallyExpanded: Boolean = false, onItemClicked: () -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(isInitiallyExpanded) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp,  Color(0xFFE6E6E8), shape = RoundedCornerShape(8.dp))
    ) {
        // Header (judul dengan tombol expand/collapse)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(category.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Expand/Collapse"
            )
        }

        // Daftar item pembayaran (muncul hanya jika expanded == true)
        if (expanded) {
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                category.items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {onItemClicked() }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            ,

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.name,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(item.name, fontSize = 14.sp)
                    }

                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                }
            }
        }
    }
}


// Preview function
@Preview(showBackground = true)
@Composable
fun ListPaymentPreview() {
    JapriTvTheme {

    }
}
