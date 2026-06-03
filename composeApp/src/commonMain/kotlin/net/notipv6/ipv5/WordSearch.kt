package net.notipv6.ipv5

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

enum class Direction(val dx: Int, val dy: Int) {
    RIGHT(1, 0), LEFT(-1, 0), DOWN(0, 1), UP(0, -1),
    DR(1, 1), DL(-1, 1), UR(1, -1), UL(-1, -1)
}

data class WordLocation(val word: String, val x: Int, val y: Int, val dir: Direction)

class WordSearchEngine(val size: Int = 16) {
    val grid = Array(size) { CharArray(size) { ' ' } }
    val placedWords = mutableListOf<WordLocation>()

    fun generate(words: List<String>) {
        val sortedWords = words.sortedByDescending { it.length }
        for (word in sortedWords) {
            placeWord(word.lowercase())
        }
        fillRandom()
    }

    private fun placeWord(word: String) {
        val directions = Direction.entries.shuffled()
        val positions = (0 until size).flatMap { x -> (0 until size).map { y -> x to y } }.shuffled()

        for ((x, y) in positions) {
            for (dir in directions) {
                if (canPlace(word, x, y, dir)) {
                    for (i in word.indices) {
                        grid[y + i * dir.dy][x + i * dir.dx] = word[i]
                    }
                    placedWords.add(WordLocation(word, x, y, dir))
                    return
                }
            }
        }
    }

    private fun canPlace(word: String, x: Int, y: Int, dir: Direction): Boolean {
        for (i in word.indices) {
            val nx = x + i * dir.dx
            val ny = y + i * dir.dy
            if (nx !in 0 until size || ny !in 0 until size) return false
            if (grid[ny][nx] != ' ' && grid[ny][nx] != word[i]) return false
        }
        return true
    }

    private fun fillRandom() {
        val chars = ('a'..'z') + ('0'..'9') + listOf('.', '-')
        for (y in 0 until size) {
            for (x in 0 until size) {
                if (grid[y][x] == ' ') {
                    grid[y][x] = chars.random()
                }
            }
        }
    }
}

