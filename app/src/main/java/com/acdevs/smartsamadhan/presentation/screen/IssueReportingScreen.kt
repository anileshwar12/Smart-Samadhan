package com.acdevs.smartsamadhan.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// Report Form Screen
@Composable
fun ReportFormScreen(onBack: () -> Unit, onSubmit: () -> Unit) {
    var selectedCategory by remember { mutableStateOf("Hole in the road") }
    var selectedPriority by remember { mutableStateOf("Medium") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111827))
    ) {
        Column {

            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Report an issue",
                    color = Color.White,
                    fontSize = 14.sp
                )

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Flag",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Photo preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color(0xFF374151), RoundedCornerShape(8.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF9CA3AF),
                                    Color(0xFF4B5563)
                                )
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    // Simplified road
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .background(Color(0xFF374151))
                            .align(Alignment.Center)
                    ) {
                        // Road line
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.White)
                                .align(Alignment.Center)
                        )

                        // Pothole
                        Box(
                            modifier = Modifier
                                .size(32.dp, 24.dp)
                                .background(Color(0xFF111827), RoundedCornerShape(50))
                                .align(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form content
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Issue category
                    item {
                        Column {
                            Text(
                                text = "Issue category",
                                color = Color(0xFF111827),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CategoryButton(
                                    emoji = "❄️",
                                    label = "Ice on the road",
                                    isSelected = selectedCategory == "Ice on the road",
                                    selectedColor = Color(0xFFEBF8FF),
                                    selectedBorderColor = Color(0xFFBFDBFE),
                                    selectedTextColor = Color(0xFF1D4ED8),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    selectedCategory = "Ice on the road"
                                }

                                CategoryButton(
                                    emoji = "🕳️",
                                    label = "Hole in the road",
                                    isSelected = selectedCategory == "Hole in the road",
                                    selectedColor = Color(0xFFFFF7ED),
                                    selectedBorderColor = Color(0xFFFEE2B5),
                                    selectedTextColor = Color(0xFFB45309),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    selectedCategory = "Hole in the road"
                                }

                                CategoryButton(
                                    emoji = "⚠️",
                                    label = "Other",
                                    isSelected = selectedCategory == "Other",
                                    selectedColor = Color(0xFFFEFCE8),
                                    selectedBorderColor = Color(0xFFFDE68A),
                                    selectedTextColor = Color(0xFFA16207),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    selectedCategory = "Other"
                                }
                            }
                        }
                    }

                    // Issue location
                    item {
                        Column {
                            Text(
                                text = "Issue location",
                                color = Color(0xFF111827),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .background(Color(0xFFFFF7ED), CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.LocationOn,
                                                contentDescription = null,
                                                tint = Color(0xFFEA580C),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        Column {
                                            Text(
                                                text = "St. Herbowa, Swoszowice",
                                                color = Color(0xFF111827),
                                                fontSize = 14.sp
                                            )
                                            Text(
                                                text = "50.001634, 19.664382",
                                                color = Color(0xFF6B7280),
                                                fontSize = 12.sp
                                            )
                                        }
                                    }

                                    Text(
                                        text = "Change",
                                        color = Color(0xFF8B5CF6),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }

                    // Issue priority
                    item {
                        Column {
                            Text(
                                text = "Issue priority",
                                color = Color(0xFF111827),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                PriorityButton(
                                    text = "Low",
                                    isSelected = selectedPriority == "Low",
                                    selectedColor = Color(0xFF10B981),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    selectedPriority = "Low"
                                }

                                PriorityButton(
                                    text = "Medium",
                                    isSelected = selectedPriority == "Medium",
                                    selectedColor = Color(0xFFFBBF24),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    selectedPriority = "Medium"
                                }

                                PriorityButton(
                                    text = "High",
                                    isSelected = selectedPriority == "High",
                                    selectedColor = Color(0xFFEF4444),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    selectedPriority = "High"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryButton(
    emoji: String,
    label: String,
    isSelected: Boolean,
    selectedColor: Color,
    selectedBorderColor: Color,
    selectedTextColor: Color,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    TODO("Not yet implemented")
}

@Composable
fun PriorityButton(
    text: String,
    isSelected: Boolean,
    selectedColor: Color,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    TODO("Not yet implemented")
}

