package com.example.delivaroos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delivaroos.ui.components.FoodItem
import com.example.delivaroos.ui.components.ScreenLayout
import com.example.delivaroos.viewmodels.CartViewModel
import com.example.delivaroos.viewmodels.FoodViewModel
import com.example.delivaroos.ui.preview.PreviewData
import com.example.delivaroos.ui.preview.PreviewFoodViewModel
import com.example.delivaroos.ui.preview.PreviewCartViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.example.delivaroos.ui.theme.DelivaroosTheme

@Composable
fun HomeScreen(
    foodViewModel: FoodViewModel,
    cartViewModel: CartViewModel
) {
    ScreenLayout(
        title = "Delivaroos"
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val foodItems by foodViewModel.foodItems.observeAsState(emptyList())
            val isLoading by foodViewModel.isLoading.observeAsState(initial = true)
            val error by foodViewModel.error.observeAsState()

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (error != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = error ?: "An error occurred",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { foodViewModel.loadFoodItems() }) {
                        Text("Retry")
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Today's Menu",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    if (foodItems.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No foods available")
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(foodItems) { food ->
                                FoodItem(
                                    food = food,
                                    onAddToCart = { 
                                        cartViewModel.addToCart(food)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DelivaroosTheme {
        HomeScreen(
            foodViewModel = PreviewFoodViewModel(),
            cartViewModel = PreviewCartViewModel()
        )
    }
}

private fun previewFoodViewModel(): FoodViewModel {
    return object : FoodViewModel() {
        init {
            _foodItems.value = listOf(
                PreviewData.sampleFood,
                PreviewData.sampleFood.copy(id = "2", name = "Veggie Burger"),
                PreviewData.sampleFood.copy(id = "3", name = "Fish Burger")
            )
            _isLoading.value = false
        }
    }
}

private fun previewCartViewModel(): CartViewModel {
    return CartViewModel()
} 