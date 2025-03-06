package com.example.japritv.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBox(
    modifier: Modifier = Modifier,
    searchText: String,
    onTextChanged: (String) -> Unit,
    onClearClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier
                .weight(1f)
                .height(28.dp)
                .background(
                    color = Color(0xFF333333),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 8.dp)
                )

                BasicTextField(
                    value = searchText,
                    onValueChange = { onTextChanged(it) }, // Panggil callback saat mengetik
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.White),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.White,
                        fontSize = 12.sp
                    ),
                    decorationBox = { innerTextField ->
                        if (searchText.isEmpty()) {
                            Text(
                                text = "Crush landing on you",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                )

                if (searchText.isNotEmpty()) {
                    IconButton(
                        onClick = { onClearClick() }, // Menghapus teks dengan callback
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.gift_vector_icon_1),
            contentDescription = "Gift",
            modifier = Modifier.size(24.dp)
        )
    }
}


//@Preview
//@Composable
//private fun CustomSearchBoxPreview() {
//    JapriTvTheme {
//        CustomSearchBox(
//            searchText = "",
//            onSearchTextChanged = {},
//            onSearchClick = {}
//        )
//    }
//}
