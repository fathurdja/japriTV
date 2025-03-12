import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R

@Composable
fun DetailPembayaranInteractive() {
    var isExpanded by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier

            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)) // Add border here
            .padding(12.dp)

    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }, // Toggle expansion
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Detail Pembayaran",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Toggle Details"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show Details if expanded
        if (isExpanded) {
            Column(modifier = Modifier.padding(vertical = 25.dp)
            , horizontalAlignment = Alignment.End,) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.theaters),
                        tint = Color.Gray,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(24.dp),
                        contentDescription = null
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total episode di upload",
                            color = Color.Gray
                        )
                        Text(
                            text = "15 episode",
                            color = Color.Gray
                        )
                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.iconamoon_discount),
                        tint = Color.Gray,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(24.dp),
                        contentDescription = null
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Biaya per episode",
                            color = Color.Gray
                        )
                        Text(
                            text = "Rp150.000",
                            color = Color.Gray
                        )
                    }
                }

                Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp))
                // Total Payment
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Pembayaran",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Rp 2.250.000",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPayNowUI() {
    DetailPembayaranInteractive()
}
