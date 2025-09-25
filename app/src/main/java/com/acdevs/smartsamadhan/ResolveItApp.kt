package com.acdevs.smartsamadhan


import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acdevs.smartsamadhan.presentation.screen.CameraScreen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay

// Custom Icons using Unicode/Text
object CustomIcons {
    const val FLASH_ON = "🔦"
    const val FLASH_OFF = "💡"
    const val GALLERY = "📷"
    const val SWITCH_CAMERA = "🔄"
    const val LOCATION = "📍"
    const val PHONE = "📞"
    const val LANGUAGE = "🌐"
    const val REPORT = "📋"
    const val DRAINAGE = "🌊"
    const val GARBAGE = "🗑️"
    const val WATER = "🚰"
    const val ELECTRICITY = "⚡"
    const val CONSTRUCTION = "🚧"
    const val QUESTION = "❓"
    const val POTHOLE = "🕳️"
    const val LIGHT = "💡"
}

// Data classes
data class District(val name: String, val code: String)
data class IssueCategory(
    val id: String,
    val nameHi: String,
    val nameEn: String,
    val emoji: String,
    val color: Color,
    val borderColor: Color,
    val textColor: Color
)

data class ReportIssue(
    val id: String,
    val titleHi: String,
    val titleEn: String,
    val emoji: String,
    val location: String,
    val distance: String,
    val timeHi: String,
    val timeEn: String,
    val priority: String,
    val color: Color
)

// Main App
@Composable
fun ResolveItApp() {
    var currentScreen by remember { mutableStateOf("home") }
    var selectedDistrict by remember { mutableStateOf("Ranchi") }
    var capturedPhoto by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<IssueCategory?>(null) }
    var isHindi by remember { mutableStateOf(true) }
    var cameraFlash by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF1E293B)
                    )
                )
            )
    ) {
        when (currentScreen) {
            "home" -> HomeScreen(
                selectedDistrict = selectedDistrict,
                isHindi = isHindi,
                onDistrictChange = { selectedDistrict = it },
                onLanguageToggle = { isHindi = !isHindi },
                onReportIssue = {
                    capturedPhoto = false
                    selectedCategory = null
                    currentScreen = "camera"
                }
            )
            "camera" -> CameraScreen(
                isHindi = isHindi,
                onBack = { currentScreen = "home" },
                onCapture = { uri ->
                    capturedPhoto = true
                    currentScreen = "category-select"
                    // you can also save/use the Uri here if needed
                },
                onGalleryClick = {
                    // open gallery picker or handle navigation to gallery screen
                }
            )

            "category-select" -> CategorySelectScreen(
                isHindi = isHindi,
                onBack = { currentScreen = "camera" },
                onCategorySelected = { category ->
                    selectedCategory = category
                    currentScreen = "report-form"
                }
            )
            "report-form" -> ReportFormScreen(
                selectedCategory = selectedCategory,
                selectedDistrict = selectedDistrict,
                isHindi = isHindi,
                onBack = { currentScreen = "category-select" },
                onSubmit = {
                    showSuccessDialog = true
                }
            )
        }

        // Success Dialog
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccessDialog = false
                            currentScreen = "home"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) {
                        Text(if (isHindi) "ठीक है" else "OK")
                    }
                },
                title = {
                    Text(
                        text = if (isHindi) "✅ शिकायत सफलतापूर्वक दर्ज की गई!" else "✅ Report Submitted Successfully!",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = if (isHindi)
                            "आपकी शिकायत ID: JH${System.currentTimeMillis().toString().takeLast(6)}\n24 घंटे में कार्रवाई की जाएगी।"
                        else
                            "Your report ID: JH${System.currentTimeMillis().toString().takeLast(6)}\nAction will be taken within 24 hours."
                    )
                }
            )
        }
    }
}

