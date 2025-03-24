package com.example.japritv.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

data class BonusItem(
    val description: String,
    val date: String,
    val amount: Int,
    val image: Int
)

@Composable
fun CompanyBalanceCard(total: Int, image: Int, bonuses: List<BonusItem>) {
    Column (modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFF333333))
        .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 12.dp)
        ) {
            // Text untuk "Total Bonus Referral"
            Text(
                text = "Total Bonus Referral",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Icon, value, dan tambahan teks
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(image),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = total.toString(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }


        }
        Divider(color = Color.Gray, thickness = 1.dp)

        BonusList(bonuses)
    }
}

@Composable
fun BonusList(bonuses: List<BonusItem>) {
    Column (modifier = Modifier
        .padding(horizontal = 16.dp)
        .padding(vertical = 12.dp)
    ){
        bonuses.forEach { bonus ->
            BonusItemRow(bonus)
        }
    }
}

@Composable
fun BonusItemRow(bonus: BonusItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.height(56.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = bonus.description,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = bonus.date,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        Row {
            Image(
                modifier = Modifier.padding(end = 12.dp),
                painter = painterResource(bonus.image),
                contentDescription = null
            )
            Text(
                text = bonus.amount.toString(),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun PreviewCompanyBalanceCard() {
    val sampleBonuses = listOf(
        BonusItem("Bonus dari upload video", "21 Oktober 2024, 12:15 PM", 200, R.drawable.point_solid_1),
        BonusItem("Bonus dari referral", "20 Oktober 2024, 10:00 AM", 150, R.drawable.point_solid_1),
        BonusItem("Bonus tambahan event", "19 Oktober 2024, 05:30 PM", 300, R.drawable.point_solid_1)
    )

    CompanyBalanceCard(total = 650, image = R.drawable.point_solid_1, bonuses = sampleBonuses)
}
