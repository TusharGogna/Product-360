package com.mdoc.product360

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mdoc.product360.ui.theme.Product360Theme
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class ProductActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Product360Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    ProductRotatingView(lifecycleScope)
                }
            }
        }
    }
}

@Composable
fun ProductRotatingView(lifecycleScope: LifecycleCoroutineScope) {
    val period = 128

    var imageIndex by remember {
        mutableIntStateOf(0)
    }

    var isMovementClockwise by remember {
        mutableStateOf(true)
    }

    val timer = Timer()
    timer.scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            lifecycleScope.launch(Dispatchers.Main) {
                if (isMovementClockwise) {
                    if (imageIndex == 19) imageIndex = 0
                    imageIndex++
                } else {
                    if (imageIndex == 0) imageIndex = 19
                    imageIndex--
                }
            }
        }
    }, 0, period.toLong())

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp),
            factory = { ctx ->
                ImageView(ctx).apply {
                    val drawable = ContextCompat.getDrawable(ctx, R.drawable.image_rotations)
                    setImageDrawable(drawable)
                }
            },
            update = { view ->
                view.setImageLevel(imageIndex)
            })
        Spacer(modifier = Modifier.height(8.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            onClick = { isMovementClockwise = !isMovementClockwise }) {
            Text(text = "Inverse Movement")
        }
    }

}