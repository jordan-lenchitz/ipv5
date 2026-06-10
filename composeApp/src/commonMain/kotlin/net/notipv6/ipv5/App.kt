package net.notipv6.ipv5

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.animation.*

sealed class Screen {
    abstract val route: String
    abstract val label: String
    abstract val icon: @Composable () -> Unit

    object Dashboard : Screen() {
        override val route = "dashboard"
        override val label = "home"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Home, null) }
    }
    object Security : Screen() {
        override val route = "security"
        override val label = "mac"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Lock, null) }
    }
    object Predictor : Screen() {
        override val route = "predictor"
        override val label = "ports"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Refresh, null) }
    }
    object Ping : Screen() {
        override val route = "ping"
        override val label = "ping"
        override val icon: @Composable () -> Unit = { Icon(Icons.AutoMirrored.Filled.Send, null) }
    }
    object DNS : Screen() {
        override val route = "dns"
        override val label = "dns"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Search, null) }
    }
    object IPv7 : Screen() {
        override val route = "ipv7"
        override val label = "ipv7+"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Star, null) }
    }
    object More : Screen() {
        override val route = "more"
        override val label = "more (36)"
        override val icon: @Composable () -> Unit = { Icon(Icons.AutoMirrored.Filled.List, null) }
    }
    object About : Screen() {
        override val route = "about"
        override val label = "about"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Info, null) }
    }
    object Settings : Screen() {
        override val route = "settings"
        override val label = "settings"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Settings, null) }
    }
    object Admin : Screen() {
        override val route = "admin"
        override val label = "admin panel"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Lock, null) }
    }
    object Dev : Screen() {
        override val route = "dev"
        override val label = "dev panel"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Build, null) }
    }
    object DevOps : Screen() {
        override val route = "devops"
        override val label = "devops panel"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Refresh, null) }
    }
    object FinOps : Screen() {
        override val route = "finops"
        override val label = "finops panel"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.ShoppingCart, null) }
    }
    object Splunk : Screen() {
        override val route = "splunk"
        override val label = "splunk panel"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Search, null) }
    }
    object Grafana : Screen() {
        override val route = "grafana"
        override val label = "grafana panel"
        override val icon: @Composable () -> Unit = { Icon(Icons.AutoMirrored.Filled.List, null) }
    }
    object Ansible : Screen() {
        override val route = "ansible"
        override val label = "ansible panel"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Check, null) }
    }
    object B2BSaaS : Screen() {
        override val route = "b2bsaas"
        override val label = "b2b saas panel"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Star, null) }
    }
    object Chaos : Screen() {
        override val route = "chaos"
        override val label = "chaos panel"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Warning, null) }
    }
    object Pizza : Screen() {
        override val route = "pizza"
        override val label = "pizza tracker"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.ShoppingCart, null) }
    }
    object HR : Screen() {
        override val route = "hr"
        override val label = "hr portal"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Person, null) }
    }
    object Lawyer : Screen() {
        override val route = "lawyer"
        override val label = "legal dept"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Info, null) }
    }
    object Marketing : Screen() {
        override val route = "marketing"
        override val label = "marketing"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Email, null) }
    }
    object Coffee : Screen() {
        override val route = "coffee"
        override val label = "coffee status"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Favorite, null) }
    }
    object Weather : Screen() {
        override val route = "weather"
        override val label = "mars weather"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.LocationOn, null) }
    }
    object Stock : Screen() {
        override val route = "stock"
        override val label = "stock market"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Notifications, null) }
    }
    object Astrology : Screen() {
        override val route = "astrology"
        override val label = "astrology routing"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Star, null) }
    }
    object History : Screen() {
        override val route = "history"
        override val label = "history"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.AccountBox, null) }
    }
    object Secret : Screen() {
        override val route = "secret"
        override val label = "secret panel"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Lock, null) }
    }
    object BugTracker : Screen() {
        override val route = "bugtracker"
        override val label = "bug tracker"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Warning, null) }
    }
    object Support : Screen() {
        override val route = "support"
        override val label = "support"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Call, null) }
    }
    object Compliance : Screen() {
        override val route = "compliance"
        override val label = "compliance"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Check, null) }
    }
    object Doc : Screen() {
        override val route = "doc"
        override val label = "documentation"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Menu, null) }
    }
    object Feedback : Screen() {
        override val route = "feedback"
        override val label = "feedback"
        override val icon: @Composable () -> Unit = { Icon(Icons.AutoMirrored.Filled.Send, null) }
    }
    object Sales : Screen() {
        override val route = "sales"
        override val label = "sales"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.ShoppingCart, null) }
    }
    object Infra : Screen() {
        override val route = "infra"
        override val label = "infrastructure"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Build, null) }
    }
    object Database : Screen() {
        override val route = "database"
        override val label = "database"
        override val icon: @Composable () -> Unit = { Icon(Icons.AutoMirrored.Filled.List, null) }
    }
    object ApiDocs : Screen() {
        override val route = "apidocs"
        override val label = "api docs"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.MoreVert, null) }
    }
    object Telemetry : Screen() {
        override val route = "telemetry"
        override val label = "telemetry"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Info, null) }
    }
    object Void : Screen() {
        override val route = "void"
        override val label = "the void"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Clear, null) }
    }
    object QuantumCat : Screen() {
        override val route = "quantumcat"
        override val label = "quantum cat"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Face, null) }
    }
    object WordSearch : Screen() {
        override val route = "wordsearch"
        override val label = "word search"
        override val icon: @Composable () -> Unit = { Icon(Icons.Default.Search, null) }
    }
}


