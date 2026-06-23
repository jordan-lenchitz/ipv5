package net.notipv6.ipv5

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

fun sanitizeEsotericText(input: String): String {
    val lowercaseInput = input.lowercase()
    val punctuation = setOf(
        ',', '!', '?', ':', ';', '-', '"', '\'', '/', '(', ')', '[', ']', 
        '*', '+', '_', '%', '<', '>', '=', '&', '#', '@', '\\', '{', '}', 
        '~', '|', '^', '`', '$'
    )
    val sanitized = buildString {
        for (i in lowercaseInput.indices) {
            val c = lowercaseInput[i]
            if (c == '.') {
                // check if flanked by either letters or digits to preserve ip addresses decimals and uris / urls
                val prevIsWordChar = i > 0 && lowercaseInput[i - 1].isLetterOrDigit()
                val nextIsWordChar = i < lowercaseInput.length - 1 && lowercaseInput[i + 1].isLetterOrDigit()
                if (prevIsWordChar && nextIsWordChar) {
                    append(c)
                } else {
                    append(' ') 
                }
            } else if (c in punctuation) {
                append(' ')
            } else {
                append(c)
            }
        }
    }
    return sanitized.replace(Regex("\\s+"), " ").trim()
}

@Composable
private fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: androidx.compose.ui.unit.TextUnit = androidx.compose.ui.unit.TextUnit.Unspecified,
    fontStyle: androidx.compose.ui.text.font.FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: androidx.compose.ui.unit.TextUnit = androidx.compose.ui.unit.TextUnit.Unspecified,
    textDecoration: androidx.compose.ui.text.style.TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: androidx.compose.ui.unit.TextUnit = androidx.compose.ui.unit.TextUnit.Unspecified,
    overflow: androidx.compose.ui.text.style.TextOverflow = androidx.compose.ui.text.style.TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (androidx.compose.ui.text.TextLayoutResult) -> Unit = {},
    style: androidx.compose.ui.text.TextStyle = LocalTextStyle.current
) {
    androidx.compose.material.Text(
        text = sanitizeEsotericText(text),
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

class InteractiveFeature(
    name: String,
    description: String,
    val content: @Composable () -> Unit
) {
    val name: String = sanitizeEsotericText(name)
    val description: String = sanitizeEsotericText(description)
}

object MoreFeaturesInteractive {
    val list = listOf(
        InteractiveFeature(
            "DNS Roulette",
            "A spinning roulette wheel of vintage domain names. Click SPIN to resolve."
        ) { DnsRoulettePlayground() },
        InteractiveFeature(
            "Quantum Packet Loss",
            "Fire packets through a quantum beam-splitter to observe observation states."
        ) { QuantumPacketLossPlayground() },
        InteractiveFeature(
            "Blockchain ARP",
            "Solve ARP requests block-by-block using simulated hashing mining power."
        ) { BlockchainArpPlayground() },
        InteractiveFeature(
            "UDP Handshake",
            "Try to shake hands with a slippery, uncooperative receiver."
        ) { UdpHandshakePlayground() },
        InteractiveFeature(
            "TCP Window Breaker",
            "Dial the TCP Window Size to the maximum. Alarms and glass cracking guaranteed."
        ) { TcpWindowBreakerPlayground() },
        InteractiveFeature(
            "HTTP/0.9 Downgrade",
            "Downgrade modern URLs into a flickering pre-CSS 1996 green screen terminal."
        ) { Http09DowngradePlayground() },
        InteractiveFeature(
            "BGP Roulette",
            "Advertise loopbacks to AS1. Hijack the global vector routing map."
        ) { BgpRoulettePlayground() },
        InteractiveFeature(
            "ICMP Scream",
            "Hold down to scream bytes onto the network. Audio equalizer visualized."
        ) { IcmpScreamPlayground() },
        InteractiveFeature(
            "Subnet Mask Gen",
            "Slice an IP block into chaotic ranges with the physical Subnet Slicer."
        ) { SubnetMaskGeneratorPlayground() },
        InteractiveFeature(
            "WiFi De-auth",
            "Sweep the radar for mock SSIDs and blast de-authentication signals."
        ) { WifiDeauthenticatorPlayground() },
        InteractiveFeature(
            "Localhost LB",
            "Balance packet weights between localhost and 0.0.0.0 on a physical scale."
        ) { LocalhostLoadBalancerPlayground() },
        InteractiveFeature(
            "IPv4 Exhaustion",
            "An hourglass of blue IP sand. Request leases until lockdown is triggered."
        ) { Ipv4ExhaustionSimulatorPlayground() },
        InteractiveFeature(
            "IPv5 Literal",
            "Solve the lock combination code to open the vault and reveal the holy 5.5.5.5."
        ) { Ipv5LiteralPlayground() },
        InteractiveFeature(
            "SSL/TLS Downgrader",
            "Corrode a golden padlock with time/acid to reveal low-security leakage."
        ) { SslTlsDowngraderPlayground() },
        InteractiveFeature(
            "MAC Address Randomizer",
            "Spin hex octet reels on a retro network slot machine for jackpots."
        ) { MacAddressRandomizerPlayground() },
        InteractiveFeature(
            "Fragmentation Max",
            "Feed text into a mechanical document shredder to spawn character packets."
        ) { PacketFragmentationMaximizerPlayground() },
        InteractiveFeature(
            "Traceroute Visualizer",
            "Sweep sonar radar to trace a submarine packet route into the open ocean."
        ) { TracerouteVisualizerPlayground() },
        InteractiveFeature(
            "Port Knocker",
            "Knock on the brass door knocker in the exact port sequence to unlock."
        ) { PortKnockerPlayground() },
        InteractiveFeature(
            "DHCP Rejector",
            "Whack connecting devices back into their ethernet ports with DHCPNACK."
        ) { DhcpRejectorPlayground() },
        InteractiveFeature(
            "Ping of Life",
            "Defibrillate a flatlining router and shock its heartbeat monitor to life."
        ) { PingOfLifePlayground() },
        InteractiveFeature(
            "Cloud Latency Injector",
            "Generate thick storm clouds that physically block packet traffic roads."
        ) { CloudLatencyInjectorPlayground() },
        InteractiveFeature(
            "Ethernet over DNS",
            "Drill tunnels beneath a giant DNS root tree to run fiber-optic cables."
        ) { EthernetOverDnsPlayground() },
        InteractiveFeature(
            "Bluetooth LE Web",
            "Download low-res images at 1KB/s while microwave radiation interrupts."
        ) { BluetoothLeWebServerPlayground() },
        InteractiveFeature(
            "VPN to Null",
            "Drag packets or drop payloads into a swirling, high-security black hole."
        ) { VpnToNullPlayground() },
        InteractiveFeature(
            "NAT Transversal",
            "Hold down a physical virtual router reset button to build up steam gauges."
        ) { NatTransversalPlayground() },
        InteractiveFeature(
            "SYN Flood Self-Defense",
            "Deflect incoming red SYN missiles or fire self-clogging torrents back."
        ) { SynFloodSelfDefensePlayground() },
        InteractiveFeature(
            "MTU Path Discovery",
            "Control a grumpy gatekeeper that bounces large packets with custom rejections."
        ) { MtuPathDiscoveryDenierPlayground() },
        InteractiveFeature(
            "BGP Hijacker",
            "Spin a wooden steering wheel to pilot a pirate ship to the island of 8.8.8.8."
        ) { BgpHijackerPlayground() },
        InteractiveFeature(
            "WEP Encryption Enforcer",
            "Encrypt messages by spinning a physical concentric cypher password wheel."
        ) { WepEncryptionEnforcerPlayground() },
        InteractiveFeature(
            "IP-over-Avian",
            "Saddle a pigeon with scrolls and launch it over hills, dodging hawks."
        ) { IpOverAvianCarriersSimulatorPlayground() },
        InteractiveFeature(
            "TCP Keep-Alive Spammer",
            "Rapid-reply to an extremely needy chatbot server before it cries."
        ) { TcpKeepAliveSpammerPlayground() },
        InteractiveFeature(
            "Proxy Chain Loop",
            "Roll packet marbles through pipe loops only to end up back where they started."
        ) { ProxyChainLoopPlayground() },
        InteractiveFeature(
            "DNSSEC Invalidator",
            "Scribble and smudge ink over a digital certificate's key to fail security."
        ) { DnssecInvalidatorPlayground() },
        InteractiveFeature(
            "QoS Minimizer",
            "Drag useful packets to trash while letting Cat Memes through the VIP rope."
        ) { QosMinimizerPlayground() },
        InteractiveFeature(
            "SNMP Public Stringer",
            "Unlock secure routers instantly by sliding a key labeled 'public' inside."
        ) { SnmpPublicCommunityStringerPlayground() },
        InteractiveFeature(
            "IPv6 to IPv4 Translator",
            "Stuff long IPv6 lines into a hydraulic press to flatten them into 192.168.1.1."
        ) { Ipv6ToIpv4TranslatorPlayground() }
    )
}

// -------------------------------------------------------------
// 1. DNS Roulette
// -------------------------------------------------------------
@Composable
fun DnsRoulettePlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var selectedDomain by remember { mutableStateOf("Ready to Spin") }
    var angle by remember { mutableStateOf(0f) }
    var spinning by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .rotate(angle)
                .background(textC.copy(alpha = 0.2f), shape = CircleShape)
                .border(2.dp, textC, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerOffset = androidx.compose.ui.geometry.Offset(size.width / 2f, size.height / 2f)
                val radius = size.minDimension / 2f
                drawCircle(color = textC, radius = 6f, center = centerOffset)
            }
            Text("🎰", fontSize = 48.sp)
        }
        Spacer(Modifier.height(16.dp))
        Text(selectedDomain, style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold, color = textC, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (!spinning) {
                    spinning = true
                    scope.launch {
                        val turns = Random.nextInt(10, 20) * 360f
                        val randomStop = Random.nextInt(0, 360)
                        val totalAngle = turns + randomStop
                        
                        var current = 0f
                        while (current < totalAngle) {
                            val remaining = totalAngle - current
                            val step = (remaining / 15f).coerceAtLeast(2f)
                            current += step
                            angle = current % 360f
                            delay(16)
                        }
                        selectedDomain = MoreFeatures.dnsRoulette("example.com")
                        spinning = false
                    }
                }
            },
            enabled = !spinning,
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text(if (spinning) "Spinning..." else "SPIN ROUTER", fontWeight = FontWeight.Bold)
        }
    }
}

