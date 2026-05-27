package net.notipv6.ipv5

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

sealed class Screen(val route: String, val label: String, val icon: @Composable () -> Unit) {
    object Dashboard : Screen("dashboard", "home", { Icon(Icons.Default.Home, null) })
    object Security : Screen("security", "mac", { Icon(Icons.Default.Lock, null) })
    object Predictor : Screen("predictor", "ports", { Icon(Icons.Default.Refresh, null) })
    object Ping : Screen("ping", "ping", { Icon(Icons.AutoMirrored.Filled.Send, null) })
    object DNS : Screen("dns", "dns", { Icon(Icons.Default.Search, null) })
    object IPv7 : Screen("ipv7", "ipv7+", { Icon(Icons.Default.Star, null) })
    object More : Screen("more", "more (36)", { Icon(Icons.AutoMirrored.Filled.List, null) })
    
    // New Drawer Screens
    object About : Screen("about", "about", { Icon(Icons.Default.Info, null) })
    object Settings : Screen("settings", "settings", { Icon(Icons.Default.Settings, null) })
    object Admin : Screen("admin", "admin panel", { Icon(Icons.Default.Lock, null) })
    object Dev : Screen("dev", "dev panel", { Icon(Icons.Default.Build, null) })
    object DevOps : Screen("devops", "devops panel", { Icon(Icons.Default.Refresh, null) })
    object FinOps : Screen("finops", "finops panel", { Icon(Icons.Default.ShoppingCart, null) })
    object Splunk : Screen("splunk", "splunk panel", { Icon(Icons.Default.Search, null) })
    object Grafana : Screen("grafana", "grafana panel", { Icon(Icons.AutoMirrored.Filled.List, null) })
    object Ansible : Screen("ansible", "ansible panel", { Icon(Icons.Default.Check, null) })
    object B2BSaaS : Screen("b2bsaas", "b2b saas panel", { Icon(Icons.Default.Star, null) })
    object Chaos : Screen("chaos", "chaos panel", { Icon(Icons.Default.Warning, null) })
    object Pizza : Screen("pizza", "pizza tracker", { Icon(Icons.Default.ShoppingCart, null) })
    object HR : Screen("hr", "hr portal", { Icon(Icons.Default.Person, null) })
    object Lawyer : Screen("lawyer", "legal dept", { Icon(Icons.Default.Info, null) })
    object Marketing : Screen("marketing", "marketing", { Icon(Icons.Default.Email, null) })
    object Coffee : Screen("coffee", "coffee status", { Icon(Icons.Default.Favorite, null) })
    object Weather : Screen("weather", "mars weather", { Icon(Icons.Default.LocationOn, null) })
    object Stock : Screen("stock", "stock market", { Icon(Icons.Default.Notifications, null) })
    object Astrology : Screen("astrology", "astrology routing", { Icon(Icons.Default.Star, null) })
    object History : Screen("history", "history", { Icon(Icons.Default.AccountBox, null) })
    object Secret : Screen("secret", "secret panel", { Icon(Icons.Default.Lock, null) })
    object BugTracker : Screen("bugtracker", "bug tracker", { Icon(Icons.Default.Warning, null) })
    object Support : Screen("support", "support", { Icon(Icons.Default.Call, null) })
    object Compliance : Screen("compliance", "compliance", { Icon(Icons.Default.Check, null) })
    object Doc : Screen("doc", "documentation", { Icon(Icons.Default.Menu, null) })
    object Feedback : Screen("feedback", "feedback", { Icon(Icons.AutoMirrored.Filled.Send, null) })
    object Sales : Screen("sales", "sales", { Icon(Icons.Default.ShoppingCart, null) })
    object Infra : Screen("infra", "infrastructure", { Icon(Icons.Default.Build, null) })
    object Database : Screen("database", "database", { Icon(Icons.AutoMirrored.Filled.List, null) })
    object ApiDocs : Screen("apidocs", "api docs", { Icon(Icons.Default.MoreVert, null) })
    object Telemetry : Screen("telemetry", "telemetry", { Icon(Icons.Default.Info, null) })
    object Void : Screen("void", "the void", { Icon(Icons.Default.Clear, null) })
    object QuantumCat : Screen("quantumcat", "quantum cat", { Icon(Icons.Default.Face, null) })
}

