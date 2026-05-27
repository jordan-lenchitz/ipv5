package com.example.ipv5

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun DevOpsPanel() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    var progress by remember { mutableStateOf(0.8f) }
    
    LaunchedEffect(Unit) {
        while(true) {
            delay(100)
            progress -= 0.01f
            if (progress <= 0f) progress = 1.0f
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("DevOps Panel", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Text("Deploying to production on a Friday...", color = Color.Red)
        Spacer(Modifier.height(24.dp))
        
        Text("Build Progress (In Reverse): ${(progress * 100).toInt()}%")
        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth(), color = Color.Magenta)
        
        Spacer(Modifier.height(16.dp))
        Text("Recent Logs:", fontWeight = FontWeight.Bold)
        Card(backgroundColor = Color.Black, modifier = Modifier.fillMaxWidth().height(200.dp)) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                item { Text("ERROR: Production DB deleted (Whoops)", color = Color.Red, fontFamily = FontFamily.Monospace) }
                item { Text("INFO: Bypassing unit tests for 'speed'", color = Color.Yellow, fontFamily = FontFamily.Monospace) }
                item { Text("DEBUG: Why is the server screaming?", color = Color.Green, fontFamily = FontFamily.Monospace) }
                item { Text("FATAL: Coffee machine offline", color = Color.Magenta, fontFamily = FontFamily.Monospace) }
            }
        }
    }
}

@Composable
fun FinOpsPanel() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    var burnRate by remember { mutableStateOf(4200.0) }
    var totalBurned by remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        while(true) {
            delay(10)
            totalBurned += (burnRate / 100.0)
            burnRate += Random.nextDouble(-10.0, 50.0)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("FinOps Panel", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Text("Optimizing shareholder value through chaos.", style = MaterialTheme.typography.caption)
        Spacer(Modifier.height(24.dp))
        
        Text("Current Money Burn Rate: $${burnRate.toInt()}/sec", style = MaterialTheme.typography.h5, color = Color.Red)
        Text("Total AWS Bill (Estimated): $${totalBurned.toInt()}", style = MaterialTheme.typography.h3, fontWeight = FontWeight.Bold)
        
        Spacer(Modifier.height(32.dp))
        Button(onClick = { burnRate *= 2 }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
            Text("Scaling for Growth (x2 Burn)", color = Color.White)
        }
    }
}

@Composable
fun SplunkPanel() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val logs = remember { mutableStateListOf<String>() }
    
    LaunchedEffect(Unit) {
        while(true) {
            delay(Random.nextLong(500, 2000))
            val log = listOf(
                "User breathed on keyboard",
                "Packet felt lonely and died",
                "Mainframe reached sentience (briefly)",
                "Admin spilled tea on Rack 4",
                "Gravity reversed in data center",
                "Bit flipped by cosmic ray",
                "Server decided it's a toaster now"
            ).random()
            logs.add(0, "[${System.currentTimeMillis()}] $log")
            if (logs.size > 50) logs.removeAt(50)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Splunk Panel", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Spacer(Modifier.height(16.dp))
        Card(backgroundColor = Color.DarkGray, modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(logs) { log ->
                    Text(log, color = Color.Green, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun GrafanaPanel() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Grafana Dashboard", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Spacer(Modifier.height(24.dp))
        
        Text("Metric: Customer Satisfaction")
        Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Red)) {
            Text("📉", modifier = Modifier.align(Alignment.Center), fontSize = 48.sp)
        }
        
        Spacer(Modifier.height(16.dp))
        Text("Metric: CEO Bonus")
        Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Green)) {
            Text("📈", modifier = Modifier.align(Alignment.Center), fontSize = 48.sp)
        }
        
        Spacer(Modifier.height(16.dp))
        Text("Metric: Router Temperature")
        Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Black)) {
            Text("🔥", modifier = Modifier.align(Alignment.Center), fontSize = 48.sp)
        }
    }
}

@Composable
fun AnsiblePanel() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Ansible Control", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Spacer(Modifier.height(16.dp))
        
        Text("Active Playbook: uninstall_everything.yml", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        
        repeat(5) { i ->
            Card(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()) {
                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Color.Red)
                    Spacer(Modifier.width(8.dp))
                    Text("TASK [Delete important database $i] *********")
                }
            }
        }
        
        Button(onClick = { /* Run playbook */ }, modifier = Modifier.fillMaxWidth()) {
            Text("FORCE EXECUTE (NO CALLBACKS)")
        }
    }
}

