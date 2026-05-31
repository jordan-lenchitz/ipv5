package net.notipv6.ipv5

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
fun ChaoticPanel(
    title: String,
    content: @Composable () -> Unit
) {
    val font = remember { GlobalAppState.getRandomFont() }
    val isAccessible = GlobalAppState.accessibilityMode.value
    val isIpv7 = GlobalAppState.ipv7Mode.value
    
    // Use session-persistent colors instead of local remember
    val bgColor = if (isAccessible) Color.White else GlobalAppState.currentBgColor.value
    val textColor = if (isAccessible) Color.Black else GlobalAppState.currentTextColor.value

    Column(
        modifier = Modifier.fillMaxSize().background(color = bgColor).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(title.lowercase(), style = MaterialTheme.typography.h4, fontFamily = font, color = textColor)
        Spacer(Modifier.height(24.dp))
        
        CompositionLocalProvider(
            LocalTextStyle provides LocalTextStyle.current.copy(fontFamily = font, color = textColor),
            LocalContentColor provides textColor
        ) {
            content()
        }
        
        if (isIpv7) {
            Spacer(Modifier.height(24.dp))
            Text("chaos active".lowercase(), color = textColor, fontWeight = FontWeight.ExtraBold, fontFamily = font)
        }
    }
}

@Composable
fun DevOpsPanel() {
    val progress = remember { mutableStateOf(0.8f) }
    
    LaunchedEffect(Unit) {
        while(true) {
            delay(100)
            progress.value -= 0.01f
            if (progress.value <= 0f) progress.value = 1.0f
        }
    }

    ChaoticPanel(title = "devops panel") {
        Column {
            Text("deploying to production on a friday...".lowercase())
            Spacer(Modifier.height(24.dp))
            
            Text("build progress (in reverse): ${(progress.value * 100).toInt()}%".lowercase())
            LinearProgressIndicator(progress = progress.value, modifier = Modifier.fillMaxWidth())
            
            Spacer(Modifier.height(16.dp))
            Text("recent logs:".lowercase(), fontWeight = FontWeight.Bold)
            Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    item { Text("error: production db deleted (whoops)".lowercase(), fontFamily = FontFamily.Monospace) }
                    item { Text("info: bypassing unit tests for 'speed'".lowercase(), fontFamily = FontFamily.Monospace) }
                    item { Text("debug: why is the server screaming?".lowercase(), fontFamily = FontFamily.Monospace) }
                    item { Text("fatal: coffee machine offline".lowercase(), fontFamily = FontFamily.Monospace) }
                }
            }
        }
    }
}

@Composable
fun FinOpsPanel() {
    val burnRate = remember { mutableStateOf(4200.0) }
    val totalBurned = remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        while(true) {
            delay(10)
            totalBurned.value += (burnRate.value / 100.0)
            burnRate.value += Random.nextDouble(-10.0, 50.0)
        }
    }

    ChaoticPanel(title = "finops panel") {
        Column {
            Text("current money burn rate: $${burnRate.value.toInt()}/sec".lowercase(), style = MaterialTheme.typography.h5, color = Color.Red)
            Text("total aws bill (estimated): $${totalBurned.value.toInt()}".lowercase(), style = MaterialTheme.typography.h3, fontWeight = FontWeight.Bold)
            
            Spacer(Modifier.height(32.dp))
            Button(onClick = { burnRate.value *= 2 }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                Text("scaling for growth (x2 burn)".lowercase(), color = Color.White)
            }
        }
    }
}