@Composable
fun App() {
    val navController = rememberNavController()
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val isAccessible = GlobalAppState.accessibilityMode.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var isRightDrawerOpen by remember { mutableStateOf(false) }
    var selectedFeature by remember { mutableStateOf<InteractiveFeature?>(null) }
    var shuffledEsotericFeatures by remember { mutableStateOf(MoreFeaturesInteractive.list.shuffled()) }

    fun shuffleEsotericFeatures(keepFeature: InteractiveFeature? = null) {
        val currentList = shuffledEsotericFeatures
        if (keepFeature == null) {
            shuffledEsotericFeatures = MoreFeaturesInteractive.list.shuffled()
        } else {
            val keepIndex = currentList.indexOf(keepFeature)
            if (keepIndex == -1) {
                shuffledEsotericFeatures = MoreFeaturesInteractive.list.shuffled()
            } else {
                val otherFeatures = MoreFeaturesInteractive.list.filter { it != keepFeature }.shuffled().toMutableList()
                val newList = mutableListOf<InteractiveFeature>()
                for (i in 0 until 36) {
                    if (i == keepIndex) {
                        newList.add(keepFeature)
                    } else {
                        newList.add(otherFeatures.removeAt(0))
                    }
                }
                shuffledEsotericFeatures = newList
            }
        }
    }

    var firstDrawerOpenTrigger by remember { mutableStateOf(true) }

    LaunchedEffect(isRightDrawerOpen) {
        if (firstDrawerOpenTrigger) {
            firstDrawerOpenTrigger = false
            shuffleEsotericFeatures(null)
        } else {
            GlobalAppState.refreshColors()
            shuffleEsotericFeatures(null)
        }
    }

    var prevSelectedFeature by remember { mutableStateOf<InteractiveFeature?>(null) }

    LaunchedEffect(selectedFeature) {
        if (selectedFeature != prevSelectedFeature) {
            GlobalAppState.refreshColors()
            if (selectedFeature != null) {
                shuffleEsotericFeatures(selectedFeature)
            } else {
                shuffleEsotericFeatures(prevSelectedFeature)
            }
            prevSelectedFeature = selectedFeature
        }
    }


    val drawerScreens = remember {
        mutableStateOf(
            listOf(
                Screen.WordSearch, Screen.About, Screen.Dev,
                Screen.Settings, Screen.Admin, Screen.DevOps, Screen.FinOps, Screen.Splunk,
                Screen.Grafana, Screen.Ansible, Screen.B2BSaaS, Screen.Chaos, Screen.Pizza,
                Screen.HR, Screen.Lawyer, Screen.Marketing, Screen.Coffee,
                Screen.Weather, Screen.Stock, Screen.Astrology, Screen.History,
                Screen.Secret, Screen.BugTracker, Screen.Support, Screen.Compliance,
                Screen.Doc, Screen.Feedback, Screen.Sales, Screen.Infra,
                Screen.Database, Screen.ApiDocs, Screen.Telemetry,
                Screen.Void, Screen.QuantumCat
            )
        )
    }

    fun randomizeScreens() {
        val top3 = listOf(Screen.WordSearch, Screen.About, Screen.Dev)
        val bottom2 = listOf(Screen.Void, Screen.QuantumCat)
        val others = listOf(
            Screen.Settings, Screen.Admin, Screen.DevOps, Screen.FinOps, Screen.Splunk,
            Screen.Grafana, Screen.Ansible, Screen.B2BSaaS, Screen.Chaos, Screen.Pizza,
            Screen.HR, Screen.Lawyer, Screen.Marketing, Screen.Coffee,
            Screen.Weather, Screen.Stock, Screen.Astrology, Screen.History,
            Screen.Secret, Screen.BugTracker, Screen.Support, Screen.Compliance,
            Screen.Doc, Screen.Feedback, Screen.Sales, Screen.Infra,
            Screen.Database, Screen.ApiDocs, Screen.Telemetry
        ).shuffled()
        drawerScreens.value = top3 + others + bottom2
    }
    
    LaunchedEffect(Unit) {
        GlobalAppState.refreshColors()
        randomizeScreens()
    }
    
    val bgColor = if (isAccessible) Color.White else GlobalAppState.currentBgColor.value
    val navColor = if (isAccessible) Color.LightGray else GlobalAppState.currentNavColor.value
    val textColor = if (isAccessible) Color.Black else GlobalAppState.currentTextColor.value

    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            background = bgColor,
            surface = navColor,
            primary = textColor,
            onBackground = textColor,
            onSurface = textColor
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    Surface(color = navColor, elevation = AppBarDefaults.TopAppBarElevation) {
                        TopAppBar(
                            title = { Text("ipv5 ${if(isIpv7) "chaos" else "manager"}", color = textColor) },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, contentDescription = "menu", tint = textColor)
                                }
                            },
                            actions = {
                                TextButton(onClick = { 
                                    val wasAccessible = GlobalAppState.accessibilityMode.value
                                    GlobalAppState.accessibilityMode.value = !wasAccessible
                                    GlobalAppState.refreshColors()
                                    randomizeScreens()
                                }) {
                                    Text("accessible", color = textColor)
                                }
                            },
                            backgroundColor = Color.Transparent,
                            elevation = 0.dp,
                            modifier = Modifier.statusBarsPadding()
                        )
                    }
                },
                drawerContent = {
                    Column(modifier = Modifier.fillMaxSize().background(color = bgColor)) {
                        Text(
                            "systems and as operations", 
                            modifier = Modifier.padding(16.dp), 
                            style = MaterialTheme.typography.h6,
                            fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default
                        )
                        Divider()
                        
                        LazyColumn {
                            items(drawerScreens.value) { screen ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            scope.launch {
                                                if (isIpv7) delay(2000)
                                                navController.navigate(screen.route) {
                                                    popUpTo(Screen.Dashboard.route)
                                                    launchSingleTop = true
                                                }
                                                scaffoldState.drawerState.close()
                                            }
                                        }
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    screen.icon()
                                    Spacer(Modifier.width(32.dp))
                                    Text(screen.label, fontFamily = if(isAccessible) FontFamily.Default else GlobalAppState.getRandomFont())
                                }
                            }
                        }
                    }
                },
                bottomBar = {
                    Surface(color = navColor, elevation = BottomNavigationDefaults.Elevation) {
                        BottomNavigation(
                            backgroundColor = Color.Transparent,
                            elevation = 0.dp,
                            modifier = Modifier.navigationBarsPadding()
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route
                            val screens = listOf(Screen.Dashboard, Screen.Security, Screen.Predictor, Screen.Ping, Screen.DNS, Screen.IPv7, Screen.More)
                            
                            screens.forEach { screen ->
                                val isSelected = if (screen == Screen.More) isRightDrawerOpen else currentRoute == screen.route
                                BottomNavigationItem(
                                    icon = screen.icon,
                                    label = { Text(screen.label, fontFamily = if (isIpv7) FontFamily.Cursive else FontFamily.Default) },
                                    selected = isSelected,
                                    onClick = {
                                        scope.launch {
                                            if (isIpv7) delay(2000)
                                            if (screen == Screen.More) {
                                                isRightDrawerOpen = true
                                            } else {
                                                navController.navigate(screen.route) {
                                                    popUpTo(Screen.Dashboard.route)
                                                    launchSingleTop = true
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                val contentOffset = remember { Animatable(0f) }
                LaunchedEffect(isIpv7) {
                    if (isIpv7) {
                        while(true) {
                            contentOffset.animateTo(15f, animationSpec = tween(10000))
                            contentOffset.animateTo(-15f, animationSpec = tween(10000))
                        }
                    } else {
                        contentOffset.animateTo(0f)
                    }
                }
                Box(modifier = Modifier.padding(innerPadding).fillMaxSize().offset(y = contentOffset.value.dp).background(color = bgColor)) {
                    NavHost(navController, startDestination = Screen.Dashboard.route) {
                        composable(Screen.Dashboard.route) { DashboardScreen() }
                        composable(Screen.Security.route) { SecurityScreen() }
                        composable(Screen.Predictor.route) { PredictorScreen() }
                        composable(Screen.Ping.route) { PingScreen() }
                        composable(Screen.DNS.route) { DnsScreen() }
                        composable(Screen.IPv7.route) { Ipv7Screen() }
                        composable(Screen.More.route) { MoreFeaturesScreen() }
                        
                        composable(Screen.About.route) { AboutScreen() }
                        composable(Screen.Settings.route) { SettingsScreen() }
                        composable(Screen.Admin.route) { AdminScreen() }
                        composable(Screen.Dev.route) { DevScreen() }
                        
                        composable(Screen.DevOps.route) { DevOpsPanel() }
                        composable(Screen.FinOps.route) { FinOpsPanel() }
                        composable(Screen.Splunk.route) { SplunkPanel() }
                        composable(Screen.Grafana.route) { GrafanaPanel() }
                        composable(Screen.Ansible.route) { AnsiblePanel() }
                        composable(Screen.B2BSaaS.route) { B2BSaaSPanel() }
                        composable(Screen.Chaos.route) { ChaosPanel() }
                        composable(Screen.Pizza.route) { PizzaTrackerPanel() }
                        composable(Screen.HR.route) { HRPanel() }
                        composable(Screen.Lawyer.route) { LawyerPanel() }
                        composable(Screen.Marketing.route) { MarketingPanel() }
                        composable(Screen.Coffee.route) { CoffeePanel() }
                        composable(Screen.Weather.route) { WeatherPanel() }
                        composable(Screen.Stock.route) { StockPanel() }
                        composable(Screen.Astrology.route) { AstrologyPanel() }
                        composable(Screen.History.route) { HistoryPanel() }
                        composable(Screen.Secret.route) { SecretPanel() }
                        composable(Screen.BugTracker.route) { BugTrackerPanel() }
                        composable(Screen.Support.route) { SupportPanel() }
                        composable(Screen.Compliance.route) { CompliancePanel() }
                        composable(Screen.Doc.route) { DocPanel() }
                        composable(Screen.Feedback.route) { FeedbackPanel() }
                        composable(Screen.Sales.route) { SalesPanel() }
                        composable(Screen.Infra.route) { InfraPanel() }
                        composable(Screen.Database.route) { DatabasePanel() }
                        composable(Screen.ApiDocs.route) { ApiDocsPanel() }
                        composable(Screen.Telemetry.route) { TelemetryPanel() }
                        composable(Screen.Void.route) { VoidPanel() }
                        composable(Screen.QuantumCat.route) { QuantumCatPanel() }
                        composable(Screen.WordSearch.route) { WordSearchPanel() }
                    }
                }
            }

            // Sliding Right Drawer (Full Screen Width)
            AnimatedVisibility(
                visible = isRightDrawerOpen,
                enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)),
                exit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = bgColor)
                        .clickable(enabled = false) {} // block clicks below
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .navigationBarsPadding()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { isRightDrawerOpen = false }) {
                                Icon(Icons.Default.Close, contentDescription = "close", tint = textColor)
                            }
                            Spacer(Modifier.width(16.dp))
                            Text(
                                "the esoteric toolset (36)",
                                style = MaterialTheme.typography.h5,
                                color = textColor,
                                fontFamily = if (isIpv7) FontFamily.Cursive else FontFamily.Default
                            )
                        }
                        Divider(color = textColor.copy(alpha = 0.2f))
                        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                            items(shuffledEsotericFeatures) { feature ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                        .clickable {
                                            selectedFeature = feature
                                        },
                                    elevation = 4.dp,
                                    backgroundColor = navColor
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            feature.name,
                                            fontWeight = FontWeight.Bold,
                                            color = textColor,
                                            fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default
                                        )
                                        Spacer(Modifier.height(4.dp))
                                        Text(
                                            feature.description,
                                            style = MaterialTheme.typography.body2,
                                            color = textColor.copy(alpha = 0.8f),
                                            fontFamily = if (isIpv7) FontFamily.Cursive else FontFamily.Default
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Sliding Custom Sub-Drawer (Slide-Up Bottom Sheet Panel)
            AnimatedVisibility(
                visible = selectedFeature != null,
                enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)),
                exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(bgColor)
                        .clickable(enabled = false) {} // block clicks below
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(navColor)
                                .statusBarsPadding()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { selectedFeature = null }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back", tint = textColor)
                            }
                            Spacer(Modifier.width(16.dp))
                            Text(
                                selectedFeature?.name ?: "",
                                style = MaterialTheme.typography.h6,
                                color = textColor,
                                fontFamily = if (isIpv7) FontFamily.Cursive else FontFamily.Default
                            )
                        }
                        Divider(color = textColor.copy(alpha = 0.2f))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            selectedFeature?.content?.invoke()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardScreen() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val isAccessible = GlobalAppState.accessibilityMode.value
    val fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default
    val titleFont = if (isIpv7) FontFamily.Cursive else FontFamily.Default
    
    var ip by remember { mutableStateOf(IPv5Address.random()) }
    var ipv7Address by remember { mutableStateOf(IPv5Address.randomIPv7()) }
    var realIpv4 by remember { mutableStateOf("Fetching...") }
    var realIpv6 by remember { mutableStateOf("Fetching...") }
    var selectedSign by remember { mutableStateOf("Aries") }
    var astrologicalIp by remember { mutableStateOf("") }
    var quantumState by remember { mutableStateOf("Superposition") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        realIpv4 = fetchIp(false)
        realIpv6 = fetchIp(true)
        while(true) {
            delay(2000)
            quantumState = listOf("Alive", "Dead", "Both", "Missing").random()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("IPv5 Dashboard", style = MaterialTheme.typography.h3, fontFamily = titleFont, color = if(isAccessible) Color.Black else if(isIpv7) Color.Cyan else Color.Unspecified)
        Spacer(Modifier.height(16.dp))
        
        Text("Quantum Packet State: $quantumState", color = if(isAccessible) Color.Black else Color.Magenta, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
        Text("Local Cloud Density: ${Random.nextInt(0, 100)}% (Routing impact: HIGH)", style = MaterialTheme.typography.caption, fontFamily = fontFamily)
        
        Spacer(Modifier.height(16.dp))
        Text("Legacy Protocols:", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
        Text("IPv3 (Deprecated): 192.168.1.???", color = Color.Gray, fontFamily = fontFamily, fontWeight = FontWeight.Bold)
        Text("IPv4 (Boring): $realIpv4", color = if(isAccessible) Color.Black else if(isIpv7) Color.Green else Color.Gray, fontFamily = fontFamily)
        Text("IPv6 (Try-hard): $realIpv6", color = if(isAccessible) Color.Black else if(isIpv7) Color.Red else Color.Gray, fontFamily = fontFamily)

        if (isIpv7) {
            Spacer(Modifier.height(8.dp))
            Text("IPv7 (PREMIUM): $ipv7Address", color = if(isAccessible) Color.Black else Color.Yellow, fontFamily = fontFamily, fontWeight = FontWeight.ExtraBold)
        }

        Spacer(Modifier.height(32.dp))
        Text("Current Dynamic IPv5:", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
        Card(
            elevation = if(isIpv7) 24.dp else 4.dp, 
            modifier = Modifier.padding(16.dp), 
            backgroundColor = if(isIpv7) Color.Black else MaterialTheme.colors.surface
        ) {
            Text(
                ip.toString(),
                modifier = Modifier.padding(24.dp),
                style = MaterialTheme.typography.h5,
                color = if(isIpv7) Color.Yellow else MaterialTheme.colors.primary,
                fontFamily = fontFamily
            )
        }
        Button(
            onClick = { 
                ip = IPv5Address.random()
                if (isIpv7) ipv7Address = IPv5Address.randomIPv7()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = if(isIpv7) Color.Red else Color(0xFF6200EE))
        ) {
            Text("Re-polarize Quantum Field", fontFamily = fontFamily, color = Color.White)
        }
        Spacer(Modifier.height(16.dp))
        Text("IPv5 Protocol Status: ${if (isIpv7) "MIND BLOWN" else "HYPER-ACTIVE"}", color = if(isIpv7) Color.Blue else Color(0xFF4CAF50), fontSize = if(isIpv7) 24.sp else 16.sp, fontFamily = titleFont)
        
        Spacer(Modifier.height(32.dp))
        Divider()
        Text("Astrological IP Allocation", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
        val signs = listOf("Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces")
        var expanded by remember { mutableStateOf(false) }
        Box {
            Button(
                onClick = { expanded = true },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE))
            ) { 
                Text(selectedSign, color = Color.White) 
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                signs.forEach { sign ->
                    DropdownMenuItem(onClick = { 
                        selectedSign = sign
                        expanded = false
                        astrologicalIp = IPv5Utilities.getAstrologicalIp(sign)
                    }) { Text(sign) }
                }
            }
        }
        if (astrologicalIp.isNotEmpty()) {
            Text("Your Astral IP: $astrologicalIp", color = Color(0xFF9C27B0), fontWeight = FontWeight.Bold, fontFamily = fontFamily)
        }
    }
}

@Composable
fun SecurityScreen() {
    var mac by remember { mutableStateOf("00:1A:2B:3C:4D:5E") }
    var ipv5 by remember { mutableStateOf(IPv5Address.random()) }
    var entangled by remember { mutableStateOf("") }
    var culinaryMac by remember { mutableStateOf("") }
    var tinFoilMode by remember { mutableStateOf(false) }
    var bluetoothStatus by remember { mutableStateOf("Ready") }
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val isAccessible = GlobalAppState.accessibilityMode.value
    val fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("MAC Entanglement", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Text("XOR security for the modern age.", style = MaterialTheme.typography.caption, fontFamily = fontFamily)
        Spacer(Modifier.height(16.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Tin Foil Hat Protection", modifier = Modifier.weight(1f))
            Switch(checked = tinFoilMode, onCheckedChange = { tinFoilMode = it })
        }
        
        Spacer(Modifier.height(8.dp))
        TextField(value = mac, onValueChange = { mac = it }, label = { Text("Device MAC Address") })
        Spacer(Modifier.height(8.dp))
        
        Box(modifier = if(tinFoilMode) Modifier.background(color = Color.LightGray).padding(8.dp) else Modifier) {
            Text("Base IPv5: ${ipv5}", fontFamily = fontFamily)
        }
        
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { 
                val mathResult = IPv5Utilities.evaluateSimpleMath(mac)
                if (mathResult != null) {
                    entangled = "00:00:00:00:00:" + mathResult.toString(16).uppercase().padStart(2, '0')
                } else {
                    entangled = IPv5Utilities.entangleMac(ipv5, mac) 
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE))
        ) {
            Text("Entangle Security", fontFamily = fontFamily, color = Color.White)
        }
        
        if (entangled.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            Text("Entangled IPv5 Address:", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
            Text(entangled, color = if(isAccessible) Color.Black else if(isIpv7) Color.White else Color.Magenta, style = MaterialTheme.typography.h5, fontFamily = fontFamily, modifier = Modifier.background(color = if(isAccessible) Color.Transparent else if(isIpv7) Color.Black else Color.Transparent))
        }
        
        Spacer(Modifier.height(32.dp))
        Divider()
        Text("Experimental Security", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
        
        Button(
            onClick = { culinaryMac = IPv5Utilities.scrambleMacCulinary(mac) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
        ) {
            Text("Hash MAC with Chicken Noodle Soup", fontFamily = fontFamily, color = Color.White)
        }
        if (culinaryMac.isNotEmpty()) {
            Text("Culinary Hash: $culinaryMac", color = Color(0xFF4CAF50), fontFamily = fontFamily)
        }
        
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { 
                bluetoothStatus = "Translating Bluetooth signals to IPv5..."
                // Mock delay logic could go here
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2196F3))
        ) {
            Text("Translate Bluetooth to IPv5 Node", fontFamily = fontFamily, color = Color.White)
        }
        Text("Bluetooth Status: $bluetoothStatus", style = MaterialTheme.typography.caption, fontFamily = fontFamily)
    }
}

@Composable
fun PredictorScreen() {
    var battery by remember { mutableStateOf(42) }
    var port by remember { mutableStateOf(0) }
    var teaReading by remember { mutableStateOf("") }
    var tunnelingPort by remember { mutableStateOf(0) }
    var isTunneling by remember { mutableStateOf(false) }
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val isAccessible = GlobalAppState.accessibilityMode.value
    val fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default

    LaunchedEffect(isTunneling) {
        if (isTunneling) {
            while (isTunneling) {
                tunnelingPort = Random.nextInt(1024, 65535)
                delay(50)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Dynamic Port Predictor", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Text("Ports determined by energy levels.", style = MaterialTheme.typography.caption, fontFamily = fontFamily)
        Spacer(Modifier.height(16.dp))
        Text("Simulated Battery: ${battery}%", fontFamily = fontFamily)
        Slider(value = battery.toFloat(), onValueChange = { battery = it.toInt() }, valueRange = 0f..100f)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { port = IPv5Utilities.predictPort(battery) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE))
        ) {
            Text("Predict Active Port", fontFamily = fontFamily, color = Color.White)
        }
        if (port != 0) {
            Text("Suggested Port: $port", style = MaterialTheme.typography.h5, color = if(isAccessible) Color.Black else if(isIpv7) Color.Red else Color(0xFF4CAF50), fontFamily = fontFamily)
        }
        
        Spacer(Modifier.height(32.dp))
        Divider()
        Text("Esoteric Port Methods", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
        
        Button(
            onClick = { teaReading = IPv5Utilities.getTeaLeafReading() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF795548))
        ) {
            Text("Divinate Port via Tea Leaves", fontFamily = fontFamily, color = Color.White)
        }
        if (teaReading.isNotEmpty()) {
            Text("The Leaves say: $teaReading", color = Color(0xFF795548), fontFamily = fontFamily)
            Text("Recommended Port: ${Random.nextInt(1, 1024)}", fontWeight = FontWeight.Bold, fontFamily = fontFamily)
        }
        
        Spacer(Modifier.height(16.dp))
        Text("Quantum Tunneling Port: ${if (tunnelingPort == 0) "---" else tunnelingPort}", style = MaterialTheme.typography.h5, fontFamily = fontFamily)
        Button(
            onClick = { 
                if (isTunneling) {
                    isTunneling = false
                } else {
                    isTunneling = true
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C27B0))
        ) {
            Text(if (isTunneling) "Collapse Wavefunction" else "Begin Quantum Tunneling", fontFamily = fontFamily, color = Color.White)
        }
    }
}

@Composable
fun PingScreen() {
    var host by remember { mutableStateOf("127.0.0.1") }
    var results = remember { mutableStateListOf<String>() }
    var realLatencies = remember { mutableStateListOf<Long?>() }
    var pinging by remember { mutableStateOf(false) }
    var paranormalMode by remember { mutableStateOf(false) }
    var pigeonMode by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Boomerang Ping", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Text("Routes localhost through the moon for accuracy.", style = MaterialTheme.typography.caption, fontFamily = fontFamily)
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Paranormal Mode", modifier = Modifier.weight(1f))
            Checkbox(checked = paranormalMode, onCheckedChange = { paranormalMode = it })
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Carrier Pigeon Mode", modifier = Modifier.weight(1f))
            Checkbox(checked = pigeonMode, onCheckedChange = { pigeonMode = it })
        }
        
        TextField(value = host, onValueChange = { host = it }, label = { Text("Target Host") }, modifier = Modifier.fillMaxWidth())
        Button(
            onClick = {
                scope.launch {
                    pinging = true
                    results.clear()
                    realLatencies.clear()
                    val modePrefix = if(paranormalMode) "[GHOST] " else if(pigeonMode) "[BIRD] " else ""
                    results.add(0, "${modePrefix}Pinging $host via Boomerang Route...")
                    repeat(4) {
                        val realLatency = pingHost(host)
                        delay(if(isIpv7) 100 else 1000)
                        
                        if (paranormalMode) {
                            results.add(0, "Reply from The Beyond: latency=${IPv5Utilities.getParanormalLatency()}")
                            realLatencies.add(0, realLatency)
                        } else if (pigeonMode) {
                            results.add(0, "Pigeon at ${Random.nextInt(10, 50)}km/h. Wind resistance: HIGH. Reply in ${Random.nextInt(5, 30)}s")
                            realLatencies.add(0, realLatency)
                        } else if (realLatency != null) {
                            results.add(0, "Reply from $host: bytes=40 time=${realLatency * 10000}ms TTL=1")
                            realLatencies.add(0, realLatency)
                        } else {
                            results.add(0, "Request timed out: The Moon is currently blocked by a cloud.")
                            realLatencies.add(0, null)
                        }
                    }
                    pinging = false
                }
            }, 
            enabled = !pinging,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE91E63))
        ) {
            Text(if (pinging) "Pinging..." else "Start Boomerang Ping", fontFamily = fontFamily, color = Color.White)
        }
        
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(results) { index, res ->
                Column {
                    Text(res, style = MaterialTheme.typography.overline, fontFamily = fontFamily, fontSize = if(isIpv7) 14.sp else 10.sp)
                    val real = realLatencies.getOrNull(index)
                    if (real != null) {
                        Text(
                            "Actual latency: ${real}ms (Highlight to reveal secrets)", 
                            color = Color.Transparent, 
                            fontSize = 8.sp,
                            fontFamily = fontFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DnsScreen() {
    var url by remember { mutableStateOf("google.com") }
    var logs = remember { mutableStateListOf<String>() }
    var resolving by remember { mutableStateOf(false) }
    var emojiDnsMode by remember { mutableStateOf(false) }
    var ancestralMode by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val isAccessible = GlobalAppState.accessibilityMode.value
    val fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Slow-DNS Resolver", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Emoji Translation", modifier = Modifier.weight(1f))
            Switch(checked = emojiDnsMode, onCheckedChange = { emojiDnsMode = it })
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Ancestral (Geocities) Mode", modifier = Modifier.weight(1f))
            Switch(checked = ancestralMode, onCheckedChange = { ancestralMode = it })
        }
        
        TextField(value = url, onValueChange = { url = it }, label = { Text("URL to resolve") }, modifier = Modifier.fillMaxWidth())
        Button(
            onClick = {
                scope.launch {
                    resolving = true
                    logs.clear()
                    val realIp = resolveDns(url)
                    val steps = if(ancestralMode) {
                        listOf("Connecting to 56k Modem...", "Bypassing Under Construction signs...", "Searching the Yahoo! Directory...", "Ancestral site located!")
                    } else {
                        listOf("Contacting root servers...", "Reticulating splines...", "Consulting the Oracle...", "Waiting for carrier pigeon...", "IPv5 found!")
                    }
                    
                    for (step in steps) {
                        logs.add(if(emojiDnsMode) "🛠️ ${step}" else step)
                        delay(if(isIpv7) Random.nextLong(10, 100) else Random.nextLong(1000, 3000))
                    }
                    
                    if (emojiDnsMode) {
                        logs.add("Resolution Result: ${IPv5Utilities.getEmojiDns(url)}")
                    } else if (ancestralMode) {
                        logs.add("Ancestral URL: www.geocities.com/SiliconValley/Hacker/${url.split(".")[0]}/index.html")
                    } else if (realIp != null) {
                        val antiIp = realIp.split(".").reversed().joinToString(".")
                        val hexMac = realIp.split(".").map { it.toIntOrNull()?.toString(16)?.uppercase()?.padStart(2, '0') ?: "00" }.joinToString(":")
                        logs.add("Resolved Anti-IP: $antiIp")
                        logs.add("Discovered MAC Address: $hexMac")
                    } else {
                        logs.add("Resolution failed: Carrier pigeon lost at sea.")
                    }
                    resolving = false
                }
            }, 
            enabled = !resolving,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF9800))
        ) {
            Text(if (resolving) "Resolving..." else "Resolve URL", fontFamily = fontFamily, color = Color.White)
        }
        
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(logs) { log ->
                Text("> $log", style = MaterialTheme.typography.overline, fontFamily = fontFamily, fontSize = if(isIpv7) 16.sp else 10.sp, color = if(isAccessible) Color.Black else if(isIpv7) Color.White else Color.Unspecified)
            }
        }
    }
}

@Composable
fun Ipv7Screen() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("IPv7 Premium", style = MaterialTheme.typography.h2, fontFamily = if(isIpv7) FontFamily.Monospace else FontFamily.Default, color = if(isIpv7) Color.White else Color.Unspecified)
            Text("9-octet Base64 Addresses", style = MaterialTheme.typography.h5, color = if(isIpv7) Color.Yellow else Color.Unspecified)
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    GlobalAppState.ipv7Mode.value = !GlobalAppState.ipv7Mode.value
                    GlobalAppState.refreshColors()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = if(isIpv7) Color.Green else Color(0xFF6200EE))
            ) {
                Text(if(isIpv7) "DEACTIVATE CHAOS" else "UNLEASH IPv7 CHAOS", color = Color.White)
            }
        }
    }
}