@Composable
fun App() {
    val navController = rememberNavController()
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    
    val bgColor = remember { GlobalAppState.getRandomVibrantColor() }
    val navColor = remember { GlobalAppState.getRandomVibrantColor() }
    val textColor = remember { GlobalAppState.getRandomVibrantColor() }

    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            background = bgColor,
            surface = navColor,
            primary = textColor,
            onBackground = textColor,
            onSurface = textColor
        )
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text("ipv5 ${if(isIpv7) "chaos" else "manager"}", color = textColor) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "menu", tint = textColor)
                        }
                    },
                    backgroundColor = navColor
                )
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
                    val drawerScreens = listOf(
                        Screen.About, Screen.Settings, Screen.Admin, Screen.Dev,
                        Screen.DevOps, Screen.FinOps, Screen.Splunk, Screen.Grafana,
                        Screen.Ansible, Screen.B2BSaaS, Screen.Chaos, Screen.Pizza,
                        Screen.HR, Screen.Lawyer, Screen.Marketing, Screen.Coffee,
                        Screen.Weather, Screen.Stock, Screen.Astrology, Screen.History,
                        Screen.Secret, Screen.BugTracker, Screen.Support, Screen.Compliance,
                        Screen.Doc, Screen.Feedback, Screen.Sales, Screen.Infra,
                        Screen.Database, Screen.ApiDocs, Screen.Telemetry, Screen.Void,
                        Screen.QuantumCat
                    )
                    LazyColumn {
                        items(drawerScreens) { screen ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                        scope.launch { scaffoldState.drawerState.close() }
                                    }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                screen.icon()
                                Spacer(Modifier.width(32.dp))
                                Text(screen.label, fontFamily = GlobalAppState.getRandomFont())
                            }
                        }
                    }
                }
            },
            bottomBar = {
                BottomNavigation(backgroundColor = navColor) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    val screens = listOf(Screen.Dashboard, Screen.Security, Screen.Predictor, Screen.Ping, Screen.DNS, Screen.IPv7, Screen.More)
                    
                    screens.forEach { screen ->
                        BottomNavigationItem(
                            icon = screen.icon,
                            label = { Text(screen.label, fontFamily = if (isIpv7) FontFamily.Cursive else FontFamily.Default) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize().background(color = bgColor)) {                NavHost(navController, startDestination = Screen.Dashboard.route) {
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
                }
            }
        }
    }
}

