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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

enum class Direction(val dx: Int, val dy: Int) {
    RIGHT(1, 0), LEFT(-1, 0), DOWN(0, 1), UP(0, -1),
    DR(1, 1), DL(-1, 1), UR(1, -1), UL(-1, -1)
}

data class WordLocation(val word: String, val x: Int, val y: Int, val dir: Direction)

class WordSearchEngine(val size: Int = 12) {
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
        val chars = 'a'..'z'
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
            "ipv5", "chaos", "quantum", "packet", "subnet", "router", "switch", "network",
            "bridge", "protocol", "octet", "entropy", "logic", "drift", "flux", "buffer",
            "stack", "heap", "node", "link", "port", "dns", "mac", "ping", "trace", "sync"
        )
    }

    var wordsToFind by remember { mutableStateOf(wordPool.shuffled().take(8)) }
    val gridSize = 12
    val engine = remember(wordsToFind) {
        WordSearchEngine(gridSize).apply { generate(wordsToFind) }
    }

    var selectedStart by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var foundWords by remember { mutableStateOf(setOf<String>()) }
    val isIpv7 = GlobalAppState.ipv7Mode.value
    val textColor = GlobalAppState.currentTextColor.value
    val font = GlobalAppState.getRandomFont()

    ChaoticPanel(title = "word search") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                "find ${wordsToFind.size - foundWords.size} more words".lowercase(),
                style = MaterialTheme.typography.subtitle1,
                color = textColor,
                fontFamily = font
            )
            
            Spacer(Modifier.height(16.dp))

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
                                        fontSize = if (isIpv7) 22.sp else 16.sp,
                                        color = if (isPartOfFound) Color.Green else textColor,
                                        fontFamily = font,
                                        modifier = if (isIpv7 && Random.nextFloat() > 0.95f) Modifier.padding(Random.nextInt(4).dp) else Modifier
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Word List
            Column(modifier = Modifier.fillMaxWidth()) {
                wordsToFind.chunked(4).forEach { rowWords ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowWords.forEach { word ->
                            val isFound = foundWords.contains(word)
                            Text(
                                word,
                                color = if (isFound) Color.Green else textColor.copy(alpha = 0.6f),
                                fontFamily = font,
                                style = if (isFound) MaterialTheme.typography.body2.copy(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough)
                                        else MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    wordsToFind = wordPool.shuffled().take(8)
                    foundWords = emptySet()
                    selectedStart = null
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = if (isIpv7) Color.Red else Color(0xFF6200EE))
            ) {
                Text("re-scramble grid".lowercase(), color = Color.White, fontFamily = font)
            }
            
            if (foundWords.size == wordsToFind.size) {
                Text(
                    "all words found! chaos averted.".lowercase(),
                    modifier = Modifier.padding(top = 16.dp),
                    color = Color.Green,
                    fontWeight = FontWeight.Bold,
                    fontFamily = font
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
