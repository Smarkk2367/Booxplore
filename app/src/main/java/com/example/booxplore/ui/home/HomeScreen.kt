package com.example.booxplore.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.booxplore.ui.navigation.detailRoute

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column {
        Text("Home Screen")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(detailRoute("TEST_ID"))
            }
        ) {
            Text("Go to Detail")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("favorites") }) {
            Text("Go to Favorites")
        }
    }
}