// -------------------------------------------------------------
// 2. Quantum Packet Loss
// -------------------------------------------------------------
@Composable
fun QuantumPacketLossPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var state by remember { mutableStateOf("Idle: Load a quantum payload") }
    var animatedProgress by remember { mutableStateOf(0f) }
    var firing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(modifier = Modifier.fillMaxWidth().height(100.dp).border(1.dp, textC.copy(alpha = 0.5f)), contentAlignment = Alignment.Center) {
            if (firing) {
                Box(
                    modifier = Modifier
                        .offset(x = (-100 + (animatedProgress * 200)).dp)
                        .size(16.dp)
                        .background(textC, shape = CircleShape)
                )
            }
            Text("⚡ Beam Splitter ⚡", color = textC.copy(alpha = 0.4f), style = MaterialTheme.typography.overline)
        }
        Spacer(Modifier.height(16.dp))
        Text(state, color = textC, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (!firing) {
                    firing = true
                    scope.launch {
                        state = "Firing packet through superposition field..."
                        animatedProgress = 0f
                        repeat(50) {
                            animatedProgress += 0.02f
                            delay(20)
                        }
                        val packet = MoreFeatures.quantumPacketLoss("DATA_SEGMENT_#05")
                        state = if (packet == null) {
                            "COLLAPSED: Wavefunction observed! Packet dropped into vacuum."
                        } else {
                            "RETAINED: Packet successfully bypassed observer. Payload intact."
                        }
                        firing = false
                    }
                }
            },
            enabled = !firing,
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Fire Quantum Packet")
        }
    }
}