// Enhanced Home Screen with Animations
@Composable
fun HomeScreen(
    selectedDistrict: String,
    isHindi: Boolean,
    onDistrictChange: (String) -> Unit,
    onLanguageToggle: () -> Unit,
    onReportIssue: () -> Unit
) {
    val jharkhandDistricts = listOf(
        "Ranchi", "Dhanbad", "Jamshedpur", "Bokaro", "Deoghar",
        "Hazaribagh", "Giridih", "Ramgarh", "Chaibasa", "Dumka",
        "Godda", "Gumla", "Kodarma", "Lohardaga", "Pakur",
        "Palamu", "Sahibganj", "Seraikela-Kharsawan", "Simdega",
        "Jamtara", "Khunti", "Latehar", "Chatra", "Garhwa"
    )

    val sampleReports = listOf(
        ReportIssue(
            "1", "सड़क में गड्ढा", "Road Pothole", CustomIcons.POTHOLE,
            "Main Road, Doranda, Ranchi", "0.8km", "2 घंटे पहले", "2 hours ago",
            "High", Color(0xFFF59E0B)
        ),
        ReportIssue(
            "2", "टूटी स्ट्रीट लाइट", "Broken Street Light", CustomIcons.LIGHT,
            "Circular Road, Ranchi", "1.2km", "4 घंटे पहले", "4 hours ago",
            "Medium", Color(0xFFEF4444)
        ),
        ReportIssue(
            "3", "अवरुद्ध नाला", "Blocked Drainage", CustomIcons.DRAINAGE,
            "HEC Colony, Ranchi", "2.1km", "6 घंटे पहले", "6 hours ago",
            "Low", Color(0xFF10B981)
        )
    )

    var showDistrictDropdown by remember { mutableStateOf(false) }
    var selectedReportIndex by remember { mutableStateOf(-1) }
    val fabScale by animateFloatAsState(
        targetValue = if (selectedReportIndex >= 0) 1.1f else 1f,
        animationSpec = spring(dampingRatio = 0.6f)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Enhanced Header with Language Toggle
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E293B).copy(alpha = 0.9f)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.                                clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { showDistrictDropdown = true }
                            ) {
                                Text(
                                    text = CustomIcons.LOCATION,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = selectedDistrict,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color(0xFF64748B),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = if (isHindi) "झारखंड, भारत" else "Jharkhand, India",
                                color = Color(0xFF64748B),
                                fontSize = 12.sp
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Language Toggle
                            Card(
                                modifier = Modifier
                                    .clickable { onLanguageToggle() }
                                    .padding(4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF3B82F6).copy(alpha = 0.2f)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = CustomIcons.LANGUAGE,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isHindi) "EN" else "हि",
                                        color = Color(0xFF3B82F6),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))
                                        ),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "🏛️",
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            // District Dropdown
            if (showDistrictDropdown) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 250.dp)
                                .padding(8.dp)
                        ) {
                            items(jharkhandDistricts) { district ->
                                Text(
                                    text = district,
                                    color = if (district == selectedDistrict) Color(0xFF3B82F6) else Color.White,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            onDistrictChange(district)
                                            showDistrictDropdown = false
                                        }
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Welcome Section with Animation
            if (!showDistrictDropdown) {
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = if (isHindi) "नमस्ते Priya! 🙏" else "Hello Priya! 👋",
                            color = Color(0xFF64748B),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isHindi) "झारखंड की सड़कों की देखभाल करें" else "Help maintain Jharkhand's roads",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        if (isHindi) {
                            Text(
                                text = "Help maintain Jharkhand's roads",
                                color = Color(0xFF64748B),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                // Current Issue Progress Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🕳️", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = if (isHindi) "सड़क में गड्ढा" else "Road Pothole",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        if (isHindi) {
                                            Text(
                                                text = "Road Pothole",
                                                color = Color(0xFF64748B),
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                }

                                Surface(
                                    color = Color(0xFFDC2626),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = if (isHindi) "उच्च" else "High",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Issue ID: #JH240921001",
                                color = Color(0xFF64748B),
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Enhanced Progress Indicator
                            ProgressIndicator(isHindi = isHindi)
                        }
                    }
                }

                // Reports Section Header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isHindi) "आस-पास की शिकायतें" else "Nearby Reports",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            if (isHindi) {
                                Text(
                                    text = "Nearby Reports",
                                    color = Color(0xFF64748B),
                                    fontSize = 12.sp
                                )
                            }
                        }
                        Text(
                            text = if (isHindi) "सभी देखें" else "View All",
                            color = Color(0xFF3B82F6),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { }
                        )
                    }
                }

                // Enhanced Report Items
                items(sampleReports) { report ->
                    val index = sampleReports.indexOf(report)
                    AnimatedReportItem(
                        report = report,
                        isHindi = isHindi,
                        isSelected = selectedReportIndex == index,
                        onClick = {
                            selectedReportIndex = if (selectedReportIndex == index) -1 else index
                        }
                    )
                }
            }
        }

        // Animated FAB
        FloatingActionButton(
            onClick = onReportIssue,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-110).dp)
                .scale(fabScale)
                .size(64.dp),
            containerColor = Color(0xFF3B82F6),
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = if (isHindi) "समस्या रिपोर्ट करें" else "Report Issue",
                modifier = Modifier.size(28.dp)
            )
        }

        // Enhanced Bottom Navigation
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E293B).copy(alpha = 0.95f)
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomNavItem(
                    icon = Icons.Default.Home,
                    label = if (isHindi) "होम" else "Home",
                    isSelected = true
                )
                BottomNavItem(
                    icon = Icons.Default.Check,
                    label = if (isHindi) "रिपोर्ट्स" else "Reports",
                    isSelected = false
                )
                BottomNavItem(
                    icon = Icons.Default.Check,
                    label = if (isHindi) "मैप" else "Map",
                    isSelected = false
                )
                BottomNavItem(
                    icon = Icons.Default.Settings,
                    label = if (isHindi) "सेटिंग्स" else "Settings",
                    isSelected = false
                )
            }
        }
    }
}