@Composable
fun AboutScreen() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("About IPv5", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Spacer(Modifier.height(16.dp))
        Text(
            "IPv5 is the revolutionary networking protocol that skipped the number 4 (well, 4 was taken) and went straight to 5, only to be immediately superseded by the chaos of IPv7.",
            fontFamily = fontFamily
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Developed by a team of highly caffeinated octopuses, IPv5 uses quantum-entangled MAC addresses and battery-powered port prediction to ensure that your data stays exactly where it was supposed to be (mostly).",
            fontFamily = fontFamily
        )
        Spacer(Modifier.height(16.dp))
        Text("Version: 0.5.7-beta (Bleeding Edge)", fontWeight = FontWeight.Bold, fontFamily = fontFamily)
        
        Spacer(Modifier.height(32.dp))
        Divider()
        Text("The Esoteric History of Proto-IPv5 Systems", style = MaterialTheme.typography.h6, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Spacer(Modifier.height(12.dp))
        
        Text(
            "Phase 1: The MGH Era (1966-1968)",
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            color = if(isIpv7) Color.Yellow else Color.Unspecified
        )
        Text(
            "In 1966, G. Octo Barnett and his team at MGH Laboratory of Computer Science realized that traditional data structures were too 'logical'. They birthed MUMPS (Massachusetts General Hospital Utility Multi-Programming System). Unlike inferior languages, MUMPS treated 'Globals' (hierarchical disk-based sparse arrays) as the primary deity. These ^Globals allowed doctors to store data in a tree structure so complex that the only way to retrieve it was via a blood sacrifice or a 256-node traversal in a single string-subscripted instruction.",
            fontFamily = fontFamily,
            style = MaterialTheme.typography.body2
        )
        
        Spacer(Modifier.height(12.dp))
        Text(
            "Phase 2: The MIIS Schism (1969-1972)",
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            color = if(isIpv7) Color.Yellow else Color.Unspecified
        )
        Text(
            "By 1969, the standard MUMPS path was deemed too 'public'. Neil Pappalardo and Curt Marble led a strategic retreat into MIIS (Meditech Interpretive Information System). MIIS was optimized to run on the DEC PDP-11, where it didn't just 'run' on the OS—it WAS the OS. It bypassed the concept of files entirely, treating the hard drive platter as one giant, spinning MUMPS global. \n\nTechnically, MIIS introduced the 'Proprietary Truth' constant: while standard MUMPS used 1/0, MIIS used the ASCII 127 (Delete) character for 'True' and an empty string for 'False', ensuring that if your logic was correct, the computer would physically try to delete its own mind.",
            fontFamily = fontFamily,
            style = MaterialTheme.typography.body2
        )

        Spacer(Modifier.height(12.dp))
        Text(
            "Phase 3: The Secret IPv5 Pre-Scoping (1974)",
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            color = if(isIpv7) Color.Yellow else Color.Unspecified
        )
        Text(
            "In 1974, a secret lab in a bunker beneath a Boston hospital attempted to bridge MIIS nodes using 'MIIS-Net'. They discovered that by vibrating the core memory of a PDP-11 at exactly 14.318 MHz, they could transmit three-octet addresses. This was 'IPv5-Alpha'. However, the protocol was so efficient that it began predicting user input before the user had even bought a computer. To prevent a temporal paradox, the project was buried under 5,000 tons of discarded punch cards, only to be rediscovered in 2026 by the Chaos Foundation.",
            fontFamily = fontFamily,
            style = MaterialTheme.typography.body2
        )
        
        Spacer(Modifier.height(12.dp))
        Text(
            "Phase 4: The MAGIC and NPR Dark Ages",
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            color = if(isIpv7) Color.Yellow else Color.Unspecified
        )
        Text(
            "As MIIS aged, it evolved into MAGIC (1982), a language so high-level it required a wizard's permit to compile. Then came NPR (Non-Procedural Representation), where the code was so non-procedural it often refused to execute on Tuesdays unless there was a full moon or a specific brand of pepperoni pizza in the server room.",
            fontFamily = fontFamily,
            style = MaterialTheme.typography.body2
        )

        Spacer(Modifier.height(32.dp))
        Divider()
        Text("THE SACRED TEXTS (1000+ Lines of Pure Chaos)", style = MaterialTheme.typography.h6, fontFamily = if(isIpv7) FontFamily.Monospace else FontFamily.Default)
        Spacer(Modifier.height(8.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth().height(400.dp),
            backgroundColor = if(isIpv7) Color.Black else Color.DarkGray
        ) {
            Box(modifier = Modifier.padding(8.dp).verticalScroll(rememberScrollState())) {
                Text(
                    IPv5Lore.HUGE_HISTORY,
                    color = if(isIpv7) Color.Green else Color.White,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp
                )
            }
        }
        
        Spacer(Modifier.height(16.dp))
        Text("Copyright © 2026 The Chaos Foundation", style = MaterialTheme.typography.caption, fontFamily = fontFamily)
    }
}

@Composable
fun SettingsScreen() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    var quantumRouting by remember { mutableStateOf(true) }
    var bypassMainframe by remember { mutableStateOf(false) }
    var pigeonPriority by remember { mutableStateOf(true) }
    var entanglementStrength by remember { mutableStateOf(0.5f) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("System Settings", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Spacer(Modifier.height(24.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Enable Quantum Routing", modifier = Modifier.weight(1f))
            Switch(checked = quantumRouting, onCheckedChange = { quantumRouting = it })
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Bypass Mainframe (Risky)", modifier = Modifier.weight(1f))
            Switch(checked = bypassMainframe, onCheckedChange = { bypassMainframe = it })
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Carrier Pigeon Priority", modifier = Modifier.weight(1f))
            Switch(checked = pigeonPriority, onCheckedChange = { pigeonPriority = it })
        }
        
        Spacer(Modifier.height(16.dp))
        Text("Quantum Entanglement Strength: ${(entanglementStrength * 100).toInt()}%")
        Slider(value = entanglementStrength, onValueChange = { entanglementStrength = it })
        
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = { 
                GlobalAppState.ipv7Mode.value = !isIpv7
                GlobalAppState.refreshColors()
            }, 
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = if(isIpv7) Color.Red else Color(0xFF6200EE))
        ) {
            Text(if(isIpv7) "RESTORE SANITY" else "ACTIVATE IPv7 PROTOCOL", color = Color.White)
        }
    }
}