// -------------------------------------------------------------
// 3. Blockchain ARP
// -------------------------------------------------------------
@Composable
fun BlockchainArpPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var blockByBlock by remember { mutableStateOf("Ready to mine block...") }
    var progress by remember { mutableStateOf(0f) }
    var minerPower by remember { mutableStateOf(50f) }
    var mining by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Block Height: #993,124", color = textC, fontWeight = FontWeight.Bold)
        Text("ARP Network Ledger", style = MaterialTheme.typography.caption, color = textC.copy(alpha = 0.6f))
        Spacer(Modifier.height(8.dp))
        Text("Miner Power: ${minerPower.toInt()} kW", color = textC)
        Slider(
            value = minerPower,
            onValueChange = { if (!mining) minerPower = it },
            valueRange = 10f..500f,
            colors = SliderDefaults.colors(thumbColor = textC, activeTrackColor = textC)
        )
        Spacer(Modifier.height(16.dp))
        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth(), color = textC)
        Spacer(Modifier.height(8.dp))
        Card(backgroundColor = textC.copy(alpha = 0.1f), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Text(blockByBlock, modifier = Modifier.padding(12.dp), color = textC, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                mining = true
                scope.launch {
                    val duration = (50000 / minerPower).toLong().coerceIn(500, 4000)
                    progress = 0f
                    val steps = listOf("Hasing block header...", "Checking Nonce: 0x932F...", "Evaluating proof-of-work...", "Propagating ARP entry to miners...")
                    for (step in steps) {
                        blockByBlock = step
                        delay(duration / steps.size)
                        progress += 1f / steps.size
                    }
                    progress = 1f
                    blockByBlock = "RESOLVED: " + MoreFeatures.blockchainArp() + " @ MAC 4E:54:99"
                    mining = false
                }
            },
            enabled = !mining,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Mine MAC Block")
        }
    }
}

// -------------------------------------------------------------
// 4. UDP Handshake
// -------------------------------------------------------------
@Composable
fun UdpHandshakePlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var message by remember { mutableStateOf("Try to catch the hand to shake!") }
    var shakes by remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .border(1.dp, textC.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .offset(x = offsetX.dp, y = offsetY.dp)
                    .size(48.dp)
                    .background(textC, shape = CircleShape)
                    .clickable {
                        shakes++
                        message = "Connected! Shake count: $shakes. But did they receive it? " + MoreFeatures.udpHandshake()
                        offsetX = Random.nextInt(-80, 80).toFloat()
                        offsetY = Random.nextInt(-40, 40).toFloat()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("🤝", fontSize = 24.sp)
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(message, color = textC, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                offsetX = Random.nextInt(-80, 80).toFloat()
                offsetY = Random.nextInt(-40, 40).toFloat()
                message = "Hand slipped away! UDP is connectionless anyway."
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC.copy(alpha = 0.5f), contentColor = textC)
        ) {
            Text("Chase Hand")
        }
    }
}