// Enhanced Category Selection Screen
@Composable
fun CategorySelectScreen(
    isHindi: Boolean,
    onBack: () -> Unit,
    onCategorySelected: (IssueCategory) -> Unit
) {
    val jharkhandIssueCategories = listOf(
        IssueCategory(
            "pothole", "सड़क में गड्ढा", "Road Pothole", CustomIcons.POTHOLE,
            Color(0xFFFFF7ED), Color(0xFFFED7AA), Color(0xFFEA580C)
        ),
        IssueCategory(
            "streetlight", "टूटी स्ट्रीट लाइट", "Broken Street Light", CustomIcons.LIGHT,
            Color(0xFFFEFCE8), Color(0xFFFDE047), Color(0xFFA16207)
        ),
        IssueCategory(
            "drainage", "अवरुद्ध नाला", "Blocked Drainage", CustomIcons.DRAINAGE,
            Color(0xFFECFDF5), Color(0xFFA7F3D0), Color(0xFF059669)
        ),
        IssueCategory(
            "garbage", "कचरा संग्रह", "Garbage Collection", CustomIcons.GARBAGE,
            Color(0xFFF3F4F6), Color(0xFFD1D5DB), Color(0xFF6B7280)
        ),
        IssueCategory(
            "water_supply", "पानी की आपूर्ति", "Water Supply", CustomIcons.WATER,
            Color(0xFFEBF8FF), Color(0xFFBAE6FD), Color(0xFF0284C7)
        ),
        IssueCategory(
            "electricity", "बिजली की समस्या", "Electricity Issue", CustomIcons.ELECTRICITY,
            Color(0xFFFEF3C7), Color(0xFFFDE047), Color(0xFF92400E)
        ),
        IssueCategory(
            "road_damage", "सड़क की क्षति", "Road Damage", CustomIcons.CONSTRUCTION,
            Color(0xFFFEE2E2), Color(0xFFFCA5A5), Color(0xFFDC2626)
        ),
        IssueCategory(
            "other", "अन्य", "Other", CustomIcons.QUESTION,
            Color(0xFFF8FAFC), Color(0xFFCBD5E1), Color(0xFF475569)
        )
    )

    var selectedCategoryId by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F172A), Color(0xFF1E293B))
                )
            )
    ) {
        Column {
            // Enhanced Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .background(Color(0xFF1E293B), CircleShape)
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isHindi) "समस्या का प्रकार चुनें" else "Select Issue Category",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (isHindi) "Select Issue Category" else "समस्या का प्रकार चुनें",
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                }

                Box(modifier = Modifier.size(48.dp)) // Placeholder for symmetry
            }

            // Categories Grid with Animation
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(jharkhandIssueCategories.chunked(2)) { rowCategories ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowCategories.forEach { category ->
                            CategoryCard(
                                category = category,
                                isHindi = isHindi,
                                isSelected = selectedCategoryId == category.id,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    selectedCategoryId = category.id
                                    onCategorySelected(category)
                                }
                            )
                        }
                        if (rowCategories.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: IssueCategory,
    isHindi: Boolean,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(dampingRatio = 0.6f)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                category.color.copy(alpha = 0.3f)
            else
                category.color
        ),
        border = BorderStroke(
            2.dp,
            if (isSelected) category.textColor else category.borderColor
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = category.emoji,
                fontSize = if (isSelected) 36.sp else 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isHindi) category.nameHi else category.nameEn,
                color = category.textColor,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 15.sp
            )
        }
    }
}