@Composable
fun AdminScreen() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    var ramStatus by remember { mutableStateOf("Low") }
    var load by remember { mutableStateOf(0.99f) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Admin Panel", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Text("Restricted Access - Level 5 Clearance Required", color = Color.Red, style = MaterialTheme.typography.caption)
        Spacer(Modifier.height(24.dp))
        
        Text("System Load: ${(load * 10000).toInt()}%", style = MaterialTheme.typography.h6)
        LinearProgressIndicator(progress = load, modifier = Modifier.fillMaxWidth(), color = Color.Red)
        Spacer(Modifier.height(16.dp))
        
        Button(
            onClick = { 
                ramStatus = "Downloading..."
                load = 0.42f
                ramStatus = "RAM Downloaded (Capacity: ∞)"
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2196F3))
        ) {
            Text("Download More RAM for Router", color = Color.White)
        }
        Text("RAM Status: $ramStatus", fontFamily = FontFamily.Monospace)
        
        Spacer(Modifier.height(16.dp))
        Text("Active Entanglements: 1,337", style = MaterialTheme.typography.body1)
        Text("Banned IPs: localhost, 127.0.0.1, ::1", style = MaterialTheme.typography.body1)
        Text("Mainframe Status: VIBRATING", style = MaterialTheme.typography.body1)
        
        Spacer(Modifier.weight(1f))
        Button(
            onClick = { /* Do nothing */ }, 
            modifier = Modifier.fillMaxWidth(), 
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text("SELF-DESTRUCT (MOCK)", color = Color.White)
        }
    }
}