// -------------------------------------------------------------
// 5. TCP Window Breaker
// -------------------------------------------------------------
@Composable
fun TcpWindowBreakerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var windowSize by remember { mutableStateOf(50f) }
    val scope = rememberCoroutineScope()
    var flash by remember { mutableStateOf(false) }

    val cracked = windowSize > 95f

    LaunchedEffect(cracked) {
        if (cracked) {
            while (true) {
                flash = !flash
                delay(200)
            }
        } else {
            flash = false
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(if (flash) Color.Red else textC.copy(alpha = 0.1f))
                .border(2.dp, if (cracked) Color.Red else textC),
            contentAlignment = Alignment.Center
        ) {
            if (cracked) {
                Text("⚠️ WARNING: WINDOW CRITICAL ⚠️\nGLASS EXPLOSION IMINENT", color = if (flash) Color.White else Color.Yellow, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            } else {
                Text("TCP Frame Buffer Window", color = textC, style = MaterialTheme.typography.overline)
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("Window Size Slider: ${(windowSize * 21474836.47).toLong()} bytes", color = textC)
        Slider(
            value = windowSize,
            onValueChange = { windowSize = it },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(thumbColor = if (cracked) Color.Red else textC, activeTrackColor = textC)
        )
        if (cracked) {
            Text("STATUS: " + MoreFeatures.tcpWindowBreaker() + " (BREAK MAXIMUM!)", color = Color.Red, fontWeight = FontWeight.Bold)
        }
    }
}

// -------------------------------------------------------------
// 6. HTTP/0.9 Downgrade
// -------------------------------------------------------------
@Composable
fun Http09DowngradePlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var url by remember { mutableStateOf("https://openai.com") }
    var terminalOutput by remember { mutableStateOf("Terminal idle. Ready to downgrade.") }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        TextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Enter modern URL", color = textC) },
            colors = TextFieldDefaults.textFieldColors(textColor = textC, focusedLabelColor = textC),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                scope.launch {
                    terminalOutput = "HTTP/1.1 -> HTTP/0.9 Downgrade process initiated...\n"
                    delay(500)
                    terminalOutput += "Sending raw header: " + MoreFeatures.http09Downgrade(url) + "\n"
                    delay(500)
                    terminalOutput += "Receiving pre-CSS response streams:\n"
                    delay(800)
                    terminalOutput += "<html>\n<header><title>Welcome to the 90s</title></header>\n"
                    terminalOutput += "<body>\n<blink><font size=6>Warning: No CSS allowed here!</font></blink>\n"
                    terminalOutput += "Resolved via pure, raw HTTP/0.9. Real-time graphics are prohibited. Have a nice dial-up session.\n"
                    terminalOutput += "</body>\n</html>"
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Downgrade Now")
        }
        Spacer(Modifier.height(8.dp))
        Card(
            backgroundColor = Color.Black,
            modifier = Modifier.fillMaxWidth().height(150.dp).border(1.dp, textC)
        ) {
            Box(modifier = Modifier.verticalScroll(rememberScrollState()).padding(8.dp)) {
                Text(terminalOutput, color = Color.Green, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
            }
        }
    }
}

// -------------------------------------------------------------
// 7. BGP Roulette
// -------------------------------------------------------------
@Composable
fun BgpRoulettePlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var mapLog by remember { mutableStateOf("All global AS coordinates aligned. Click to trigger hijacking.") }
    var activeReroutes by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Canvas(modifier = Modifier.fillMaxWidth().height(100.dp).border(1.dp, textC.copy(alpha = 0.3f))) {
            drawCircle(textC, radius = 5f, center = center)
            if (activeReroutes > 0) {
                repeat(activeReroutes) {
                    drawLine(
                        color = Color.Red,
                        start = center,
                        end = androidx.compose.ui.geometry.Offset(Random.nextFloat() * size.width, Random.nextFloat() * size.height),
                        strokeWidth = 3f
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(mapLog, color = textC, textAlign = TextAlign.Center)
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                scope.launch {
                    activeReroutes = 5
                    mapLog = "ADVERTISING LOOPBACK: " + MoreFeatures.bgpRoulette() + "\n"
                    delay(600)
                    activeReroutes = 15
                    mapLog += "Panic triggered at AS701 (Verizon). Traffic redirected.\n"
                    delay(800)
                    activeReroutes = 35
                    mapLog += "Global path collapsed. BGP Roulette jackpot achieved."
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Advertise 127.0.0.1 to AS1")
        }
    }
}

// -------------------------------------------------------------
// 8. ICMP Scream
// -------------------------------------------------------------
@Composable
fun IcmpScreamPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var screaming by remember { mutableStateOf(false) }
    var screamIntensity = remember { mutableStateListOf(2.dp, 5.dp, 8.dp, 3.dp, 10.dp, 4.dp) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(screaming) {
        if (screaming) {
            while (screaming) {
                screamIntensity.clear()
                repeat(8) {
                    screamIntensity.add(Random.nextInt(5, 50).dp)
                }
                delay(80)
            }
        } else {
            screamIntensity.clear()
            repeat(8) {
                screamIntensity.add(2.dp)
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(
            modifier = Modifier.height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            screamIntensity.forEach { height ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(10.dp)
                        .height(height)
                        .background(textC, shape = RoundedCornerShape(2.dp))
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(
            if (screaming) "🔊 SCREAMING 65535 BYTES OF NOISE!" else "Silence... Tap button to scream onto net",
            color = textC,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                screaming = !screaming
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text(if (screaming) "STOP SCREAMING" else "SCREAM INTO NET")
        }
    }
}

// -------------------------------------------------------------
// 9. Subnet Mask Gen
// -------------------------------------------------------------
@Composable
fun SubnetMaskGeneratorPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var sliceOffset by remember { mutableStateOf(50f) }
    var resultText by remember { mutableStateOf("Drag slider to slice.") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(modifier = Modifier.fillMaxWidth().height(60.dp).border(1.dp, textC.copy(alpha = 0.4f))) {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                Text("192.168.1", modifier = Modifier.weight(1f).padding(start = 12.dp), color = textC, style = MaterialTheme.typography.h6)
                Box(modifier = Modifier.width(3.dp).fillMaxHeight().background(Color.Red))
                Text(".${(sliceOffset * 2.5).toInt()}", modifier = Modifier.weight(1f).padding(start = 12.dp), color = textC, style = MaterialTheme.typography.h6)
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("Slice Intensity Control", color = textC)
        Slider(
            value = sliceOffset,
            onValueChange = {
                sliceOffset = it
                resultText = "Sliced Subnet Mask: " + MoreFeatures.subnetMaskGenerator() + " (Custom CIDR: /${(it / 3).toInt() + 1})"
            },
            colors = SliderDefaults.colors(thumbColor = textC, activeTrackColor = textC)
        )
        Spacer(Modifier.height(8.dp))
        Text(resultText, color = textC, fontWeight = FontWeight.SemiBold)
    }
}

// -------------------------------------------------------------
// 10. WiFi De-auth
// -------------------------------------------------------------
@Composable
fun WifiDeauthenticatorPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var activeSSID by remember { mutableStateOf("Select mock target above") }
    var deauthed by remember { mutableStateOf(false) }
    var angle by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            angle = (angle + 4f) % 360f
            delay(16)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            listOf("NSA_Surveillance", "Neighbor_Wifi", "FBI_HQ_Net").forEach { ssid ->
                Button(
                    onClick = {
                        activeSSID = ssid
                        deauthed = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (activeSSID == ssid) textC else textC.copy(alpha = 0.2f),
                        contentColor = if (activeSSID == ssid) GlobalAppState.currentBgColor.value else textC
                    )
                ) {
                    Text(ssid, fontSize = 10.sp)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(120.dp)
                .border(2.dp, textC, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(color = textC.copy(alpha = 0.1f))
            }
            Box(
                modifier = Modifier
                    .rotate(angle)
                    .width(2.dp)
                    .fillMaxHeight()
                    .background(textC.copy(alpha = 0.5f))
            )
            Text(if (deauthed) "💥" else "📡", fontSize = 32.sp)
        }
        Spacer(Modifier.height(16.dp))
        Text(
            if (deauthed) "DISCONNECTED: " + MoreFeatures.wifiDeauthenticator() else "Target: $activeSSID",
            color = if (deauthed) Color.Red else textC,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { deauthed = true },
            enabled = activeSSID != "Select mock target above",
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("BLAST DE-AUTH PACKETS")
        }
    }
}

// -------------------------------------------------------------
// 11. Localhost Load Balancer
// -------------------------------------------------------------
@Composable
fun LocalhostLoadBalancerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var balanceAngle by remember { mutableStateOf(0f) }
    var weightLeft by remember { mutableStateOf(0) }
    var weightRight by remember { mutableStateOf(0) }

    LaunchedEffect(weightLeft, weightRight) {
        val diff = weightLeft - weightRight
        balanceAngle = (diff * 5f).coerceIn(-45f, 45f)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Weight Pan: 127.0.0.1 vs 0.0.0.0", color = textC, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(160.dp, 80.dp)
                .rotate(balanceAngle),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawLine(textC, start = androidx.compose.ui.geometry.Offset(0f, size.height / 2), end = androidx.compose.ui.geometry.Offset(size.width, size.height / 2), strokeWidth = 5f)
            }
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.size(50.dp).background(textC.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                    Text("$weightLeft packets", fontSize = 10.sp, color = textC)
                }
                Box(modifier = Modifier.size(50.dp).background(textC.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                    Text("$weightRight packets", fontSize = 10.sp, color = textC)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Button(
                onClick = { weightLeft++ },
                colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
            ) {
                Text("+127.0.0.1")
            }
            Button(
                onClick = { weightRight++ },
                colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
            ) {
                Text("+0.0.0.0")
            }
        }
        Spacer(Modifier.height(8.dp))
        Text("Balancer Active IP: " + MoreFeatures.localhostLoadBalancer(), color = textC, style = MaterialTheme.typography.caption)
    }
}

// -------------------------------------------------------------
// 12. IPv4 Exhaustion Simulator
// -------------------------------------------------------------
@Composable
fun Ipv4ExhaustionSimulatorPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var ipsLeft by remember { mutableStateOf(10) }
    var lockdown by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .border(2.dp, if (lockdown) Color.Red else textC),
            contentAlignment = Alignment.Center
        ) {
            if (lockdown) {
                Text("🔴 EXHAUSTED", color = Color.Red, fontWeight = FontWeight.Bold)
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("⏳ Remaining IPs", color = textC, style = MaterialTheme.typography.caption)
                    Text("$ipsLeft / 10", style = MaterialTheme.typography.h4, color = textC, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (ipsLeft > 1) {
                    ipsLeft--
                } else {
                    ipsLeft = 0
                    lockdown = true
                }
            },
            enabled = !lockdown,
            colors = ButtonDefaults.buttonColors(backgroundColor = if (lockdown) Color.Red else textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Acquire IP Lease")
        }
        if (lockdown) {
            Spacer(Modifier.height(8.dp))
            Text("SYSTEM CRITICAL: " + (MoreFeatures.ipv4ExhaustionSimulator("DATA") ?: "NO MORE ADDRESSES LEFT"), color = Color.Red, fontWeight = FontWeight.Bold)
        }
    }
}

// -------------------------------------------------------------
// 13. IPv5 Literal
// -------------------------------------------------------------
@Composable
fun Ipv5LiteralPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var combination by remember { mutableStateOf(listOf(false, false, false)) }
    var vaultOpened by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Vault Security Lock", color = textC, style = MaterialTheme.typography.overline)
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            combination.forEachIndexed { idx, value ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(40.dp)
                        .border(1.dp, textC)
                        .clickable {
                            val next = combination.toMutableList()
                            next[idx] = !next[idx]
                            combination = next
                            vaultOpened = next.all { it }
                        }
                        .background(if (value) textC else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (value) "🔒" else "🔓", color = if (value) GlobalAppState.currentBgColor.value else textC)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        if (vaultOpened) {
            Text("✨ VAULT CRACKED! ✨", color = Color.Green, fontWeight = FontWeight.Bold)
            Text("The holy literal is: " + MoreFeatures.ipv5Literal(), style = MaterialTheme.typography.h5, color = textC)
        } else {
            Text("Set all locks to ON to open.", color = textC)
        }
    }
}

// -------------------------------------------------------------
// 14. SSL/TLS Downgrader
// -------------------------------------------------------------
@Composable
fun SslTlsDowngraderPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var corrosionFactor by remember { mutableStateOf(0f) }
    val corroded = corrosionFactor > 0.8f

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(if (corroded) Color.Red.copy(alpha = 0.1f) else textC.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
                .border(2.dp, if (corroded) Color.Red else textC, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(if (corroded) "🔓" else "🔒", fontSize = 48.sp)
        }
        Spacer(Modifier.height(8.dp))
        Text("Security Padlock Corrosion Slicer", color = textC)
        Slider(
            value = corrosionFactor,
            onValueChange = { corrosionFactor = it },
            colors = SliderDefaults.colors(thumbColor = if (corroded) Color.Red else textC, activeTrackColor = textC)
        )
        if (corroded) {
            Text("STATUS: " + MoreFeatures.sslTlsDowngrader() + "! Leakage Active.", color = Color.Red, fontWeight = FontWeight.Bold)
        } else {
            Text("Protected by robust 256-bit encryption", color = textC)
        }
    }
}

// -------------------------------------------------------------
// 15. MAC Address Randomizer
// -------------------------------------------------------------
@Composable
fun MacAddressRandomizerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var r1 by remember { mutableStateOf("00") }
    var r2 by remember { mutableStateOf("1A") }
    var r3 by remember { mutableStateOf("4C") }
    var spinning by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            listOf(r1, r2, r3).forEach { reel ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(60.dp, 80.dp)
                        .border(2.dp, textC)
                        .background(textC.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(reel, style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold, color = textC)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (!spinning) {
                    spinning = true
                    scope.launch {
                        repeat(15) {
                            r1 = Random.nextInt(10, 99).toString()
                            r2 = Random.nextInt(10, 99).toString()
                            r3 = Random.nextInt(10, 99).toString()
                            delay(60)
                        }
                        val fullMac = MoreFeatures.macAddressRandomizer()
                        val parts = fullMac.split(":")
                        r1 = parts.getOrElse(1) { "00" }
                        r2 = parts.getOrElse(2) { "00" }
                        r3 = "XX"
                        spinning = false
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("PULL MAC LEVER 🎰")
        }
    }
}

// -------------------------------------------------------------
// 16. Fragmentation Max
// -------------------------------------------------------------
@Composable
fun PacketFragmentationMaximizerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var inputStr by remember { mutableStateOf("HELLO") }
    var fragmentedUnits = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        TextField(
            value = inputStr,
            onValueChange = { inputStr = it },
            label = { Text("Text to Fragmentation Shredder", color = textC) },
            colors = TextFieldDefaults.textFieldColors(textColor = textC, focusedLabelColor = textC),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                fragmentedUnits.clear()
                val chars = MoreFeatures.packetFragmentationMaximizer(inputStr)
                fragmentedUnits.addAll(chars)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("SHRED TEXT PACKET")
        }
        Spacer(Modifier.height(8.dp))
        Text("Fragment Output:", color = textC, style = MaterialTheme.typography.caption)
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp).horizontalScroll(rememberScrollState())) {
            fragmentedUnits.forEach { char ->
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(30.dp)
                        .background(textC.copy(alpha = 0.2f))
                        .border(1.dp, textC),
                    contentAlignment = Alignment.Center
                ) {
                    Text(char, color = textC, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 17. Traceroute Visualizer
// -------------------------------------------------------------
@Composable
fun TracerouteVisualizerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var logList = remember { mutableStateListOf<String>() }
    var tracing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Button(
            onClick = {
                if (!tracing) {
                    tracing = true
                    logList.clear()
                    scope.launch {
                        val steps = listOf(
                            "1. Requesting gateway (0.5ms)",
                            "2. Reaching edge server (4.3ms)",
                            "3. Traversing oceanic fiber (82ms)",
                            "4. Discovered map: " + MoreFeatures.tracerouteVisualizer()
                        )
                        for (step in steps) {
                            logList.add(step)
                            delay(800)
                        }
                        tracing = false
                    }
                }
            },
            enabled = !tracing,
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Launch Traceroute Sonar")
        }
        Spacer(Modifier.height(8.dp))
        Card(
            backgroundColor = textC.copy(alpha = 0.05f),
            modifier = Modifier.fillMaxWidth().height(120.dp).border(1.dp, textC.copy(alpha = 0.3f))
        ) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(logList) { log ->
                    Text(log, color = textC, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 18. Port Knocker
// -------------------------------------------------------------
@Composable
fun PortKnockerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var knocks = remember { mutableStateListOf<Int>() }
    var message by remember { mutableStateOf("Tap the brass knocker to open.") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(textC.copy(alpha = 0.1f), shape = CircleShape)
                .border(2.dp, textC, shape = CircleShape)
                .clickable {
                    val port = listOf(80, 443, 22, 21, 23).random()
                    knocks.add(port)
                    if (knocks.size >= 5) {
                        message = "KNOCK PATTERN RESOLVED: " + MoreFeatures.portKnocker()
                    } else {
                        message = "Knocked on port $port!"
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text("🚪", fontSize = 48.sp)
        }
        Spacer(Modifier.height(16.dp))
        Text(message, color = textC, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                knocks.clear()
                message = "Knock pattern wiped out."
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC.copy(alpha = 0.3f), contentColor = textC)
        ) {
            Text("Wipe Knock Log")
        }
    }
}

// -------------------------------------------------------------
// 19. DHCP Rejector
// -------------------------------------------------------------
@Composable
fun DhcpRejectorPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var hits by remember { mutableStateOf(0) }
    var activeBox by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            activeBox = Random.nextInt(0, 4)
            delay(1500)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Score (NACKS): $hits", color = textC, style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            repeat(4) { idx ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(60.dp)
                        .border(1.dp, textC)
                        .background(if (activeBox == idx) textC else Color.Transparent)
                        .clickable {
                            if (activeBox == idx) {
                                hits++
                                activeBox = -1
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (activeBox == idx) "🔌" else "", fontSize = 24.sp)
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Text("STAMP: " + MoreFeatures.dhcpRejector(), color = textC, style = MaterialTheme.typography.caption)
    }
}

// -------------------------------------------------------------
// 20. Ping of Life
// -------------------------------------------------------------
@Composable
fun PingOfLifePlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var routerHealth by remember { mutableStateOf(0f) }
    val revived = routerHealth > 0.9f

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .border(2.dp, if (revived) Color.Green else textC),
            contentAlignment = Alignment.Center
        ) {
            if (revived) {
                Text("💚 PING ALIVE: Heartbeat stable at 60BPM", color = Color.Green, fontWeight = FontWeight.Bold)
            } else {
                Text("⚡ FLATLINE: 192.168.1.1 is dead", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("Router Defibrillator Energy: ${(routerHealth * 100).toInt()}%", color = textC)
        Slider(
            value = routerHealth,
            onValueChange = { routerHealth = it },
            colors = SliderDefaults.colors(thumbColor = if (revived) Color.Green else textC, activeTrackColor = textC)
        )
        if (revived) {
            Text("PADDLES READY: " + MoreFeatures.pingOfLife(), color = Color.Green, fontWeight = FontWeight.Bold)
        }
    }
}

// -------------------------------------------------------------
// 21. Cloud Latency Injector
// -------------------------------------------------------------
@Composable
fun CloudLatencyInjectorPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var rawMs by remember { mutableStateOf(50f) }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Cloud Density: ${rawMs.toInt()}%", color = textC)
        Slider(
            value = rawMs,
            onValueChange = { rawMs = it },
            valueRange = 0f..500f,
            colors = SliderDefaults.colors(thumbColor = textC, activeTrackColor = textC)
        )
        Spacer(Modifier.height(8.dp))
        Text("Resulting Injected Latency: " + MoreFeatures.cloudLatencyInjector(rawMs.toInt()) + "ms", color = textC, fontWeight = FontWeight.Bold)
    }
}

// -------------------------------------------------------------
// 22. Ethernet over DNS
// -------------------------------------------------------------
@Composable
fun EthernetOverDnsPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var digClicks by remember { mutableStateOf(0) }
    var stateMsg by remember { mutableStateOf("Tap DIG to tunnel cable under DNS Tree roots.") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Dig Log depth: $digClicks feet", color = textC)
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                digClicks += 5
                if (digClicks > 30) {
                    stateMsg = "SUCCESS: " + MoreFeatures.ethernetOverDns() + " @ Depth $digClicks"
                } else {
                    stateMsg = "Digging deeper under DNS tree... Layer: " + listOf("Quartz", "Clay", "Digital Fossil", "MUMPS node").random()
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("🪓 DIG TUNNEL")
        }
        Spacer(Modifier.height(16.dp))
        Text(stateMsg, color = textC, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}

// -------------------------------------------------------------
// 23. Bluetooth LE Web Server
// -------------------------------------------------------------
@Composable
fun BluetoothLeWebServerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var bytesReceived by remember { mutableStateOf(0) }
    var downloading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        LinearProgressIndicator(
            progress = (bytesReceived / 1024f).coerceIn(0f, 1f),
            modifier = Modifier.fillMaxWidth(),
            color = textC
        )
        Spacer(Modifier.height(8.dp))
        Text("Speed: " + MoreFeatures.bluetoothLeWebServer() + " (Received $bytesReceived/1024 bytes)", color = textC)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (!downloading) {
                    downloading = true
                    bytesReceived = 0
                    scope.launch {
                        while (bytesReceived < 1024) {
                            bytesReceived += Random.nextInt(10, 50)
                            delay(100)
                        }
                        bytesReceived = 1024
                        downloading = false
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text(if (downloading) "Syncing BLE..." else "Initiate Download")
        }
    }
}

// -------------------------------------------------------------
// 24. VPN to Null
// -------------------------------------------------------------
@Composable
fun VpnToNullPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var suckedCount by remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.Black, shape = CircleShape)
                .border(2.dp, textC, shape = CircleShape)
                .clickable { suckedCount++ },
            contentAlignment = Alignment.Center
        ) {
            Text("🌀", fontSize = 48.sp)
        }
        Spacer(Modifier.height(16.dp))
        Text("Packets swallowed into /dev/null: $suckedCount", color = textC, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Policy: " + MoreFeatures.vpnToNull(), color = textC, style = MaterialTheme.typography.caption, textAlign = TextAlign.Center)
    }
}