// Enhanced Report Form Screen
@Composable
fun ReportFormScreen(
    selectedCategory: IssueCategory?,
    selectedDistrict: String,
    isHindi: Boolean,
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    var issueDescription by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf("Medium") }
    var contactNumber by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F172A), Color(0xFF1E293B))
                )
            )
    ) {
        Column {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .background(Color(0xFF1E293B), CircleShape)
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isHindi) "शिकायत दर्ज करें" else "File Report",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (isHindi) "File Report" else "शिकायत दर्ज करें",
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                }

                Box(modifier = Modifier.size(48.dp)) // Placeholder
            }

            // Scrollable Form Content
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Selected Category Display
                    selectedCategory?.let { category ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = category.color),
                            border = BorderStroke(2.dp, category.borderColor),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = category.emoji,
                                    fontSize = 28.sp
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(
                                        text = if (isHindi) category.nameHi else category.nameEn,
                                        color = category.textColor,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (isHindi) {
                                        Text(
                                            text = category.nameEn,
                                            color = category.textColor.copy(alpha = 0.7f),
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Issue Description
                    Column {
                        Text(
                            text = if (isHindi)
                                "समस्या का विवरण (Issue Description)"
                            else
                                "Issue Description (समस्या का विवरण)",
                            color = Color(0xFF111827),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = issueDescription,
                            onValueChange = { issueDescription = it },
                            placeholder = {
                                Text(
                                    text = if (isHindi)
                                        "समस्या के बारे में विस्तार से लिखें..."
                                    else
                                        "Describe the issue in detail...",
                                    color = Color(0xFF9CA3AF)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF3B82F6),
                                unfocusedBorderColor = Color(0xFFE5E7EB),
                                focusedLabelColor = Color(0xFF3B82F6)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            maxLines = 6
                        )
                    }

                    // Location Information
                    Column {
                        Text(
                            text = if (isHindi)
                                "स्थान की जानकारी (Location Info)"
                            else
                                "Location Information (स्थान की जानकारी)",
                            color = Color(0xFF111827),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                            border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(Color(0xFFEBF8FF), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = CustomIcons.LOCATION,
                                            fontSize = 18.sp
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = "Main Road, Doranda",
                                            color = Color(0xFF111827),
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "$selectedDistrict, Jharkhand",
                                            color = Color(0xFF6B7280),
                                            fontSize = 12.sp
                                        )
                                        Text(
                                            text = "23.3569° N, 85.3240° E",
                                            color = Color(0xFF9CA3AF),
                                            fontSize = 10.sp
                                        )
                                    }
                                }

                                Text(
                                    text = if (isHindi) "बदलें" else "Change",
                                    color = Color(0xFF3B82F6),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.clickable { }
                                )
                            }
                        }
                    }

                    // Priority Selection
                    Column {
                        Text(
                            text = if (isHindi)
                                "प्राथमिकता (Priority Level)"
                            else
                                "Priority Level (प्राथमिकता)",
                            color = Color(0xFF111827),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            PriorityButton(
                                text = if (isHindi) "कम (Low)" else "Low (कम)",
                                isSelected = selectedPriority == "Low",
                                selectedColor = Color(0xFF10B981),
                                modifier = Modifier.weight(1f)
                            ) { selectedPriority = "Low" }

                            PriorityButton(
                                text = if (isHindi) "मध्यम (Medium)" else "Medium (मध्यम)",
                                isSelected = selectedPriority == "Medium",
                                selectedColor = Color(0xFFF59E0B),
                                modifier = Modifier.weight(1f)
                            ) { selectedPriority = "Medium" }

                            PriorityButton(
                                text = if (isHindi) "उच्च (High)" else "High (उच्च)",
                                isSelected = selectedPriority == "High",
                                selectedColor = Color(0xFFEF4444),
                                modifier = Modifier.weight(1f)
                            ) { selectedPriority = "High" }
                        }
                    }

                    // Contact Number
                    Column {
                        Text(
                            text = if (isHindi)
                                "संपर्क नंबर (Contact Number)"
                            else
                                "Contact Number (संपर्क नंबर)",
                            color = Color(0xFF111827),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = contactNumber,
                            onValueChange = { contactNumber = it },
                            placeholder = {
                                Text(
                                    text = if (isHindi)
                                        "अपना मोबाइल नंबर दर्ज करें"
                                    else
                                        "Enter your mobile number",
                                    color = Color(0xFF9CA3AF)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF3B82F6),
                                unfocusedBorderColor = Color(0xFFE5E7EB)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text(
                                        text = CustomIcons.PHONE,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "+91",
                                        color = Color(0xFF6B7280),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        )
                    }

                    // Submit Button
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onSubmit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF3B82F6)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = if (isHindi)
                                        "शिकायत दर्ज करें (Submit Report)"
                                    else
                                        "Submit Report (शिकायत दर्ज करें)",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF0F9FF)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (isHindi)
                                    "ℹ️ आपकी शिकायत 24 घंटे में देखी जाएगी।\nYour report will be reviewed within 24 hours."
                                else
                                    "ℹ️ Your report will be reviewed within 24 hours.\nआपकी शिकायत 24 घंटे में देखी जाएगी।",
                                color = Color(0xFF1E40AF),
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// Helper Components with Animations
@Composable
fun ProgressIndicator(isHindi: Boolean) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                if (isHindi) "शिकायत" else "Reported",
                color = Color(0xFF64748B),
                fontSize = 10.sp
            )
            Text(
                if (isHindi) "प्रगति" else "Progress",
                color = Color(0xFF64748B),
                fontSize = 10.sp
            )
            Text(
                if (isHindi) "पूर्ण" else "Complete",
                color = Color(0xFF64748B),
                fontSize = 10.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val progress by animateFloatAsState(
            targetValue = 0.6f,
            animationSpec = tween(2000, easing = EaseInOutCubic)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(Color(0xFF3B82F6), CircleShape)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .background(Color(0xFF334155), RoundedCornerShape(3.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))
                            ),
                            RoundedCornerShape(3.dp)
                        )
                )
            }
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        if (progress > 0.5f) Color(0xFF3B82F6) else Color(0xFF475569),
                        CircleShape
                    )
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .background(Color(0xFF334155), RoundedCornerShape(3.dp))
            )
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(Color(0xFF475569), CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("09:15 AM", color = Color(0xFF64748B), fontSize = 10.sp)
            Text("02:30 PM", color = Color(0xFF64748B), fontSize = 10.sp)
            Text("", fontSize = 10.sp)
        }
    }
}

