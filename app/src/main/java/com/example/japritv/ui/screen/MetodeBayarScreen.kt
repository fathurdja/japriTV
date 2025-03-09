package com.example.japritv.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.japritv.ui.components.Header
import com.example.japritv.ui.components.payment.ExpandableList
import com.example.japritv.viewmodel.PaymentViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun MetodeBayarScreen(viewModel: PaymentViewModel, navigateTo: ()->Unit) {
    val paymentMethods by viewModel.paymentMethods.observeAsState(emptyList())
        LazyColumn(modifier = Modifier.padding(vertical = 20.dp)) {
            itemsIndexed(paymentMethods) { index, category ->
                ExpandableList(category = category, isInitiallyExpanded = index == 0, onItemClicked = navigateTo)
            }
        }

}

//@Preview
//@Composable
//private fun MetodeBayarScreenPreview() {
//    val viewModel = PaymentViewModel()
//    MetodeBayarScreen(viewModel)
//}