// -------------------------------------------------------------
// 25. NAT Transversal
// -------------------------------------------------------------
@Composable
fun NatTransversalPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var holdDuration by remember { mutableStateOf(0f) }
    var complete by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Physical Router Reset Dial", color = textC)
        Spacer(Modifier.height(8.dp))
        CircularProgressIndicator(progress = holdDuration, modifier = Modifier.size(80.dp), color = textC)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                holdDuration = 1f
                complete = true
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Press Reset")
        }
        if (complete) {
            Spacer(Modifier.height(8.dp))
            Text("INSTRUCTION: " + MoreFeatures.natTransversal(), color = textC, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
    }
}

// -------------------------------------------------------------
// 26. SYN Flood Self-Defense
// -------------------------------------------------------------
@Composable
fun SynFloodSelfDefensePlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var score by remember { mutableStateOf(0) }
    var defenseState by remember { mutableStateOf("Dodge the incoming SYN requests") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Incoming packets deflected: $score", color = textC, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                score += 12
                defenseState = "STATUS: " + MoreFeatures.synFloodSelfDefense()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Deploy Firewall Deflectors")
        }
        Spacer(Modifier.height(8.dp))
        Text(defenseState, color = textC, style = MaterialTheme.typography.caption)
    }
}

// -------------------------------------------------------------
// 27. MTU Path Discovery Denier
// -------------------------------------------------------------
@Composable
fun MtuPathDiscoveryDenierPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var output by remember { mutableStateOf("Large packet approaching gate...") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("👮 MTU Gatekeeper", color = textC, style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(8.dp))
        Text(output, color = textC, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                output = "GATEKEEPER SPEAKS: " + MoreFeatures.mtuPathDiscoveryDenier() + " No passage allowed."
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Shut Gate")
        }
    }
}

