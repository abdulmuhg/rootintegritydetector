package com.abdulmuhg.rootintegritydetector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RootIntegrityApp() }
    }
}

@Composable
fun RootIntegrityApp() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface(Modifier.fillMaxSize()) {
            IntegrityScreen()
        }
    }
}

@Composable
fun IntegrityScreen() {
    var json by remember { mutableStateOf<String?>(null) }
    var score by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Root Integrity Detector", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Runs native (C++) checks via JNI: su paths, system properties, SELinux enforcement. " +
                    "Aggregates an Integrity Score (0–100).",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = {
                val report = NativeChecker.nativeCheck()
                json = prettify(report)
                score = scoreFromReport(report)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Run Integrity Check")
        }

        if (score != null) {
            val s = score!!
            val color = if (s >= 80) MaterialTheme.colorScheme.tertiary
            else MaterialTheme.colorScheme.error
            AssistChip(
                onClick = {},
                label = { Text("Integrity score: $s / 100") },
                colors = AssistChipDefaults.assistChipColors(
                    labelColor = color
                )
            )
        }

        if (json != null) {
            Text("Report (native JSON):", style = MaterialTheme.typography.titleMedium)
            Surface(
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    json!!,
                    modifier = Modifier.padding(12.dp),
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

private fun scoreFromReport(json: String): Int {
    return try {
        val o = JSONObject(json)
        var s = 100
        if (o.optBoolean("suFound")) s -= 70
        if (o.optString("roBuildTags").contains("test-keys", true)) s -= 15
        if (o.optString("roDebuggable") == "1") s -= 10
        if (o.optString("roSecure") == "0") s -= 10
        if (o.optString("selinuxEnforce").trim() == "0") s -= 10
        s.coerceIn(0, 100)
    } catch (e: Exception) {
        50
    }
}

private fun prettify(json: String) = try {
    JSONObject(json).toString(2)
} catch (_: Exception) {
    json
}
