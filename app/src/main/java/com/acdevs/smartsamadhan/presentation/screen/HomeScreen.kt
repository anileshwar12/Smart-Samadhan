package com.acdevs.smartsamadhan.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acdevs.smartsamadhan.presentation.component.BottomNavItem
import com.acdevs.smartsamadhan.presentation.component.ReportItem


// Dashboard Screen
@Composable
fun HomeScreen(onReportIssue: () -> Unit) {
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
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Ranchi",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFFB923C), CircleShape)
                )
            }

            // Main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 80.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Hello Matthew!",
                    color = Color(0xFF9CA3AF),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Take care of the roads",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Current issue card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1F2937)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("❄️", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Ice on the road",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }

                            Surface(
                                color = Color(0xFFDC2626),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "High",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Issue number: #601375",
                            color = Color(0xFF9CA3AF),
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Progress indicators
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Pending", color = Color(0xFF9CA3AF), fontSize = 10.sp)
                            Text("In progress", color = Color(0xFF9CA3AF), fontSize = 10.sp)
                            Text("Completed", color = Color(0xFF9CA3AF), fontSize = 10.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Progress bar
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF8B5CF6), CircleShape)
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(4.dp)
                                    .background(Color(0xFF374151), RoundedCornerShape(2.dp))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.33f)
                                        .fillMaxHeight()
                                        .background(Color(0xFF8B5CF6), RoundedCornerShape(2.dp))
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF8B5CF6), CircleShape)
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(4.dp)
                                    .background(Color(0xFF374151), RoundedCornerShape(2.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF4B5563), CircleShape)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("08:21 AM", color = Color(0xFF9CA3AF), fontSize = 10.sp)
                            Text("08:37 AM", color = Color(0xFF9CA3AF), fontSize = 10.sp)
                            Text("", fontSize = 10.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Reports from others
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reports from others",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Show more",
                        color = Color(0xFF8B5CF6),
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Report items
                ReportItem(
                    color = Color(0xFFFB923C),
                    title = "Hole in the road",
                    emoji = "🕳️",
                    location = "St. Herbowa, Swoszowice",
                    distance = "0.3km"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ReportItem(
                    color = Color(0xFF60A5FA),
                    title = "Ice on the road",
                    emoji = "❄️",
                    location = "St. Myślenicka, Swoszowice",
                    distance = "0.6km"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ReportItem(
                    color = Color(0xFFFBBF24),
                    title = "Damaged sidewalk",
                    emoji = "⚠️",
                    location = "St. Sliczna, Pradnik Czerwony",
                    distance = "1.5km"
                )
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = onReportIssue,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-96).dp),
            containerColor = Color(0xFF8B5CF6),
            contentColor = Color.White
        ) {
            Text("📋", fontSize = 24.sp)
        }

        // Bottom Navigation
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color(0xFF1F2937)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomNavItem(
                    icon = Icons.Default.Home,
                    label = "Home",
                    isSelected = true
                )
                BottomNavItem(
                    icon = Icons.Default.Email,
                    label = "My reports",
                    isSelected = false
                )
                BottomNavItem(
                    icon = Icons.Default.CheckCircle,
                    label = "Map",
                    isSelected = false
                )
                BottomNavItem(
                    icon = Icons.Default.Settings,
                    label = "Settings",
                    isSelected = false
                )
            }
        }

        // Home indicator
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-4).dp)
                .width(128.dp)
                .height(4.dp)
                .background(Color.White, RoundedCornerShape(2.dp))
        )
    }
}

@Preview
@Composable
fun PreviewDashboardScreen() {
    HomeScreen(onReportIssue = {})
}