// -------------------------------------------------------------
// 28. BGP Hijacker
// -------------------------------------------------------------
@Composable
fun BgpHijackerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var compassAngle by remember { mutableStateOf(0f) }
    var output by remember { mutableStateOf("Set sail for Google DNS island!") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .rotate(compassAngle)
                .border(2.dp, textC, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("☸️", fontSize = 48.sp)
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Button(
                onClick = {
                    compassAngle -= 45f
                    output = "Steered left. " + MoreFeatures.bgpHijacker()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
            ) {
                Text("Port ↩️")
            }
            Button(
                onClick = {
                    compassAngle += 45f
                    output = "Steered right. " + MoreFeatures.bgpHijacker()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
            ) {
                Text("Starboard ↪️")
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(output, color = textC, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}

// -------------------------------------------------------------
// 29. WEP Encryption Enforcer
// -------------------------------------------------------------
@Composable
fun WepEncryptionEnforcerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var value by remember { mutableStateOf("secret") }
    var encrypted by remember { mutableStateOf("Run enforcer to crypt") }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Input message to cipher", color = textC) },
            colors = TextFieldDefaults.textFieldColors(textColor = textC, focusedLabelColor = textC),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                encrypted = "WEP_CIPHER_VALUE: [ " + MoreFeatures.wepEncryptionEnforcer() + " ] Applied encryption key."
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Enforce WEP Shield")
        }
        Spacer(Modifier.height(8.dp))
        Text(encrypted, color = textC, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.caption)
    }
}

// -------------------------------------------------------------
// 30. IP-over-Avian
// -------------------------------------------------------------
@Composable
fun IpOverAvianCarriersSimulatorPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var pstate by remember { mutableStateOf("Pigeon in coop. Ready to fly.") }
    var launched by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(textC.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(if (launched) "🕊️ 💨" else "🕊️ 🏠", fontSize = 32.sp)
        }
        Spacer(Modifier.height(16.dp))
        Text(pstate, color = textC, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                launched = true
                scope.launch {
                    pstate = "Pigeon took off with packet scroll..."
                    delay(1200)
                    pstate = "Dodged hawk in cloud bank..."
                    delay(1200)
                    pstate = MoreFeatures.ipOverAvianCarriersSimulator()
                    launched = false
                }
            },
            enabled = !launched,
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("LAUNCH AVIAN PACKET")
        }
    }
}

