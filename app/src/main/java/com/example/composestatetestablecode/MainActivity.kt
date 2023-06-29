@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composestatetestablecode

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composestatetestablecode.ui.theme.ComposeStateTestableCodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStateTestableCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PyeongToMeter()
                }
            }
        }
    }
}

/**
 * state 만 관리하고 UI는 다른 Composable 로 넘김.
 */
@Composable
fun PyeongToMeter() {
    var pyeong by rememberSaveable {
        mutableStateOf("23")
    }
    var meter by rememberSaveable {
        mutableStateOf("${23 * 3.306}")
    }

    PyeongToMeterScreen(pyeong = pyeong, meter = meter) {
        if (it.isBlank()) {
            pyeong = ""
            meter = ""
            return@PyeongToMeterScreen
        }

        val numeric = it.toDoubleOrNull() ?: return@PyeongToMeterScreen
        pyeong = it
        meter = (numeric * 3.306).toString()
        Log.d("TAG", "PyeongToMeter: $it / $numeric / $pyeong / $meter")
    }
}

/**
 * state 없이 값만 받아서 testable 하고
 * 수정이 용이한 형태로 변경. (State Hoisting)
 */
@Composable
fun PyeongToMeterScreen(
    pyeong: String,
    meter: String,
    onPyeongChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = pyeong,
            onValueChange = onPyeongChange,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            label = {
                Text(text = "평")
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = meter,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            label = {
                Text(text = "미터")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeStateTestableCodeTheme {
        PyeongToMeter()
    }
}