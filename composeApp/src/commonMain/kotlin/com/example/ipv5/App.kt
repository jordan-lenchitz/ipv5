package com.example.ipv5

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
    object Dashboard : Screen("dashboard", "Home", { Icon(Icons.Default.Home, null) })
    object Security : Screen("security", "MAC", { Icon(Icons.Default.Lock, null) })
    object Predictor : Screen("predictor", "Ports", { Icon(Icons.Default.Refresh, null) })
    object Ping : Screen("ping", "Ping", { Icon(Icons.Default.Send, null) })
    object DNS : Screen("dns", "DNS", { Icon(Icons.Default.Search, null) })
    object IPv7 : Screen("ipv7", "IPv7+", { Icon(Icons.Default.Star, null) })
    
    // New Drawer Screens
    object About : Screen("about", "About", { Icon(Icons.Default.Info, null) })
    object Settings : Screen("settings", "Settings", { Icon(Icons.Default.Settings, null) })
    object Admin : Screen("admin", "Admin Panel", { Icon(Icons.Default.Lock, null) }) // Reusing Lock icon or Build
    object Dev : Screen("dev", "Dev Panel", { Icon(Icons.Default.Build, null) })
}

@Composable
fun App() {
    val navController = rememberNavController()
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    
    val bgColor = if (isIpv7) Color(0xFFFF00FF) else MaterialTheme.colors.background // Magenta in IPv7
    val navColor = if (isIpv7) Color.Yellow else MaterialTheme.colors.surface

    MaterialTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text("IPv5 ${if(isIpv7) "CHAOS" else "Manager"}", fontFamily = if (isIpv7) FontFamily.Cursive else FontFamily.Default) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    backgroundColor = navColor
                )
            },
            drawerContent = {
                Column(modifier = Modifier.fillMaxSize().background(bgColor)) {
                    Text(
                        "System Operations", 
                        modifier = Modifier.padding(16.dp), 
                        style = MaterialTheme.typography.h6,
                        fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default
                    )
                    Divider()
                    val drawerScreens = listOf(Screen.About, Screen.Settings, Screen.Admin, Screen.Dev)
                    drawerScreens.forEach { screen ->
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
                            Text(screen.label, fontFamily = if(isIpv7) FontFamily.Monospace else FontFamily.Default)
                        }
                    }
                }
            },
            bottomBar = {
                BottomNavigation(backgroundColor = navColor) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    val screens = listOf(Screen.Dashboard, Screen.Security, Screen.Predictor, Screen.Ping, Screen.DNS, Screen.IPv7)
                    
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
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize().background(bgColor)) {
                NavHost(navController, startDestination = Screen.Dashboard.route) {
                    composable(Screen.Dashboard.route) { DashboardScreen() }
                    composable(Screen.Security.route) { SecurityScreen() }
                    composable(Screen.Predictor.route) { PredictorScreen() }
                    composable(Screen.Ping.route) { PingScreen() }
                    composable(Screen.DNS.route) { DnsScreen() }
                    composable(Screen.IPv7.route) { Ipv7Screen() }
                    
                    composable(Screen.About.route) { AboutScreen() }
                    composable(Screen.Settings.route) { SettingsScreen() }
                    composable(Screen.Admin.route) { AdminScreen() }
                    composable(Screen.Dev.route) { DevScreen() }
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
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        realIpv4 = fetchIp(false)
        realIpv6 = fetchIp(true)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("IPv5 Dashboard", style = MaterialTheme.typography.h3, fontFamily = titleFont, color = if(isIpv7) Color.Cyan else Color.Unspecified)
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
    }
}

@Composable
fun SecurityScreen() {
    var mac by remember { mutableStateOf("00:1A:2B:3C:4D:5E") }
    var ipv5 by remember { mutableStateOf(IPv5Address.random()) }
    var entangled by remember { mutableStateOf("") }
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("MAC Entanglement", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Text("XOR security for the modern age.", style = MaterialTheme.typography.caption, fontFamily = fontFamily)
        Spacer(Modifier.height(16.dp))
        TextField(value = mac, onValueChange = { mac = it }, label = { Text("Device MAC Address") })
        Spacer(Modifier.height(8.dp))
        Text("Base IPv5: ${ipv5}", fontFamily = fontFamily)
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
            Text(entangled, color = if(isIpv7) Color.White else Color.Magenta, style = MaterialTheme.typography.h5, fontFamily = fontFamily, modifier = Modifier.background(if(isIpv7) Color.Black else Color.Transparent))
        }
    }
}

@Composable
fun PredictorScreen() {
    var battery by remember { mutableStateOf(42) }
    var port by remember { mutableStateOf(0) }
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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
    }
}

@Composable
fun PingScreen() {
    var host by remember { mutableStateOf("127.0.0.1") }
    var results = remember { mutableStateListOf<String>() }
    var realLatencies = remember { mutableStateListOf<Long?>() }
    var pinging by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Boomerang Ping", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Text("Routes localhost through the moon for accuracy.", style = MaterialTheme.typography.caption, fontFamily = fontFamily)
        TextField(value = host, onValueChange = { host = it }, label = { Text("Target Host") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = {
            scope.launch {
                pinging = true
                results.clear()
                realLatencies.clear()
                results.add(0, "Pinging $host via Boomerang Route...")
                repeat(4) {
                    val realLatency = pingHost(host)
                    delay(if(isIpv7) 100 else 1000)
                    if (realLatency != null) {
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
    val scope = rememberCoroutineScope()
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val fontFamily = if (isIpv7) FontFamily.Monospace else FontFamily.Default

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Slow-DNS Resolver", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        TextField(value = url, onValueChange = { url = it }, label = { Text("URL to resolve") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = {
            scope.launch {
                resolving = true
                logs.clear()
                val realIp = resolveDns(url)
                val steps = listOf("Contacting root servers...", "Reticulating splines...", "Consulting the Oracle...", "Waiting for carrier pigeon...", "IPv5 found!")
                for (step in steps) {
                    logs.add(step)
                    delay(if(isIpv7) Random.nextLong(10, 100) else Random.nextLong(1000, 3000))
                }
                
                if (realIp != null) {
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
        Text("Copyright © 2026 The Chaos Foundation", style = MaterialTheme.typography.caption, fontFamily = fontFamily)
    }
}

@Composable
fun SettingsScreen() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    var quantumRouting by remember { mutableStateOf(true) }
    var bypassMainframe by remember { mutableStateOf(false) }
    var pigeonPriority by remember { mutableStateOf(true) }

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
        
        Spacer(Modifier.height(32.dp))
        Button(onClick = { GlobalAppState.ipv7Mode.value = !isIpv7 }, modifier = Modifier.fillMaxWidth()) {
            Text(if(isIpv7) "RESTORE SANITY" else "ACTIVATE IPv7 PROTOCOL")
        }
    }
}

@Composable
fun AdminScreen() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Admin Panel", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Text("Restricted Access - Level 5 Clearance Required", color = Color.Red, style = MaterialTheme.typography.caption)
        Spacer(Modifier.height(24.dp))
        
        Text("System Load: OVER 9000%", style = MaterialTheme.typography.h6)
        LinearProgressIndicator(progress = 0.99f, modifier = Modifier.fillMaxWidth(), color = Color.Red)
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