@Composable
fun SplunkPanel() {
    val logs = remember { mutableStateListOf<String>() }
    
    LaunchedEffect(Unit) {
        while(true) {
            delay(Random.nextLong(500, 2000))
            val log = listOf(
                "user breathed on keyboard",
                "packet felt lonely and died",
                "mainframe reached sentience (briefly)",
                "admin spilled tea on rack 4",
                "gravity reversed in data center",
                "bit flipped by cosmic ray",
                "server decided it's a toaster now"
            ).random()
            logs.add(0, "[${currentTimeMillis()}] $log".lowercase())
            if (logs.size > 50) logs.removeAt(50)
        }
    }

    ChaoticPanel(title = "splunk panel") {
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
    ChaoticPanel(title = "grafana dashboard") {
        Column {
            Text("metric: customer satisfaction".lowercase())
            Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(color = Color.Red)) {
                Text("📉", modifier = Modifier.align(Alignment.Center), fontSize = 48.sp)
            }
            
            Spacer(Modifier.height(16.dp))
            Text("metric: ceo bonus".lowercase())
            Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(color = Color.Green)) {
                Text("📈", modifier = Modifier.align(Alignment.Center), fontSize = 48.sp)
            }
            
            Spacer(Modifier.height(16.dp))
            Text("metric: router temperature".lowercase())
            Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(color = Color.Black)) {
                Text("🔥", modifier = Modifier.align(Alignment.Center), fontSize = 48.sp)
            }
        }
    }
}

@Composable
fun AnsiblePanel() {
    ChaoticPanel(title = "ansible control") {
        Column {
            Text("active playbook: uninstall_everything.yml".lowercase(), fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            
            repeat(5) { i ->
                Card(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()) {
                    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.Red)
                        Spacer(Modifier.width(8.dp))
                        Text("task [delete important database $i] *********".lowercase())
                    }
                }
            }
            
            Button(onClick = { /* run playbook */ }, modifier = Modifier.fillMaxWidth()) {
                Text("force execute (no callbacks)".lowercase())
            }
        }
    }
}

@Composable
fun B2BSaaSPanel() {
    val apiKey = remember { mutableStateOf("") }
    val unlocked = remember { mutableStateOf(false) }

    ChaoticPanel(title = "enterprise b2b saas") {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (!unlocked.value) {
                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(64.dp))
                Text("enterprise api key required".lowercase())
                Spacer(Modifier.height(16.dp))
                TextField(value = apiKey.value, onValueChange = { apiKey.value = it }, label = { Text("enter api key".lowercase()) })
                Spacer(Modifier.height(8.dp))
                Button(onClick = { if (apiKey.value == "api_key") unlocked.value = true }) {
                    Text("authenticate (billed hourly)".lowercase())
                }
            } else {
                Text("welcome, valued enterprise partner".lowercase(), color = Color.Magenta, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))
                Text("ultra-expensive features you will regret purchasing:".lowercase())
                Spacer(Modifier.height(16.dp))
                
                val features = listOf(
                    "manual packet delivery via luxury courier",
                    "golden plated ethernet cables ($50,000/m)",
                    "quantum-entangled support chat (zero latency)",
                    "bespoke ipv5 subnets (hand-crafted)",
                    "priority routing through the ceo's ipad",
                    "blockchain-backed pong",
                    "ai-powered random rebooter"
                )
                
                LazyColumn {
                    items(features) { feature ->
                        Card(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(), elevation = 8.dp) {
                            Text(feature.lowercase(), modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AbsurdPanel(title: String, content: String) {
    ChaoticPanel(title = title) {
        Text(content.lowercase())
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
fun VoidPanel() = Box(Modifier.fillMaxSize().background(color = Color.Black))
@Composable
fun ChaosPanel() {
    val color = remember { mutableStateOf(Color.White) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(50)
            color.value = Color(Random.nextInt())
        }
    }
    Box(Modifier.fillMaxSize().background(color = color.value), contentAlignment = Alignment.Center) {
        Text("system collapse".lowercase(), fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun QuantumCatPanel() {
    val state = remember { mutableStateOf("superposition") }
    LaunchedEffect(Unit) {
        while(true) {
            delay(1000)
            state.value = if(Random.nextBoolean()) "alive" else "dead"
        }
    }
    ChaoticPanel(title = "quantum cat") {
        Text("status: ${state.value}".lowercase())
    }
}