@Composable
fun AnimatedReportItem(
    report: ReportIssue,
    isHindi: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(dampingRatio = 0.6f)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .padding(horizontal = 16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                Color(0xFF1E293B).copy(alpha = 0.9f)
            else
                Color(0xFF1E293B)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        ),
        border = if (isSelected) BorderStroke(2.dp, Color(0xFF3B82F6)) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(report.color.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = report.emoji,
                        fontSize = 22.sp
                    )
                }

                Column {
                    Text(
                        text = if (isHindi) report.titleHi else report.titleEn,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    if (isHindi) {
                        Text(
                            text = report.titleEn,
                            color = Color(0xFF64748B),
                            fontSize = 11.sp
                        )
                    }
                    Text(
                        text = report.location,
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                    Text(
                        text = if (isHindi) report.timeHi else report.timeEn,
                        color = Color(0xFF64748B),
                        fontSize = 10.sp
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Surface(
                    color = report.color,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = report.distance,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = spring(dampingRatio = 0.6f)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.scale(scale)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFF3B82F6) else Color(0xFF64748B),
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = label,
            color = if (isSelected) Color(0xFF3B82F6) else Color(0xFF64748B),
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun PriorityButton(
    text: String,
    isSelected: Boolean,
    selectedColor: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(dampingRatio = 0.6f)
    )

    Card(
        modifier = modifier
            .height(56.dp)
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                selectedColor.copy(alpha = 0.15f)
            else
                Color.Transparent
        ),
        border = BorderStroke(
            2.dp,
            if (isSelected) selectedColor else Color(0xFFE5E7EB)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (isSelected) selectedColor else Color(0xFF6B7280),
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}



@Composable
fun InteractiveStatsCard(
    title: String,
    value: String,
    icon: String,
    color: Color,
    isHindi: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.6f)
    )

    Card(
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isPressed = !isPressed
            },
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                color = Color(0xFF6B7280),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Enhanced notification system for better user engagement
@Composable
fun NotificationBadge(count: Int) {
    if (count > 0) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color(0xFFEF4444), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (count > 99) "99+" else count.toString(),
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Add haptic feedback simulation for better UX
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun HapticButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFF3B82F6),
    contentColor: Color = Color.White
) {
    var isPressed by remember { mutableStateOf(false) }

    Button(
        onClick = {
            isPressed = true
            onClick()
            // Simulate haptic feedback delay
            GlobalScope.launch {
                delay(100)
                isPressed = false
            }
        },
        modifier = modifier.scale(if (isPressed) 0.98f else 1f),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            color = contentColor,
            fontWeight = FontWeight.Medium
        )
    }
}