@Composable
fun DevScreen() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState() // Note: This is local to DevScreen, maybe use a SnackbarHostState

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Dev Tools", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Spacer(Modifier.height(24.dp))
        
        Button(
            onClick = { throw NullPointerException("Chaos Mode NPE: Someone spilled coffee on the mainframe") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text("Force NullPointerException", color = Color.White)
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { 
                GlobalAppState.refreshColors()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF673AB7))
        ) {
            Text("Vaporize Local Cache", color = Color.White)
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { 
                scope.launch {
                    repeat(10) {
                        GlobalAppState.refreshColors()
                        delay(100)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722))
        ) {
            Text("Simulate Solar Flare Interference", color = Color.White)
        }        
        Spacer(Modifier.height(32.dp))
        Text("Developer Logs:", style = MaterialTheme.typography.h6)
        Card(modifier = Modifier.fillMaxWidth().height(200.dp), backgroundColor = Color.DarkGray) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                item { Text("DEBUG: Reticulating splines...", color = Color.Green, fontFamily = FontFamily.Monospace) }
                item { Text("WARN: Quantum fluctuation detected in sector 7G", color = Color.Yellow, fontFamily = FontFamily.Monospace) }
                item { Text("ERROR: Pizza delivery failed (Timeout)", color = Color.Red, fontFamily = FontFamily.Monospace) }
                item { Text("DEBUG: Entangling MACs with coffee...", color = Color.Green, fontFamily = FontFamily.Monospace) }
            }
        }
    }
}

