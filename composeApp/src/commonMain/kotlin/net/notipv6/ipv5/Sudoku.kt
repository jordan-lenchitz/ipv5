package net.notipv6.ipv5

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

class SudokuEngine {
    val solved = Array(9) { IntArray(9) }
    val initial = Array(9) { IntArray(9) }
    val user = Array(9) { IntArray(9) }

    fun generate(difficulty: String) {
        for (r in 0..8) {
            for (c in 0..8) {
                solved[r][c] = 0
                initial[r][c] = 0
                user[r][c] = 0
            }
        }

        fillBoard(0, 0)

        for (r in 0..8) {
            for (c in 0..8) {
                initial[r][c] = solved[r][c]
            }
        }

        val cluesToKeep = when (difficulty) {
            "easy" -> 45
            "medium" -> 35
            else -> 26
        }
        val cellsToRemove = 81 - cluesToKeep
        val list = (0..80).toList().shuffled()
        for (i in 0 until cellsToRemove) {
            val idx = list[i]
            val r = idx / 9
            val c = idx % 9
            initial[r][c] = 0
        }

        for (r in 0..8) {
            for (c in 0..8) {
                user[r][c] = initial[r][c]
            }
        }
    }

    private fun fillBoard(row: Int, col: Int): Boolean {
        var r = row
        var c = col
        if (c == 9) {
            c = 0
            r++
            if (r == 9) return true
        }

        val numbers = (1..9).toList().shuffled()
        for (num in numbers) {
            if (isValid(r, c, num)) {
                solved[r][c] = num
                if (fillBoard(r, c + 1)) return true
                solved[r][c] = 0
            }
        }
        return false
    }

    private fun isValid(row: Int, col: Int, num: Int): Boolean {
        for (i in 0..8) {
            if (solved[row][i] == num) return false
            if (solved[i][col] == num) return false
        }
        val startRow = (row / 3) * 3
        val startCol = (col / 3) * 3
        for (i in 0..2) {
            for (j in 0..2) {
                if (solved[startRow + i][startCol + j] == num) return false
            }
        }
        return true
    }
}

