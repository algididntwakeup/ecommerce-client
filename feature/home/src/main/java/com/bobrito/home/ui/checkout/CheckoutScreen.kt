package com.bobrito.home.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobrito.ui.components.BobImageViewPhotoUrlRounded

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    onComplete: () -> Unit
) {
    var currentStep by remember { mutableStateOf(0) }
    val steps = listOf("Delivery", "Address", "Payment")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar
        TopAppBar(
            title = { Text(if (currentStep == 3) "Summary" else "Checkout") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        // Progress Indicator
        if (currentStep < 3) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                steps.forEachIndexed { index, step ->
                    StepIndicator(
                        step = step,
                        isCompleted = index < currentStep,
                        isActive = index == currentStep,
                        isLast = index == steps.size - 1
                    )
                }
            }
        }

        // Content based on current step
        when (currentStep) {
            0 -> DeliveryStep(
                onNext = { currentStep++ }
            )
            1 -> AddressStep(
                onBack = { currentStep-- },
                onNext = { currentStep++ }
            )
            2 -> PaymentStep(
                onBack = { currentStep-- },
                onNext = { currentStep++ }
            )
            3 -> OrderSummaryStep(
                onBack = { currentStep-- },
                onComplete = onComplete
            )
        }
    }
}

@Composable
private fun StepIndicator(
    step: String,
    isCompleted: Boolean,
    isActive: Boolean,
    isLast: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circle indicator
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isCompleted || isActive -> Color(0xFFFF3D00)
                            else -> Color.LightGray
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Connecting line
            if (!isLast) {
                Divider(
                    modifier = Modifier
                        .width(60.dp)
                        .padding(horizontal = 8.dp),
                    color = if (isCompleted) Color(0xFFFF3D00) else Color.LightGray
                )
            }
        }
        
        Text(
            text = step,
            fontSize = 12.sp,
            color = if (isActive) Color(0xFFFF3D00) else Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun DeliveryStep(onNext: () -> Unit) {
    var selectedDelivery by remember { mutableStateOf("regular") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Regular Delivery Option
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { selectedDelivery = "regular" },
            colors = CardDefaults.cardColors(
                containerColor = if (selectedDelivery == "regular") Color(0xFFFFF3E0) else Color.White
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedDelivery == "regular",
                    onClick = { selectedDelivery = "regular" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFFFF3D00)
                    )
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = "Regular Delivery",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Order will be delivered between 3 - 5 business days",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        // Express Delivery Option
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { selectedDelivery = "express" },
            colors = CardDefaults.cardColors(
                containerColor = if (selectedDelivery == "express") Color(0xFFFFF3E0) else Color.White
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedDelivery == "express",
                    onClick = { selectedDelivery = "express" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFFFF3D00)
                    )
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = "Express Delivery",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Place your order before 6 pm and your items will be delivered",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3D00))
        ) {
            Text("Next")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddressStep(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    var street1 by remember { mutableStateOf("street armstrong 15") }
    var street2 by remember { mutableStateOf("street 15") }
    var country by remember { mutableStateOf("Indonesia") }
    var state by remember { mutableStateOf("Jawa Barat") }
    var city by remember { mutableStateOf("Bandung") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = street1,
            onValueChange = { street1 = it },
            label = { Text("Street 1") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = street2,
            onValueChange = { street2 = it },
            label = { Text("Street 2") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Country") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = state,
                onValueChange = { state = it },
                label = { Text("States") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text("BACK", color = Color.Black)
            }

            Button(
                onClick = onNext,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3D00))
            ) {
                Text("NEXT")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentStep(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    var cardName by remember { mutableStateOf("street armstrong 15") }
    var cardNumber by remember { mutableStateOf("9586 9594 4943 4595") }
    var expiryDate by remember { mutableStateOf("09/18") }
    var cvv by remember { mutableStateOf("****") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = cardName,
            onValueChange = { cardName = it },
            label = { Text("Name on Card") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Card Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color(0xFFFF3D00)
                )
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("Expiry Date") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("CVV") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text("BACK", color = Color.Black)
            }

            Button(
                onClick = onNext,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3D00))
            ) {
                Text("NEXT")
            }
        }
    }
}

@Composable
private fun OrderSummaryStep(
    onBack: () -> Unit,
    onComplete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Order List Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Order List",
                        fontWeight = FontWeight.Bold
                    )
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                }

                // Order Items
                OrderItem(
                    name = "NMD_R1 SHOES",
                    price = "Rp250.000",
                    imageUrl = "https://images.stockx.com/images/Nike-Air-Zoom-Elite-8-Turquoise-Jade-Volt.png"
                )
                OrderItem(
                    name = "NMD_R1 SHOES",
                    price = "Rp250.000",
                    imageUrl = "https://images.stockx.com/images/Nike-Air-Zoom-Elite-8-Turquoise-Jade-Volt.png"
                )
            }
        }

        // Shipping Address
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Shipping Address",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    "Jl. Geusan Ulun No.11, Citarum, Kec. Bandung Wetan, Kota Bandung, Jawa Barat 40115",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        // Payment Method
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Payment",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFFFF3D00),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        "Master Card",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    "**** **** **** 4595",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text("BACK", color = Color.Black)
            }

            Button(
                onClick = onComplete,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3D00))
            ) {
                Text("PAY")
            }
        }
    }
}

@Composable
private fun OrderItem(
    name: String,
    price: String,
    imageUrl: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BobImageViewPhotoUrlRounded(
            url = imageUrl,
            description = "Product Image",
            modifier = Modifier.size(60.dp)
        )
        
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = price,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}