// -------------------------------------------------------------
// 31. TCP Keep-Alive Spammer
// -------------------------------------------------------------
@Composable
fun TcpKeepAliveSpammerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var countdown by remember { mutableStateOf(10) }
    var alertMsg by remember { mutableStateOf("Are you there?") }
    var alive by remember { mutableStateOf(true) }

    LaunchedEffect(alive) {
        if (alive) {
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            alertMsg = "Server disconnected: Spammer failed keep-alive!"
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Keep-Alive countdown: $countdown seconds", color = textC, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("SERVER PROMPT: $alertMsg", color = textC)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                countdown = 10
                alertMsg = MoreFeatures.tcpKeepAliveSpammer()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("PING ALIVE SPOOF")
        }
    }
}

// -------------------------------------------------------------
// 32. Proxy Chain Loop
// -------------------------------------------------------------
@Composable
fun ProxyChainLoopPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var activeProxy by remember { mutableStateOf("You") }
    var looping by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            listOf("Proxy 1", "Proxy 2", "Proxy 3", "You").forEach { px ->
                Box(
                    modifier = Modifier
                        .size(60.dp, 40.dp)
                        .border(1.dp, textC)
                        .background(if (activeProxy == px) textC else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(px, color = if (activeProxy == px) GlobalAppState.currentBgColor.value else textC, fontSize = 10.sp)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (!looping) {
                    looping = true
                    scope.launch {
                        val sequence = listOf("Proxy 1", "Proxy 2", "Proxy 3", "You")
                        for (node in sequence) {
                            activeProxy = node
                            delay(600)
                        }
                        looping = false
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Traverse Proxy Chain")
        }
        Spacer(Modifier.height(8.dp))
        Text("Routing log: " + MoreFeatures.proxyChainLoop(), color = textC, style = MaterialTheme.typography.caption)
    }
}

// -------------------------------------------------------------
// 33. DNSSEC Invalidator
// -------------------------------------------------------------
@Composable
fun DnssecInvalidatorPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var certIntegrity by remember { mutableStateOf(100) }
    var message by remember { mutableStateOf("DNSSEC Signatures are pristine and signed.") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .border(2.dp, if (certIntegrity < 50) Color.Red else textC),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Certificate Security Health", color = textC, style = MaterialTheme.typography.caption)
                Text("$certIntegrity%", style = MaterialTheme.typography.h6, color = if (certIntegrity < 50) Color.Red else textC)
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                certIntegrity = (certIntegrity - 20).coerceAtLeast(0)
                if (certIntegrity == 0) {
                    message = "WARNING: " + MoreFeatures.dnssecInvalidator() + " Validation failed."
                } else {
                    message = "Ink smeared. Signature corrupted."
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text("Smudge signature ink")
        }
        Spacer(Modifier.height(8.dp))
        Text(message, color = textC, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
    }
}

// -------------------------------------------------------------
// 34. QoS Minimizer
// -------------------------------------------------------------
@Composable
fun QosMinimizerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var selectedQueue by remember { mutableStateOf("Select Packet Priority Slicer") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            listOf("Urgent Wire", "Spam Rig", "Cat Meme").forEach { q ->
                Button(
                    onClick = {
                        selectedQueue = q + " rerouted to: " + MoreFeatures.qosMinimizer()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = textC.copy(alpha = 0.2f), contentColor = textC)
                ) {
                    Text(q, fontSize = 10.sp)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(selectedQueue, color = textC, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    }
}

// -------------------------------------------------------------
// 35. SNMP Public Community Stringer
// -------------------------------------------------------------
@Composable
fun SnmpPublicCommunityStringerPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var targetField by remember { mutableStateOf("") }
    var unlocked by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        TextField(
            value = targetField,
            onValueChange = {
                targetField = it
                if (it == MoreFeatures.snmpPublicCommunityStringer()) {
                    unlocked = true
                }
            },
            label = { Text("Enter community string key", color = textC) },
            colors = TextFieldDefaults.textFieldColors(textColor = textC, focusedLabelColor = textC),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(if (unlocked) Color.Green.copy(alpha = 0.1f) else textC.copy(alpha = 0.1f))
                .border(2.dp, if (unlocked) Color.Green else textC),
            contentAlignment = Alignment.Center
        ) {
            Text(if (unlocked) "🔓 ROUTER OPEN" else "🔒 ROUTER LOCKED", fontSize = 12.sp, color = textC, textAlign = TextAlign.Center)
        }
        Spacer(Modifier.height(8.dp))
        Text("Tip: Default key is 'public'", color = textC, style = MaterialTheme.typography.caption)
    }
}

// -------------------------------------------------------------
// 36. IPv6 to IPv4 Translator
// -------------------------------------------------------------
@Composable
fun Ipv6ToIpv4TranslatorPlayground() {
    val textC = GlobalAppState.currentTextColor.value
    var ipv6Input by remember { mutableStateOf("2001:db8::8a2e:370:7334") }
    var pressedResult by remember { mutableStateOf("Press hydraulic leverage to compress") }
    var progressFactor by remember { mutableStateOf(0f) }
    var crushing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        TextField(
            value = ipv6Input,
            onValueChange = { ipv6Input = it },
            label = { Text("128-bit IPv6 Source Address", color = textC) },
            colors = TextFieldDefaults.textFieldColors(textColor = textC, focusedLabelColor = textC),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        LinearProgressIndicator(progress = progressFactor, modifier = Modifier.fillMaxWidth(), color = textC)
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                if (!crushing) {
                    crushing = true
                    scope.launch {
                        progressFactor = 0f
                        repeat(20) {
                            progressFactor += 0.05f
                            delay(50)
                        }
                        progressFactor = 1f
                        pressedResult = "CRUSHED OUT: " + MoreFeatures.ipv6ToIpv4Translator(ipv6Input)
                        crushing = false
                    }
                }
            },
            enabled = !crushing,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(backgroundColor = textC, contentColor = GlobalAppState.currentBgColor.value)
        ) {
            Text(if (crushing) "CRUSHING..." else "CRUSH ADDR HYDRAULICALLY")
        }
        Spacer(Modifier.height(8.dp))
        Text(pressedResult, color = textC, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.caption)
    }
}