@Composable
fun SudokuPanel(onCloseApp: () -> Unit = { exitApp() }) {
    var difficulty by remember { mutableStateOf("easy") }
    val engine = remember { SudokuEngine() }
    
    // Board state trigger
    var gameTrigger by remember { mutableStateOf(0) }
    var gridUpdateTrigger by remember { mutableStateOf(0) }
    
    // Initialize board on first load or difficulty change
    LaunchedEffect(difficulty, gameTrigger) {
        engine.generate(difficulty)
        gridUpdateTrigger++
    }

    var selectedCell by remember(difficulty, gameTrigger) { mutableStateOf<Pair<Int, Int>?>(null) }
    var showVictoryDialog by remember { mutableStateOf(false) }
    var verificationMessage by remember(difficulty, gameTrigger) { mutableStateOf("") }
    var highlightedMistakes by remember(difficulty, gameTrigger) { mutableStateOf(setOf<Pair<Int, Int>>()) }

    val isAccessible = GlobalAppState.accessibilityMode.value
    val bgColor = if (isAccessible) Color.Black else Color.White
    val textColor = if (isAccessible) Color.White else Color.Black
    val foundColor = Color(0xFF4DB6AC) // Teal accent
    val font = FontFamily.Default

    // Check if user solved it
    fun checkWinCondition() {
        var solvedCount = 0
        for (r in 0..8) {
            for (c in 0..8) {
                if (engine.user[r][c] == engine.solved[r][c]) {
                    solvedCount++
                }
            }
        }
        if (solvedCount == 81) {
            showVictoryDialog = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = bgColor)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("sudoku", style = MaterialTheme.typography.h4, fontFamily = font, color = textColor)
        Spacer(Modifier.height(16.dp))

        // Difficulty Selector Tabs
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("easy", "medium", "hard").forEach { diff ->
                val isSelected = difficulty == diff
                Button(
                    onClick = { difficulty = diff },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isSelected) {
                            foundColor
                        } else {
                            if (isAccessible) Color(0xFF2E2E2E) else Color(0xFFEEEEEE)
                        }
                    ),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp).height(32.dp)
                ) {
                    Text(
                        diff,
                        color = if (isSelected) Color.Black else textColor.copy(alpha = 0.6f),
                        fontFamily = font,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // The 9x9 Grid UI
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(2.dp, textColor.copy(alpha = 0.8f))
                .background(bgColor)
        ) {
            // Read gridUpdateTrigger to force recomposition when board changes
            val trigger = gridUpdateTrigger
            for (r in 0..8) {
                // Thick horizontal borders for 3x3 blocks
                if (r > 0 && r % 3 == 0) {
                    Divider(color = textColor.copy(alpha = 0.8f), thickness = 2.dp)
                } else if (r > 0) {
                    Divider(color = textColor.copy(alpha = 0.15f), thickness = 0.5.dp)
                }

                Row(modifier = Modifier.weight(1f)) {
                    for (c in 0..8) {
                        // Thick vertical borders for 3x3 blocks
                        if (c > 0 && c % 3 == 0) {
                            Box(modifier = Modifier.width(2.dp).fillMaxHeight().background(textColor.copy(alpha = 0.8f)))
                        } else if (c > 0) {
                            Box(modifier = Modifier.width(0.5.dp).fillMaxHeight().background(textColor.copy(alpha = 0.15f)))
                        }

                        val initialVal = engine.initial[r][c]
                        val userVal = engine.user[r][c]
                        val isClue = initialVal != 0
                        val isSelected = selectedCell == (r to c)
                        
                        // Highlighting helper: same row, col, or 3x3 block
                        val isHighlighted = selectedCell?.let { (sr, sc) ->
                            r == sr || c == sc || (r / 3 == sr / 3 && c / 3 == sc / 3)
                        } ?: false

                        val isMistake = highlightedMistakes.contains(r to c)

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(
                                    when {
                                        isMistake -> Color.Red.copy(alpha = 0.25f)
                                        isSelected -> foundColor.copy(alpha = 0.4f)
                                        isHighlighted -> foundColor.copy(alpha = 0.1f)
                                        else -> Color.Transparent
                                    }
                                )
                                .clickable {
                                    selectedCell = r to c
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (userVal != 0) {
                                Text(
                                    userVal.toString(),
                                    fontSize = 18.sp,
                                    fontWeight = if (isClue) FontWeight.Bold else FontWeight.Normal,
                                    color = when {
                                        isMistake -> Color.Red
                                        isClue -> textColor
                                        else -> foundColor
                                    },
                                    fontFamily = font
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Verification Message Banner
        if (verificationMessage.isNotEmpty()) {
            Text(
                verificationMessage,
                color = if (verificationMessage.contains("mistake")) Color.Red else foundColor,
                fontSize = 12.sp,
                fontFamily = font,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().height(18.dp)
            )
        } else {
            Spacer(Modifier.height(18.dp))
        }

        Spacer(Modifier.height(4.dp))

        // Large Keypad (1 to 9) and "clear" (eraser) button for high touchscreen usability
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                (1..5).forEach { num ->
                    Button(
                        onClick = {
                            selectedCell?.let { (sr, sc) ->
                                if (engine.initial[sr][sc] == 0) {
                                    engine.user[sr][sc] = num
                                    // Auto-clear active mistake highlights on input
                                    highlightedMistakes = highlightedMistakes - setOf(sr to sc)
                                    verificationMessage = ""
                                    checkWinCondition()
                                    gridUpdateTrigger++
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isAccessible) Color(0xFF2E2E2E) else Color(0xFFEEEEEE)
                        ),
                        modifier = Modifier.weight(1f).padding(horizontal = 2.dp).height(38.dp)
                    ) {
                        Text(
                            num.toString(),
                            color = textColor,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                (6..9).forEach { num ->
                    Button(
                        onClick = {
                            selectedCell?.let { (sr, sc) ->
                                if (engine.initial[sr][sc] == 0) {
                                    engine.user[sr][sc] = num
                                    highlightedMistakes = highlightedMistakes - setOf(sr to sc)
                                    verificationMessage = ""
                                    checkWinCondition()
                                    gridUpdateTrigger++
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isAccessible) Color(0xFF2E2E2E) else Color(0xFFEEEEEE)
                        ),
                        modifier = Modifier.weight(1f).padding(horizontal = 2.dp).height(38.dp)
                    ) {
                        Text(
                            num.toString(),
                            color = textColor,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
                // Erase / Clear Button
                Button(
                    onClick = {
                        selectedCell?.let { (sr, sc) ->
                            if (engine.initial[sr][sc] == 0) {
                                    engine.user[sr][sc] = 0
                                    highlightedMistakes = highlightedMistakes - setOf(sr to sc)
                                    verificationMessage = ""
                                    gridUpdateTrigger++
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red.copy(alpha = 0.8f)
                    ),
                    modifier = Modifier.weight(1f).padding(horizontal = 2.dp).height(38.dp)
                ) {
                    Text(
                        "clear",
                        color = Color.White,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Action Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Verify button
            Button(
                onClick = {
                    val mistakes = mutableSetOf<Pair<Int, Int>>()
                    var hasUserInputs = false
                    for (r in 0..8) {
                        for (c in 0..8) {
                            val u = engine.user[r][c]
                            if (u != 0 && engine.initial[r][c] == 0) {
                                hasUserInputs = true
                                if (u != engine.solved[r][c]) {
                                    mistakes.add(r to c)
                                }
                            }
                        }
                    }
                    highlightedMistakes = mistakes
                    verificationMessage = when {
                        !hasUserInputs -> "enter numbers to verify"
                        mistakes.isNotEmpty() -> "mistakes found in entered values"
                        else -> "all correct so far"
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = foundColor
                ),
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp).height(36.dp)
            ) {
                Text(
                    "verify board",
                    color = Color.Black,
                    fontFamily = font,
                    fontSize = 12.sp
                )
            }

            // New puzzle button
            Button(
                onClick = {
                    gameTrigger++
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isAccessible) Color(0xFF2E2E2E) else Color(0xFFEEEEEE)
                ),
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp).height(36.dp)
            ) {
                Text(
                    "new scramble",
                    color = textColor,
                    fontFamily = font,
                    fontSize = 12.sp
                )
            }
        }

        // Victory Dialog
        if (showVictoryDialog) {
            val puzzleGuid = remember(difficulty, gameTrigger) {
                // Generate deterministic hash based on solution + difficulty
                val flatSolvedStr = engine.solved.joinToString("") { row -> row.joinToString("") }
                val rawString = "sudoku:$difficulty:$flatSolvedStr"
                var hash = 0
                for (char in rawString) {
                    hash = 31 * hash + char.code
                }
                val hexHash = (hash.toLong() and 0xFFFFFFFFL).toString(16).padStart(8, '0')
                "ipv5-sudoku-$difficulty-$hexHash"
            }

            val shareableText = remember(puzzleGuid, difficulty) {
                "ipv5 sudoku verification report\n" +
                "puzzle guid $puzzleGuid\n" +
                "difficulty $difficulty\n" +
                "matrix protocol fully resolved"
            }

            val clipboardManager = LocalClipboardManager.current
            var copied by remember { mutableStateOf(false) }

            AlertDialog(
                onDismissRequest = { showVictoryDialog = false },
                backgroundColor = bgColor,
                title = { Text("success", color = textColor, fontFamily = font, fontWeight = FontWeight.Bold) },
                text = {
                    Column {
                        Text("all protocol matrices verified and settled", color = textColor.copy(alpha = 0.8f), fontFamily = font)
                        Spacer(Modifier.height(12.dp))
                        Text("puzzle guid $puzzleGuid", color = textColor, fontFamily = font, fontWeight = FontWeight.SemiBold)
                        Text("difficulty $difficulty", color = textColor.copy(alpha = 0.8f), fontFamily = font)
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(shareableText))
                                copied = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (isAccessible) Color(0xFF2E2E2E) else Color(0xFFEEEEEE)
                            )
                        ) {
                            Text(
                                if (copied) "copied" else "copy shareable report",
                                color = textColor,
                                fontFamily = font,
                                fontSize = 12.sp
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            gameTrigger++
                            showVictoryDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isAccessible) Color(0xFF2E2E2E) else Color(0xFFEEEEEE)
                        )
                    ) {
                        Text(
                            "new scramble",
                            color = textColor,
                            fontFamily = font
                        )
                    }
                },
                dismissButton = {
                    Row {
                        TextButton(onClick = onCloseApp) {
                            Text("close app", fontFamily = font, color = Color.Red)
                        }
                        Spacer(Modifier.width(8.dp))
                        TextButton(onClick = { showVictoryDialog = false }) {
                            Text(
                                "just admire",
                                fontFamily = font,
                                color = if (isAccessible) Color.Black else textColor.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            )
        }
    }
}
