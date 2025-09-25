//package com.acdevs.resolveit.presentation.component
//
//import androidx.compose.animation.core.EaseInOutCubic
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.spring
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Send
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.acdevs.resolveit.CustomIcons
//import com.acdevs.resolveit.IssueCategory
//import com.acdevs.resolveit.ReportIssue
//
//
//@Composable
//fun UserIssueCard(issue: UserIssue, isHindi: Boolean) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { },
//        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.Top
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .size(48.dp)
//                            .background(issue.statusColor.copy(alpha = 0.2f), CircleShape),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = issue.emoji,
//                            fontSize = 20.sp
//                        )
//                    }
//
//                    Column {
//                        Text(
//                            text = if (isHindi) issue.titleHi else issue.title,
//                            color = Color.White,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Medium
//                        )
//                        Text(
//                            text = "ID: ${issue.id}",
//                            color = Color(0xFF64748B),
//                            fontSize = 12.sp
//                        )
//                    }
//                }
//
//                Surface(
//                    color = issue.statusColor,
//                    shape = RoundedCornerShape(12.dp)
//                ) {
//                    Text(
//                        text = issue.status,
//                        color = Color.White,
//                        fontSize = 11.sp,
//                        fontWeight = FontWeight.Medium,
//                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column {
//                    Text(
//                        text = if (isHindi) "स्थान:" else "Location:",
//                        color = Color(0xFF64748B),
//                        fontSize = 11.sp
//                    )
//                    Text(
//                        text = issue.location,
//                        color = Color.White,
//                        fontSize = 12.sp
//                    )
//                }
//
//                Column(horizontalAlignment = Alignment.End) {
//                    Text(
//                        text = if (isHindi) "दिनांक:" else "Date:",
//                        color = Color(0xFF64748B),
//                        fontSize = 11.sp
//                    )
//                    Text(
//                        text = issue.dateReported,
//                        color = Color.White,
//                        fontSize = 12.sp
//                    )
//                }
//            }
//
//            if (issue.description.isNotEmpty()) {
//                Spacer(modifier = Modifier.height(12.dp))
//                Text(
//                    text = issue.description,
//                    color = Color(0xFF9CA3AF),
//                    fontSize = 13.sp,
//                    lineHeight = 16.sp
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun SettingCard(
//    title: String,
//    subtitle: String,
//    icon: String,
//    value: String,
//    onClick: () -> Unit,
//    isDestructive: Boolean = false
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() },
//        colors = CardDefaults.cardColors(
//            containerColor = if (isDestructive) Color(0xFFDC2626).copy(alpha = 0.1f) else Color(0xFF1E293B)
//        ),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(
//                            if (isDestructive) Color(0xFFDC2626).copy(alpha = 0.2f) else Color(0xFF3B82F6).copy(alpha = 0.2f),
//                            CircleShape
//                        ),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = icon,
//                        fontSize = 18.sp
//                    )
//                }
//
//                Column {
//                    Text(
//                        text = title,
//                        color = if (isDestructive) Color(0xFFDC2626) else Color.White,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                    Text(
//                        text = subtitle,
//                        color = Color(0xFF64748B),
//                        fontSize = 12.sp
//                    )
//                }
//            }
//
//            if (value.isNotEmpty()) {
//                Text(
//                    text = value,
//                    color = if (isDestructive) Color(0xFFDC2626) else Color(0xFF3B82F6),
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Medium
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun LocationCard(selectedDistrict: String, isHindi: Boolean) {
//    Column {
//        Text(
//            text = if (isHindi) "स्थान की जानकारी" else "Location Information",
//            color = Color(0xFF111827),
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Medium
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
//            border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .size(40.dp)
//                            .background(Color(0xFFEBF8FF), CircleShape),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = CustomIcons.LOCATION,
//                            fontSize = 18.sp
//                        )
//                    }
//
//                    Column {
//                        Text(
//                            text = "Main Road, Doranda",
//                            color = Color(0xFF111827),
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Medium
//                        )
//                        Text(
//                            text = "$selectedDistrict, Jharkhand",
//                            color = Color(0xFF6B7280),
//                            fontSize = 12.sp
//                        )
//                        Text(
//                            text = "23.3569° N, 85.3240° E",
//                            color = Color(0xFF9CA3AF),
//                            fontSize = 10.sp
//                        )
//                    }
//                }
//
//                Text(
//                    text = if (isHindi) "बदलें" else "Change",
//                    color = Color(0xFF3B82F6),
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Medium,
//                    modifier = Modifier.clickable { }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun PrioritySelection(
//    selectedPriority: String,
//    isHindi: Boolean,
//    onPriorityChange: (String) -> Unit
//) {
//    Column {
//        Text(
//            text = if (isHindi) "प्राथमिकता" else "Priority Level",
//            color = Color(0xFF111827),
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Medium
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            PriorityButton(
//                text = if (isHindi) "कम" else "Low",
//                isSelected = selectedPriority == "Low",
//                selectedColor = Color(0xFF10B981),
//                modifier = Modifier.weight(1f)
//            ) { onPriorityChange("Low") }
//
//            PriorityButton(
//                text = if (isHindi) "मध्यम" else "Medium",
//                isSelected = selectedPriority == "Medium",
//                selectedColor = Color(0xFFF59E0B),
//                modifier = Modifier.weight(1f)
//            ) { onPriorityChange("Medium") }
//
//            PriorityButton(
//                text = if (isHindi) "उच्च" else "High",
//                isSelected = selectedPriority == "High",
//                selectedColor = Color(0xFFEF4444),
//                modifier = Modifier.weight(1f)
//            ) { onPriorityChange("High") }
//        }
//    }
//}
//
//@Composable
//fun ContactNumberField(
//    contactNumber: String,
//    isHindi: Boolean,
//    onContactChange: (String) -> Unit
//) {
//    Column {
//        Text(
//            text = if (isHindi) "संपर्क नंबर" else "Contact Number",
//            color = Color(0xFF111827),
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Medium
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        OutlinedTextField(
//            value = contactNumber,
//            onValueChange = onContactChange,
//            placeholder = {
//                Text(
//                    text = if (isHindi)
//                        "अपना मोबाइल नंबर दर्ज करें"
//                    else
//                        "Enter your mobile number",
//                    color = Color(0xFF9CA3AF)
//                )
//            },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = Color(0xFF3B82F6),
//                unfocusedBorderColor = Color(0xFFE5E7EB)
//            ),
//            shape = RoundedCornerShape(12.dp),
//            leadingIcon = {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.padding(start = 8.dp)
//                ) {
//                    Text(
//                        text = CustomIcons.PHONE,
//                        fontSize = 16.sp
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = "+91",
//                        color = Color(0xFF6B7280),
//                        fontSize = 14.sp
//                    )
//                }
//            }
//        )
//    }
//}
//
//@Composable
//fun SubmitButton(
//    isHindi: Boolean,
//    enabled: Boolean,
//    onSubmit: () -> Unit
//) {
//    Column {
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = onSubmit,
//            enabled = enabled,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = if (enabled) Color(0xFF3B82F6) else Color(0xFF9CA3AF)
//            ),
//            shape = RoundedCornerShape(16.dp),
//            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Send,
//                    contentDescription = null,
//                    tint = Color.White,
//                    modifier = Modifier.size(20.dp)
//                )
//                Text(
//                    text = if (isHindi)
//                        "शिकायत दर्ज करें"
//                    else
//                        "Submit Report",
//                    color = Color.White,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            colors = CardDefaults.cardColors(
//                containerColor = Color(0xFFF0F9FF)
//            ),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Text(
//                text = if (isHindi)
//                    "ℹ️ आपकी शिकायत 24 घंटे में देखी जाएगी।"
//                else
//                    "ℹ️ Your report will be reviewed within 24 hours.",
//                color = Color(0xFF1E40AF),
//                fontSize = 13.sp,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                lineHeight = 18.sp
//            )
//        }
//    }
//}
//
//// Remaining helper components
//@Composable
//fun ProgressIndicator(isHindi: Boolean) {
//    Column {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                if (isHindi) "शिकायत" else "Reported",
//                color = Color(0xFF64748B),
//                fontSize = 10.sp
//            )
//            Text(
//                if (isHindi) "प्रगति" else "Progress",
//                color = Color(0xFF64748B),
//                fontSize = 10.sp
//            )
//            Text(
//                if (isHindi) "पूर्ण" else "Complete",
//                color = Color(0xFF64748B),
//                fontSize = 10.sp
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        val progress by animateFloatAsState(
//            targetValue = 0.6f,
//            animationSpec = tween(2000, easing = EaseInOutCubic)
//        )
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(12.dp)
//                    .background(Color(0xFF3B82F6), CircleShape)
//            )
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .height(6.dp)
//                    .background(Color(0xFF334155), RoundedCornerShape(3.dp))
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth(progress)
//                        .fillMaxHeight()
//                        .background(
//                            Brush.horizontalGradient(
//                                colors = listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))
//                            ),
//                            RoundedCornerShape(3.dp)
//                        )
//                )
//            }
//            Box(
//                modifier = Modifier
//                    .size(12.dp)
//                    .background(
//                        if (progress > 0.5f) Color(0xFF3B82F6) else Color(0xFF475569),
//                        CircleShape
//                    )
//            )
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .height(6.dp)
//                    .background(Color(0xFF334155), RoundedCornerShape(3.dp))
//            )
//            Box(
//                modifier = Modifier
//                    .size(12.dp)
//                    .background(Color(0xFF475569), CircleShape)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text("09:15 AM", color = Color(0xFF64748B), fontSize = 10.sp)
//            Text("02:30 PM", color = Color(0xFF64748B), fontSize = 10.sp)
//            Text("", fontSize = 10.sp)
//        }
//    }
//}
//
//@Composable
//fun AnimatedReportItem(
//    report: ReportIssue,
//    isHindi: Boolean,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    val scale by animateFloatAsState(
//        targetValue = if (isSelected) 1.02f else 1f,
//        animationSpec = spring(dampingRatio = 0.6f)
//    )
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .scale(scale)
//            .padding(horizontal = 16.dp)
//            .clickable { onClick() },
//        colors = CardDefaults.cardColors(
//            containerColor = if (isSelected)
//                Color(0xFF1E293B).copy(alpha = 0.9f)
//            else
//                Color(0xFF1E293B)
//        ),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = if (isSelected) 8.dp else 2.dp
//        ),
//        border = if (isSelected) BorderStroke(2.dp, Color(0xFF3B82F6)) else null
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .size(48.dp)
//                        .background(report.color.copy(alpha = 0.2f), CircleShape),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = report.emoji,
//                        fontSize = 22.sp
//                    )
//                }
//
//                Column {
//                    Text(
//                        text = if (isHindi) report.titleHi else report.titleEn,
//                        color = Color.White,
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                    if (isHindi) {
//                        Text(
//                            text = report.titleEn,
//                            color = Color(0xFF64748B),
//                            fontSize = 11.sp
//                        )
//                    }
//                    Text(
//                        text = report.location,
//                        color = Color(0xFF64748B),
//                        fontSize = 12.sp
//                    )
//                    Text(
//                        text = if (isHindi) report.timeHi else report.timeEn,
//                        color = Color(0xFF64748B),
//                        fontSize = 10.sp
//                    )
//                }
//            }
//
//            Column(horizontalAlignment = Alignment.End) {
//                Surface(
//                    color = report.color,
//                    shape = RoundedCornerShape(12.dp)
//                ) {
//                    Text(
//                        text = report.distance,
//                        color = Color.White,
//                        fontSize = 11.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun BottomNavItem(
//    icon: ImageVector,
//    label: String,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    val scale by animateFloatAsState(
//        targetValue = if (isSelected) 1.1f else 1f,
//        animationSpec = spring(dampingRatio = 0.6f)
//    )
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(6.dp),
//        modifier = Modifier
//            .scale(scale)
//            .clickable { onClick() }
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = label,
//            tint = if (isSelected) Color(0xFF3B82F6) else Color(0xFF64748B),
//            modifier = Modifier.size(22.dp)
//        )
//        Text(
//            text = label,
//            color = if (isSelected) Color(0xFF3B82F6) else Color(0xFF64748B),
//            fontSize = 10.sp,
//            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
//        )
//    }
//}
//
//@Composable
//fun CategoryCard(
//    category: IssueCategory,
//    isHindi: Boolean,
//    isSelected: Boolean,
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit
//) {
//    val scale by animateFloatAsState(
//        targetValue = if (isSelected) 1.05f else 1f,
//        animationSpec = spring(dampingRatio = 0.6f)
//    )
//
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(130.dp)
//            .scale(scale)
//            .clickable { onClick() },
//        colors = CardDefaults.cardColors(
//            containerColor = if (isSelected)
//                category.color.copy(alpha = 0.3f)
//            else
//                category.color
//        ),
//        border = BorderStroke(
//            2.dp,
//            if (isSelected) category.textColor else category.borderColor
//        ),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = if (isSelected) 8.dp else 2.dp
//        )
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = category.emoji,
//                fontSize = if (isSelected) 36.sp else 32.sp
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = if (isHindi) category.nameHi else category.nameEn,
//                color = category.textColor,
//                fontSize = 13.sp,
//                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
//                textAlign = TextAlign.Center,
//                lineHeight = 15.sp
//            )
//        }
//    }
//}
//
//@Composable
//fun PriorityButton(
//    text: String,
//    isSelected: Boolean,
//    selectedColor: Color,
//    modifier: Modifier,
//    onClick: () -> Unit
//) {
//    val scale by animateFloatAsState(
//        targetValue = if (isSelected) 1.05f else 1f,
//        animationSpec = spring(dampingRatio = 0.6f)
//    )
//
//    Card(
//        modifier = modifier
//            .height(56.dp)
//            .scale(scale)
//            .clickable { onClick() },
//        colors = CardDefaults.cardColors(
//            containerColor = if (isSelected)
//                selectedColor.copy(alpha = 0.15f)
//            else
//                Color.Transparent
//        ),
//        border = BorderStroke(
//            2.dp,
//            if (isSelected) selectedColor else Color(0xFFE5E7EB)
//        ),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = if (isSelected) 4.dp else 0.dp
//        )
//    ) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = text,
//                color = if (isSelected) selectedColor else Color(0xFF6B7280),
//                fontSize = 13.sp,
//                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}