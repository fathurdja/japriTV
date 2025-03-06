import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japritv.ui.theme.JapriTvTheme
import kotlinx.coroutines.delay

@Composable
fun LoadingUpload(modifier: Modifier = Modifier) {
    // Defining state for progress and upload status
    var progress by remember { mutableStateOf(0f) } // To track progress
    var isUploading by remember { mutableStateOf(true) } // To control the upload state

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Mengunggah",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "${(progress * 100).toInt()}% • ${((100 - (progress * 100)) / 2).toInt()} seconds remaining",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Blue
            )
        }
    }

    // Simulate upload progress
    LaunchedEffect(Unit) {
        while (progress < 1f) {
            delay(1000) // Simulate each second of upload
            progress += 0.05f // Increase progress by 5% every second
        }
        isUploading = false // Once upload is complete
    }
}

@Preview
@Composable
private fun LoadingUploadPreview() {
    JapriTvTheme {
        LoadingUpload()
    }
}