@Composable
fun DashboardScreen() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
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
        Text("IPv5 Dashboard", style = MaterialTheme.typography.h3, fontFamily = titleFont, color = if(isIpv7) Color.Cyan else Color.Unspecified)
        Spacer(Modifier.height(16.dp))
        
        Text("Quantum Packet State: $quantumState", color = Color.Magenta, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
        Text("Local Cloud Density: ${Random.nextInt(0, 100)}% (Routing impact: HIGH)", style = MaterialTheme.typography.caption, fontFamily = fontFamily)
        
        Spacer(Modifier.height(16.dp))
        Text("Legacy Protocols:", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
        Text("IPv3 (Deprecated): 192.168.1.???", color = Color.Gray, fontFamily = fontFamily, fontWeight = FontWeight.Bold)
        Text("IPv4 (Boring): $realIpv4", color = if(isIpv7) Color.Green else Color.Gray, fontFamily = fontFamily)
        Text("IPv6 (Try-hard): $realIpv6", color = if(isIpv7) Color.Red else Color.Gray, fontFamily = fontFamily)

        if (isIpv7) {
            Spacer(Modifier.height(8.dp))
            Text("IPv7 (PREMIUM): $ipv7Address", color = Color.Yellow, fontFamily = fontFamily, fontWeight = FontWeight.ExtraBold)
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
            colors = ButtonDefaults.buttonColors(backgroundColor = if(isIpv7) Color.Red else MaterialTheme.colors.primary)
        ) {
            Text("Re-polarize Quantum Field", fontFamily = fontFamily, color = if(isIpv7) Color.White else Color.Unspecified)
        }
        Spacer(Modifier.height(16.dp))
        Text("IPv5 Protocol Status: ${if (isIpv7) "MIND BLOWN" else "HYPER-ACTIVE"}", color = if(isIpv7) Color.Blue else Color(0xFF4CAF50), fontSize = if(isIpv7) 24.sp else 16.sp, fontFamily = titleFont)
        
        Spacer(Modifier.height(32.dp))
        Divider()
        Text("Astrological IP Allocation", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
        val signs = listOf("Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces")
        var expanded by remember { mutableStateOf(false) }
        Box {
            Button(onClick = { expanded = true }) { Text(selectedSign) }
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
        Button(onClick = { 
            val mathResult = IPv5Utilities.evaluateSimpleMath(mac)
            if (mathResult != null) {
                entangled = "00:00:00:00:00:" + mathResult.toString(16).uppercase().padStart(2, '0')
            } else {
                entangled = IPv5Utilities.entangleMac(ipv5, mac) 
            }
        }) {
            Text("Entangle Security", fontFamily = fontFamily)
        }
        
        if (entangled.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            Text("Entangled IPv5 Address:", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
            Text(entangled, color = if(isIpv7) Color.White else Color.Magenta, style = MaterialTheme.typography.h5, fontFamily = fontFamily, modifier = Modifier.background(color = if(isIpv7) Color.Black else Color.Transparent))
        }
        
        Spacer(Modifier.height(32.dp))
        Divider()
        Text("Experimental Security", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
        
        Button(onClick = { culinaryMac = IPv5Utilities.scrambleMacCulinary(mac) }) {
            Text("Hash MAC with Chicken Noodle Soup", fontFamily = fontFamily)
        }
        if (culinaryMac.isNotEmpty()) {
            Text("Culinary Hash: $culinaryMac", color = Color(0xFF4CAF50), fontFamily = fontFamily)
        }
        
        Spacer(Modifier.height(16.dp))
        Button(onClick = { 
            bluetoothStatus = "Translating Bluetooth signals to IPv5..."
            // Mock delay logic could go here
        }) {
            Text("Translate Bluetooth to IPv5 Node", fontFamily = fontFamily)
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
        Button(onClick = { port = IPv5Utilities.predictPort(battery) }) {
            Text("Predict Active Port", fontFamily = fontFamily)
        }
        if (port != 0) {
            Text("Suggested Port: $port", style = MaterialTheme.typography.h5, color = if(isIpv7) Color.Red else Color(0xFF4CAF50), fontFamily = fontFamily)
        }
        
        Spacer(Modifier.height(32.dp))
        Divider()
        Text("Esoteric Port Methods", style = MaterialTheme.typography.h6, fontFamily = fontFamily)
        
        Button(onClick = { teaReading = IPv5Utilities.getTeaLeafReading() }) {
            Text("Divinate Port via Tea Leaves", fontFamily = fontFamily)
        }
        if (teaReading.isNotEmpty()) {
            Text("The Leaves say: $teaReading", color = Color(0xFF795548), fontFamily = fontFamily)
            Text("Recommended Port: ${Random.nextInt(1, 1024)}", fontWeight = FontWeight.Bold, fontFamily = fontFamily)
        }
        
        Spacer(Modifier.height(16.dp))
        Text("Quantum Tunneling Port: ${if (tunnelingPort == 0) "---" else tunnelingPort}", style = MaterialTheme.typography.h5, fontFamily = fontFamily)
        Button(onClick = { 
            if (isTunneling) {
                isTunneling = false
            } else {
                isTunneling = true
            }
        }) {
            Text(if (isTunneling) "Collapse Wavefunction" else "Begin Quantum Tunneling", fontFamily = fontFamily)
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
        Button(onClick = {
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
        }, enabled = !pinging) {
            Text(if (pinging) "Pinging..." else "Start Boomerang Ping", fontFamily = fontFamily)
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
        Button(onClick = {
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
        }, enabled = !resolving) {
            Text(if (resolving) "Resolving..." else "Resolve URL", fontFamily = fontFamily)
        }
        
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(logs) { log ->
                Text("> $log", style = MaterialTheme.typography.overline, fontFamily = fontFamily, fontSize = if(isIpv7) 16.sp else 10.sp, color = if(isIpv7) Color.White else Color.Unspecified)
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
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = if(isIpv7) Color.Green else MaterialTheme.colors.primary)
            ) {
                Text(if(isIpv7) "DEACTIVATE CHAOS" else "UNLEASH IPv7 CHAOS")
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
        Button(onClick = { GlobalAppState.ipv7Mode.value = !isIpv7 }, modifier = Modifier.fillMaxWidth()) {
            Text(if(isIpv7) "RESTORE SANITY" else "ACTIVATE IPv7 PROTOCOL")
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
        
        Button(onClick = { 
            ramStatus = "Downloading..."
            load = 0.42f
            ramStatus = "RAM Downloaded (Capacity: ∞)"
        }) {
            Text("Download More RAM for Router")
        }
        Text("RAM Status: $ramStatus", fontFamily = FontFamily.Monospace)
        
        Spacer(Modifier.height(16.dp))
        Text("Active Entanglements: 1,337", style = MaterialTheme.typography.body1)
        Text("Banned IPs: localhost, 127.0.0.1, ::1", style = MaterialTheme.typography.body1)
        Text("Mainframe Status: VIBRATING", style = MaterialTheme.typography.body1)
        
        Spacer(Modifier.weight(1f))
        Button(onClick = { /* Do nothing */ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)) {
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
        
        Button(onClick = { /* Force crash logic */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Force NullPointerException")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { /* Clear cache */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Vaporize Local Cache")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { /* Simulate drop */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Simulate Solar Flare Interference")
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