@Composable
fun B2BSaaSPanel() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    var apiKey by remember { mutableStateOf("") }
    var unlocked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Enterprise B2B SaaS", style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Spacer(Modifier.height(24.dp))

        if (!unlocked) {
            Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(64.dp))
            Text("Enterprise API Key Required", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(16.dp))
            TextField(value = apiKey, onValueChange = { apiKey = it }, label = { Text("Enter API Key") })
            Spacer(Modifier.height(8.dp))
            Button(onClick = { if (apiKey == "api_key") unlocked = true }) {
                Text("Authenticate (Billed Hourly)")
            }
        } else {
            Text("Welcome, VALUED ENTERPRISE PARTNER", color = Color.Magenta, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Text("Ultra-Expensive Features You Will Regret Purchasing:", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(16.dp))
            
            val features = listOf(
                "Manual Packet Delivery via Luxury Courier",
                "Golden Plated Ethernet Cables ($50,000/m)",
                "Quantum-Entangled Support Chat (Zero Latency)",
                "Bespoke IPv5 Subnets (Hand-crafted)",
                "Priority Routing through the CEO's iPad",
                "Blockchain-Backed Pong",
                "AI-Powered Random Rebooter"
            )
            
            LazyColumn {
                items(features) { feature ->
                    Card(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(), elevation = 8.dp) {
                        Text(feature, modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AbsurdPanel(title: String, content: String) {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(title, style = MaterialTheme.typography.h4, fontFamily = if(isIpv7) FontFamily.Cursive else FontFamily.Default)
        Spacer(Modifier.height(24.dp))
        Text(content, fontSize = 24.sp, fontFamily = if(isIpv7) FontFamily.Monospace else FontFamily.Default)
        Spacer(Modifier.height(24.dp))
        if (isIpv7) {
            Text("CHAOS DETECTED", color = Color.Red, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun PizzaTrackerPanel() = AbsurdPanel("Pizza Tracker", "Status: Dough is being reticulated.")
@Composable
fun HRPanel() = AbsurdPanel("HR Portal", "Your complaint has been successfully moved to /dev/null.")
@Composable
fun LawyerPanel() = AbsurdPanel("Legal Dept", "By reading this, you agree to donate your GPU to our mining pool.")
@Composable
fun MarketingPanel() = AbsurdPanel("Marketing", "AI-driven blockchain synergy for decentralized IPv5 paradigms.")
@Composable
fun CoffeePanel() = AbsurdPanel("Coffee Status", "Pot is EMPTY. Morale at 0.003%.")
@Composable
fun WeatherPanel() = AbsurdPanel("Mars Weather", "Dusty with a chance of zero oxygen. Routing unaffected.")
@Composable
fun StockPanel() = AbsurdPanel("Stock Market", "IPV5 Coin: 💎🙌 (Value: -$4.20)")
@Composable
fun AstrologyPanel() = AbsurdPanel("Astrology Routing", "MERCURY IS IN RETROGRADE. All packets will be lost.")
@Composable
fun HistoryPanel() = AbsurdPanel("History", "The Subnet Wars (1994-1996) were fought over a single bit.")
@Composable
fun SecretPanel() = AbsurdPanel("Shhh!", "The password is 'admin123'. Don't tell anyone.")
@Composable
fun BugTrackerPanel() = AbsurdPanel("Bug Tracker", "Bugs Found: 0. (Warning: Scanner is broken)")
@Composable
fun SupportPanel() = AbsurdPanel("Support", "Average Wait Time: 14 Years, 3 Days.")
@Composable
fun CompliancePanel() = AbsurdPanel("Compliance", "GDPR: Grossly Disrespectful Privacy Rules.")
@Composable
fun DocPanel() = AbsurdPanel("Documentation", "The code IS the documentation. Read it.")
@Composable
fun FeedbackPanel() = AbsurdPanel("Feedback", "Button intentionally left non-functional for UX testing.")
@Composable
fun SalesPanel() = AbsurdPanel("Sales", "Would you like to upgrade your 56k modem to 10Gbps?")
@Composable
fun InfraPanel() = AbsurdPanel("Infrastructure", "Server rack is currently held together by duct tape and hope.")
@Composable
fun DatabasePanel() = AbsurdPanel("Database", "Single 4GB Excel file on a shared drive.")
@Composable
fun ApiDocsPanel() = AbsurdPanel("API Docs", "Endpoint '/' returns 418 I'm a teapot.")
@Composable
fun TelemetryPanel() = AbsurdPanel("Telemetry", "You have blinked 14 times since opening this app.")
@Composable
fun VoidPanel() = Box(Modifier.fillMaxSize().background(Color.Black))
@Composable
fun ChaosPanel() {
    val isIpv7 = GlobalAppState.ipv7Mode.value
    var color by remember { mutableStateOf(Color.White) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(50)
            color = Color(Random.nextInt())
        }
    }
    Box(Modifier.fillMaxSize().background(color), contentAlignment = Alignment.Center) {
        Text("SYSTEM COLLAPSE", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}
@Composable
fun QuantumCatPanel() {
    var state by remember { mutableStateOf("Superposition") }
    LaunchedEffect(Unit) {
        while(true) {
            delay(1000)
            state = if(Random.nextBoolean()) "Alive" else "Dead"
        }
    }
    AbsurdPanel("Quantum Cat", "Status: $state")
}
