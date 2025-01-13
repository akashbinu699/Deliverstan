package com.example.delivaroos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.delivaroos.models.CartItem
import com.example.delivaroos.viewmodels.CartViewModel
import com.example.delivaroos.viewmodels.OrderViewModel
import com.example.delivaroos.navigation.NavScreen
import com.example.delivaroos.ui.components.ScreenLayout
import androidx.navigation.compose.rememberNavController
import com.example.delivaroos.ui.preview.PreviewData
import androidx.compose.ui.tooling.preview.Preview
import com.example.delivaroos.ui.theme.DelivaroosTheme
import com.example.delivaroos.ui.preview.PreviewCartViewModel
import com.example.delivaroos.ui.preview.PreviewOrderViewModel
import androidx.compose.ui.platform.LocalContext
import android.app.Application

@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    orderViewModel: OrderViewModel = viewModel()
) {
    ScreenLayout(
        title = "Cart"
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val cartItems by cartViewModel.cartItems.observeAsState(emptyList())
            val totalAmount by cartViewModel.totalAmount.observeAsState(0.0)
            val orderPlaced by cartViewModel.orderPlaced.observeAsState()

            LaunchedEffect(orderPlaced) {
                if (orderPlaced != null) {
                    if (orderPlaced?.startsWith("Order placed") == true) {
                        navController.navigate(NavScreen.OrderConfirmation.route) {
                            popUpTo(NavScreen.Cart.route) { inclusive = true }
                        }
                    }
                    cartViewModel.clearOrderPlaced()
                }
            }

            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Your cart is empty")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(cartItems) { item ->
                        CartItemCard(
                            cartItem = item,
                            onIncrement = { cartViewModel.addToCart(item.food) },
                            onDecrement = { cartViewModel.removeFromCart(item.food) }
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total",
                                style = MaterialTheme.typography.h6
                            )
                            Text(
                                text = "£${String.format("%.2f", totalAmount)}",
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = { cartViewModel.placeOrder(orderViewModel) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            enabled = cartItems.isNotEmpty()
                        ) {
                            Text("Place Order")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = cartItem.food.imageUrl,
                contentDescription = cartItem.food.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = cartItem.food.name,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "£${String.format("%.2f", cartItem.food.price)}",
                    style = MaterialTheme.typography.body1
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDecrement) {
                    Icon(Icons.Default.Remove, "Decrease quantity")
                }
                Text(
                    text = cartItem.quantity.toString(),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                IconButton(onClick = onIncrement) {
                    Icon(Icons.Default.Add, "Increase quantity")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    DelivaroosTheme {
        CartScreen(
            navController = rememberNavController(),
            cartViewModel = PreviewCartViewModel(),
            orderViewModel = PreviewOrderViewModel()
        )
    }
}

private fun previewCartViewModel(): CartViewModel {
    return object : CartViewModel() {
        init {
            _cartItems.value = listOf(
                PreviewData.sampleCartItem,
                PreviewData.sampleCartItem.copy(
                    food = PreviewData.sampleFood.copy(id = "2", name = "Veggie Burger")
                )
            )
        }
    }
} 