@Composable
fun MoreFeaturesScreen() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val fontFamily = if (isIpv7) FontFamily.Cursive else FontFamily.Default
    
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Premium Features (Quantity > Quality)", style = MaterialTheme.typography.h4, fontFamily = fontFamily)
            Spacer(modifier = Modifier.height(16.dp))
        }
        val allFeatures = listOf(
            "DNS Roulette" to MoreFeatures.dnsRoulette("example.com"),
            "Quantum Packet Loss" to (MoreFeatures.quantumPacketLoss("Hello") ?: "Dropped!"),
            "Blockchain ARP" to MoreFeatures.blockchainArp(),
            "UDP Handshake" to MoreFeatures.udpHandshake(),
            "TCP Window Breaker" to MoreFeatures.tcpWindowBreaker().toString(),
            "HTTP/0.9 Downgrade" to MoreFeatures.http09Downgrade("request").trim(),
            "BGP Roulette" to MoreFeatures.bgpRoulette(),
            "ICMP Scream" to "65535 bytes of noise sent",
            "Subnet Mask Gen" to MoreFeatures.subnetMaskGenerator(),
            "WiFi De-auth" to MoreFeatures.wifiDeauthenticator(),
            "Localhost LB" to MoreFeatures.localhostLoadBalancer(),
            "IPv4 Exhaustion" to (MoreFeatures.ipv4ExhaustionSimulator("data") ?: "Out of IPs"),
            "IPv5 Literal" to MoreFeatures.ipv5Literal(),
            "SSL/TLS Downgrader" to MoreFeatures.sslTlsDowngrader(),
            "MAC Randomizer" to MoreFeatures.macAddressRandomizer(),
            "Fragmentation Max" to MoreFeatures.packetFragmentationMaximizer("test").toString(),
            "Traceroute Visualizer" to MoreFeatures.tracerouteVisualizer(),
            "Port Knocker" to MoreFeatures.portKnocker(),
            "DHCP Rejector" to MoreFeatures.dhcpRejector(),
            "Ping of Life" to MoreFeatures.pingOfLife(),
            "Cloud Latency" to MoreFeatures.cloudLatencyInjector(10).toString() + "ms",
            "Ethernet over DNS" to MoreFeatures.ethernetOverDns(),
            "Bluetooth LE Web" to MoreFeatures.bluetoothLeWebServer(),
            "VPN to Null" to MoreFeatures.vpnToNull(),
            "NAT Transversal" to MoreFeatures.natTransversal(),
            "SYN Flood Self-Defense" to MoreFeatures.synFloodSelfDefense(),
            "MTU Path Discovery Denier" to MoreFeatures.mtuPathDiscoveryDenier(),
            "BGP Hijacker" to MoreFeatures.bgpHijacker(),
            "WEP Encryption Enforcer" to MoreFeatures.wepEncryptionEnforcer(),
            "IP-over-Avian" to MoreFeatures.ipOverAvianCarriersSimulator(),
            "TCP Keep-Alive Spammer" to MoreFeatures.tcpKeepAliveSpammer(),
            "Proxy Chain Loop" to MoreFeatures.proxyChainLoop(),
            "DNSSEC Invalidator" to MoreFeatures.dnssecInvalidator(),
            "QoS Minimizer" to MoreFeatures.qosMinimizer(),
            "SNMP Public Stringer" to MoreFeatures.snmpPublicCommunityStringer(),
            "IPv6 to IPv4" to MoreFeatures.ipv6ToIpv4Translator("::1")
        )
        items(allFeatures) { (name, result) ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                elevation = 4.dp,
                backgroundColor = if (isIpv7) Color.LightGray else MaterialTheme.colors.surface
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(name, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(result, fontFamily = fontFamily)
                }
            }
        }
    }
}