@Composable
fun WordSearchPanel(onCloseApp: () -> Unit = { exitApp() }) {
    val wordPool = remember {
        listOf(
            // --- Alphanumeric & Network Identifiers ---
            "1.1.1.1", "8.8.8.8", "127.0.0.1", "ipv4", "ipv6", "rfc791", "rfc2616", "rfc418", "rfc1149",
            "404error", "500error", "200ok", "301moved", "0xdeadbeef", "0xcahebabe", "1337h4x0r",
            "port80", "port443", "tcp22", "udp53", "802.11", "cat6", "rs232", "x86_64", "arm64",
            "win32", "posix", "v6.0", "beta2", "build42", "node.js", "sha256", "md5", "bip39",
            // --- HTTP Status Codes: Standard 1xx ---
            "100continue", "101switchingprotocols", "102processing", "103earlyhints",
            // --- HTTP Status Codes: Standard 2xx ---
            "200ok", "201created", "202accepted", "203non-authoritative", "204nocontent",
            "205resetcontent", "206partialcontent", "207multi-status", "208alreadyreported", "226imused",
            // --- HTTP Status Codes: Standard 3xx ---
            "300multiplechoices", "301movedpermanently", "302found", "303seeother", "304notmodified",
            "305useproxy", "306switchproxy", "307temporaryredirect", "308permanentredirect",
            // --- HTTP Status Codes: Standard 4xx ---
            "400badrequest", "401unauthorized", "402paymentrequired", "403forbidden", "404notfound",
            "405methodnotallowed", "406notacceptable", "407proxyauthentication", "408requesttimeout",
            "409conflict", "410gone", "411lengthrequired", "412preconditionfailed", "413contenttoolarge",
            "414uritoolong", "415unsupportedmediatype", "416rangenotsatisfiable", "417expectationfailed",
            "418imateapot", "421misdirectedrequest", "422unprocessablecontent", "423locked",
            "424faileddependency", "425tooearly", "426upgraderequired", "428preconditionrequired",
            "429toomanyrequests", "431requestheadertoolarge", "451unavailablelegal",
            // --- HTTP Status Codes: Standard 5xx ---
            "500internalerror", "501notimplemented", "502badgateway", "503serviceunavailable",
            "504gatewaytimeout", "505httpversionnotsupported", "506variantnegotiates", "507insufficientstorage",
            "508loopdetected", "510notextended", "511networkauthrequired",
            // --- Non-Standard: IIS ---
            "440logintimeout", "449retrywith", "450blockedparental", "451redirect",
            // --- Non-Standard: nginx ---
            "444noresponse", "494requestheadertoolarge", "495sslcerterror", "496sslcertrequired",
            "497httptohttps", "499clientclosed",
            // --- Non-Standard: Cloudflare ---
            "520unknownerror", "521serverisdown", "522connectiontimeout", "523originunreachable",
            "524timeoutoccurred", "525sslhandshakefailed", "526invalidsslcert", "527railgunerror",
            "530originunavailable",
            // --- Non-Standard: AWS ELB ---
            "000goaway", "460clientclosed", "463forwardedfor", "464protomismatch", "561unauthorized",
            // --- Non-Standard: Others ---
            "509bandwidthlimit", "419pageexpired", "420methodfailure", "420enhancecalm",
            "430requestheadertoolarge", "430securityrejection", "530origindnserror", "540temporarilydisabled",
            "783unexpectedtoken", "498invalidtoken", "499tokenrequired", "508resourcelimit",
            "529siteoverloaded", "530sitefrozen", "999requestdenied", "218thisisfine",
            "598readtimeout", "599connecttimeout",
            // --- Core & Lore ---
            "ipv5", "chaos", "quantum", "packet", "subnet", "router", "switch", "network",
            "bridge", "protocol", "octet", "entropy", "logic", "drift", "flux", "buffer",
            "stack", "heap", "node", "link", "port", "dns", "mac", "ping", "trace", "sync",
            "mumps", "miis", "magic", "npr", "global", "pdp11", "pdp15", "vax", "decsystem",
            "meditech", "barnett", "pappalardo", "marble", "holenet", "maybebit", "vibe"
        ).distinct()
    }

    var wordsToFind by remember { mutableStateOf(wordPool.shuffled().take(8)) }
    // IPv5 Jumboframes: Dynamic grid sizing to accommodate excessively long protocol headers
    val gridSize = remember(wordsToFind) {
        (wordsToFind.maxOfOrNull { it.length } ?: 16).coerceAtLeast(16)
    }
    val engine = remember(wordsToFind, gridSize) {
        WordSearchEngine(gridSize).apply { generate(wordsToFind) }
    }

    var selectedStart by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var selectedEnd by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var foundWords by remember { mutableStateOf(setOf<String>()) }
    var showVictoryDialog by remember { mutableStateOf(false) }
    var debugClickCount by remember { mutableStateOf(0) }
    val isAccessible = GlobalAppState.accessibilityMode.value
    val foundColor = Color(0xFF4DB6AC) // Soul-soothing soft teal
    val textColor = if (isAccessible) Color.Black else GlobalAppState.currentTextColor.value
    val font = FontFamily.Default // Sexy Dyslexia Friendly Font (Clean Sans-Serif)

    var gridSizePx by remember { mutableStateOf(IntSize.Zero) }

    ChaoticPanel(title = "word search") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        ) {
            val statusText = if (gridSize > 16) "mtu: $gridSize (jumbo)" else "mtu: 1500"
            Text(
                "find ${wordsToFind.size - foundWords.size} more codes | $statusText".lowercase(),
                style = MaterialTheme.typography.subtitle2,
                color = textColor.copy(alpha = 0.7f),
                fontFamily = font,
                modifier = Modifier.clickable {
                    debugClickCount++
                    if (debugClickCount >= 5) {
                        showVictoryDialog = true
                        debugClickCount = 0
                    }
                }
            )
            
            Spacer(Modifier.height(8.dp))

            // Adaptive UI constants for high-density grids
            val adaptivePadding = when {
                gridSize > 24 -> 0.dp
                gridSize > 20 -> 0.5.dp
                gridSize > 18 -> 1.dp
                else -> 2.dp
            }
            val adaptiveCorner = when {
                gridSize > 24 -> 0.dp
                gridSize > 20 -> 1.dp
                else -> 4.dp
            }
            val baseFontSize = 14
            // More aggressive scaling: base * (target_grid / current_grid)
            val adaptiveFontSize = (baseFontSize.toFloat() * 15f / gridSize.toFloat()).coerceAtLeast(8f).sp

            // The Grid with Robust Drag Support
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .onSizeChanged { gridSizePx = it }
                    .pointerInput(engine, gridSizePx, gridSize) {
                        if (gridSizePx.width > 0 && gridSizePx.height > 0) {
                            val cellWidth = gridSizePx.width.toFloat() / gridSize
                            val cellHeight = gridSizePx.height.toFloat() / gridSize
                            
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val x = (offset.x / cellWidth).toInt().coerceIn(0 until gridSize)
                                    val y = (offset.y / cellHeight).toInt().coerceIn(0 until gridSize)
                                    selectedStart = x to y
                                    selectedEnd = x to y
                                },
                                onDrag = { change, _ ->
                                    val x = (change.position.x / cellWidth).toInt().coerceIn(0 until gridSize)
                                    val y = (change.position.y / cellHeight).toInt().coerceIn(0 until gridSize)
                                    selectedEnd = x to y
                                },
                                onDragEnd = {
                                    if (selectedStart != null && selectedEnd != null) {
                                        checkSelection(selectedStart!!, selectedEnd!!, engine, wordsToFind) { found ->
                                            if (found != null) {
                                                val newFound = foundWords + found
                                                foundWords = newFound
                                                if (newFound.size == wordsToFind.size) {
                                                    showVictoryDialog = true
                                                }
                                            }
                                        }
                                    }
                                    selectedStart = null
                                    selectedEnd = null
                                },
                                onDragCancel = {
                                    selectedStart = null
                                    selectedEnd = null
                                }
                            )
                        }
                    }
            ) {
                Column {
                    for (y in 0 until gridSize) {
                        Row(modifier = Modifier.weight(1f)) {
                            for (x in 0 until gridSize) {
                                val char = engine.grid[y][x]
                                val isPartOfSelection = isCellInCurrentSelection(x, y, selectedStart, selectedEnd)
                                val isPartOfFound = isCellInFoundWord(x, y, engine.placedWords, foundWords)

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(adaptivePadding)
                                        .background(
                                            when {
                                                isPartOfSelection -> Color.Yellow.copy(alpha = 0.7f)
                                                isPartOfFound -> foundColor.copy(alpha = 0.4f)
                                                else -> Color.Transparent
                                            },
                                            RoundedCornerShape(adaptiveCorner)
                                        )
                                        .border(
                                            if (gridSize > 24) 0.1.dp else 0.5.dp, 
                                            if (isPartOfSelection) Color.Yellow else textColor.copy(alpha = 0.1f), 
                                            RoundedCornerShape(adaptiveCorner)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        char.toString(),
                                        style = TextStyle(
                                            textAlign = TextAlign.Center,
                                            platformStyle = PlatformTextStyle(includeFontPadding = false),
                                            lineHeightStyle = LineHeightStyle(
                                                alignment = LineHeightStyle.Alignment.Center,
                                                trim = LineHeightStyle.Trim.Both
                                            )
                                        ),
                                        fontWeight = if (isPartOfFound || isPartOfSelection) FontWeight.ExtraBold else FontWeight.Normal,
                                        fontSize = adaptiveFontSize,
                                        color = when {
                                            isPartOfSelection -> Color.Black
                                            isPartOfFound -> foundColor
                                            else -> textColor
                                        },
                                        fontFamily = font,
                                        maxLines = 1,
                                        softWrap = false,
                                        modifier = Modifier.wrapContentSize(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Word List
            Column(modifier = Modifier.fillMaxWidth()) {
                wordsToFind.chunked(2).forEach { rowWords ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowWords.forEach { word ->
                            val isFound = foundWords.contains(word)
                            Text(
                                word,
                                fontSize = 10.sp,
                                color = if (isFound) foundColor else textColor.copy(alpha = 0.6f),
                                fontFamily = font,
                                style = if (isFound) MaterialTheme.typography.body2.copy(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough)
                                        else MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    wordsToFind = wordPool.shuffled().take(8)
                    foundWords = emptySet()
                    selectedStart = null
                    selectedEnd = null
                    showVictoryDialog = false
                },
                modifier = Modifier.height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isAccessible) Color.White else Color(0xFF6200EE)
                )
            ) {
                Text(
                    "re-scramble".lowercase(), 
                    color = if (isAccessible) Color.Black else Color.White, 
                    fontFamily = font, 
                    fontSize = 12.sp
                )
            }
            
            if (showVictoryDialog) {
                AlertDialog(
                    onDismissRequest = { showVictoryDialog = false },
                    title = { Text("SUCCESS! 👏", fontFamily = font, fontWeight = FontWeight.Bold) },
                    text = { Text("all protocol codes have been verified and settled.", fontFamily = font) },
                    confirmButton = {
                        Button(onClick = {
                            wordsToFind = wordPool.shuffled().take(8)
                            foundWords = emptySet()
                            showVictoryDialog = false
                        }) {
                            Text("new scramble".lowercase(), fontFamily = font)
                        }
                    },
                    dismissButton = {
                        Row {
                            TextButton(onClick = onCloseApp) {
                                Text("close app".lowercase(), fontFamily = font, color = Color.Red)
                            }
                            Spacer(Modifier.width(8.dp))
                            TextButton(onClick = { showVictoryDialog = false }) {
                                Text("just admire".lowercase(), fontFamily = font)
                            }
                        }
                    }
                )
            }
        }
    }
}

private fun isCellInCurrentSelection(x: Int, y: Int, start: Pair<Int, Int>?, end: Pair<Int, Int>?): Boolean {
    if (start == null || end == null) return false
    
    val dx = end.first - start.first
    val dy = end.second - start.second
    val adx = kotlin.math.abs(dx)
    val ady = kotlin.math.abs(dy)
    
    // Check if it's on a valid line (horizontal, vertical, or 45-degree diagonal)
    if (dx != 0 && dy != 0 && adx != ady) return x == start.first && y == start.second
    
    val stepX = if (dx == 0) 0 else dx / adx
    val stepY = if (dy == 0) 0 else dy / ady
    val length = kotlin.math.max(adx, ady)
    
    for (i in 0..length) {
        if (start.first + i * stepX == x && start.second + i * stepY == y) return true
    }
    return false
}

private fun isCellInFoundWord(x: Int, y: Int, placedWords: List<WordLocation>, foundWords: Set<String>): Boolean {
    for (loc in placedWords) {
        if (foundWords.contains(loc.word)) {
            for (i in loc.word.indices) {
                val wx = loc.x + i * loc.dir.dx
                val wy = loc.y + i * loc.dir.dy
                if (wx == x && wy == y) return true
            }
        }
    }
    return false
}

private fun checkSelection(start: Pair<Int, Int>, end: Pair<Int, Int>, engine: WordSearchEngine, wordsToFind: List<String>, onFound: (String?) -> Unit) {
    val (x1, y1) = start
    val (x2, y2) = end
    
    val dx = x2 - x1
    val dy = y2 - y1
    val adx = kotlin.math.abs(dx)
    val ady = kotlin.math.abs(dy)
    
    if (dx == 0 && dy == 0) {
        onFound(null)
        return
    }
    
    val stepX = if (dx == 0) 0 else dx / adx
    val stepY = if (dy == 0) 0 else dy / ady
    
    if (adx != 0 && ady != 0 && adx != ady) {
        onFound(null)
        return
    }

    val length = kotlin.math.max(adx, ady) + 1
    val selectedString = StringBuilder()
    for (i in 0 until length) {
        val curX = x1 + i * stepX
        val curY = y1 + i * stepY
        if (curX !in 0 until engine.size || curY !in 0 until engine.size) break
        selectedString.append(engine.grid[curY][curX])
    }
    
    val s = selectedString.toString()
    if (wordsToFind.contains(s)) {
        onFound(s)
    } else {
        val reverseS = s.reversed()
        if (wordsToFind.contains(reverseS)) {
            onFound(reverseS)
        } else {
            onFound(null)
        }
    }
}
