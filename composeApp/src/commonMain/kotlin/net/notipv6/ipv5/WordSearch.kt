package net.notipv6.ipv5

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
fun WordSearchPanel() {
    val wordPool = remember {
        listOf(
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
            // --- Alphanumeric & Network Identifiers ---
            "1.1.1.1", "8.8.8.8", "127.0.0.1", "ipv4", "ipv6", "rfc791", "rfc2616", "rfc418", "rfc1149",
            "1337h4x0r", "port80", "port443", "tcp22", "udp53", "802.11", "cat6", "rs232", "x86_64", "arm64",
            "win32", "posix", "v6.0", "beta2", "build42", "node.js", "sha256", "md5", "bip39",
            // --- Core & Lore ---
            "ipv5", "chaos", "quantum", "packet", "subnet", "router", "switch", "network",
            "bridge", "protocol", "octet", "entropy", "logic", "drift", "flux", "buffer",
            "stack", "heap", "node", "link", "port", "dns", "mac", "ping", "trace", "sync",
            "mumps", "miis", "magic", "npr", "global", "pdp11", "pdp15", "vax", "decsystem",
            "meditech", "barnett", "pappalardo", "marble", "holenet", "maybebit", "vibe"
        )
    }

    var wordsToFind by remember { mutableStateOf(wordPool.shuffled().take(8)) }
    val gridSize = 16
    val engine = remember(wordsToFind) {
        WordSearchEngine(gridSize).apply { generate(wordsToFind) }
    }

    var selectedStart by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var foundWords by remember { mutableStateOf(setOf<String>()) }
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val isAccessible = GlobalAppState.accessibilityMode.value
    val textColor = GlobalAppState.currentTextColor.value
    val font = if (isAccessible) FontFamily.Default else GlobalAppState.getRandomFont()

    ChaoticPanel(title = "word search") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        ) {
            Text(
                "find ${wordsToFind.size - foundWords.size} more codes".lowercase(),
                style = MaterialTheme.typography.subtitle1,
                color = textColor,
                fontFamily = font
            )
            
            Spacer(Modifier.height(8.dp))

            // The Grid
            Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
                Column {
                    for (y in 0 until gridSize) {
                        Row(modifier = Modifier.weight(1f)) {
                            for (x in 0 until gridSize) {
                                val char = engine.grid[y][x]
                                val isSelected = selectedStart?.let { it.first == x && it.second == y } ?: false
                                val isPartOfFound = isCellInFoundWord(x, y, engine.placedWords, foundWords)

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(1.dp)
                                        .background(
                                            when {
                                                isSelected -> Color.Yellow.copy(alpha = 0.5f)
                                                isPartOfFound -> Color.Green.copy(alpha = 0.4f)
                                                else -> Color.Transparent
                                            },
                                            RoundedCornerShape(2.dp)
                                        )
                                        .border(
                                            0.5.dp, 
                                            textColor.copy(alpha = 0.1f), 
                                            RoundedCornerShape(2.dp)
                                        )
                                        .clickable {
                                            if (selectedStart == null) {
                                                selectedStart = x to y
                                            } else {
                                                val start = selectedStart!!
                                                checkSelection(start.first, start.second, x, y, engine, wordsToFind) { found ->
                                                    if (found != null) {
                                                        foundWords = foundWords + found
                                                    }
                                                }
                                                selectedStart = null
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        char.toString(),
                                        fontWeight = if (isPartOfFound) FontWeight.ExtraBold else FontWeight.Normal,
                                        fontSize = if (isIpv7) 16.sp else 12.sp,
                                        color = if (isPartOfFound) Color.Green else textColor,
                                        fontFamily = font,
                                        modifier = if (isIpv7 && !isAccessible && Random.nextFloat() > 0.95f) Modifier.padding(Random.nextInt(2).dp) else Modifier
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
                                color = if (isFound) Color.Green else textColor.copy(alpha = 0.6f),
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
                },
                modifier = Modifier.height(36.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = if (isIpv7) Color.Red else Color(0xFF6200EE))
            ) {
                Text("re-scramble".lowercase(), color = Color.White, fontFamily = font, fontSize = 12.sp)
            }
            
            if (foundWords.size == wordsToFind.size) {
                Text(
                    "all codes found!".lowercase(),
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.Green,
                    fontWeight = FontWeight.Bold,
                    fontFamily = font,
                    fontSize = 12.sp
                )
            }
        }
    }
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

private fun checkSelection(x1: Int, y1: Int, x2: Int, y2: Int, engine: WordSearchEngine, wordsToFind: List<String>, onFound: (String?) -> Unit) {
    val dx = x2 - x1
    val dy = y2 - y1
    
    val adx = kotlin.math.abs(dx)
    val ady = kotlin.math.abs(dy)
    
    if (dx == 0 && dy == 0) return 
    
    val stepX = if (dx == 0) 0 else dx / adx
    val stepY = if (dy == 0) 0 else dy / ady
    
    if (adx != 0 && ady != 0 && adx != ady) return

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
        // Also check reverse just in case the user selected end-to-start
        val reverseS = s.reversed()
        if (wordsToFind.contains(reverseS)) {
            onFound(reverseS)
        } else {
            onFound(null)
        }
    }
}
