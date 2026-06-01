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
            // --- Alphanumeric & Network Identifiers ---
            "1.1.1.1", "8.8.8.8", "127.0.0.1", "ipv4", "ipv6", "rfc791", "rfc2616", "rfc418", "rfc1149",
            "404error", "500error", "200ok", "301moved", "0xdeadbeef", "0xcahebabe", "1337h4x0r",
            "port80", "port443", "tcp22", "udp53", "802.11", "cat6", "rs232", "x86_64", "arm64",
            "win32", "posix", "v6.0", "beta2", "build42", "node.js", "sha256", "md5", "bip39",
            // --- Core & Lore ---
            "ipv5", "chaos", "quantum", "packet", "subnet", "router", "switch", "network",
            "bridge", "protocol", "octet", "entropy", "logic", "drift", "flux", "buffer",
            "stack", "heap", "node", "link", "port", "dns", "mac", "ping", "trace", "sync",
            "mumps", "miis", "magic", "npr", "global", "pdp11", "pdp15", "vax", "decsystem",
            "meditech", "barnett", "pappalardo", "marble", "holenet", "maybebit", "vibe",
            // --- Obscure & Technical ---
            "nibble", "word", "dword", "qword", "endian", "bigendian", "littleendian",
            "semaphore", "mutex", "deadlock", "livelock", "racecondition", "thunderingherd",
            "contextswitch", "interrupt", "trap", "syscall", "ioctl", "mmap", "brk", "sbrk",
            "pipeline", "superscalar", "outorder", "speculative", "branch", "cache", "l1cache",
            "l2cache", "l3cache", "associativity", "eviction", "writeback", "writethrough",
            "mesiprotocol", "snoop", "broadcast", "multicast", "unicast", "anycast", "broadcast",
            "jitter", "latency", "throughput", "bandwidth", "saturation", "congestion",
            "checksum", "cyclic", "crc32", "crc16", "hamming", "parity", "ecc", "reed", "solomon",
            "hashing", "collision", "avalanche", "salt", "pepper", "nonce", "iv", "cbc", "ebc",
            "gcm", "ctr", "aes", "rsa", "ecc", "diffie", "hellman", "keywrap", "pki", "x509",
            "token", "bearer", "oauth", "jwt", "saml", "oidc", "cookie", "session", "cors",
            "csrf", "xss", "sqli", "bufferoverflow", "heapcheck", "asan", "tsan", "msan",
            "compiler", "linker", "assembler", "loader", "interpreter", "jit", "aot", "bytecode",
            "ast", "parser", "lexer", "tokenizer", "optimizer", "codegen", "backend", "frontend",
            "transpiler", "minifier", "bundler", "webpack", "rollup", "vite", "esbuild",
            "docker", "container", "podman", "kubernetes", "kubelet", "etcd", "istio", "envoy",
            "microservice", "monolith", "serverless", "lambda", "fission", "knative",
            "database", "relational", "sql", "nosql", "acid", "base", "captheorem", "paxos",
            "raft", "zab", "consensus", "quorum", "sharding", "replication", "consistency",
            "sharding", "index", "btree", "hashindex", "gin", "gist", "vacuum", "wal",
            "journaling", "filesystem", "ext4", "zfs", "btrfs", "ntfs", "apfs", "inode",
            "superbloack", "journal", "fragmentation", "defrag", "swap", "paging", "mmu",
            "tlb", "segments", "overlays", "banks", "pagefault", "dirtybit", "cow", "refcount",
            "garbage", "collector", "mark", "sweep", "compact", "generational", "nursery",
            "thread", "process", "coroutine", "fiber", "greenbolt", "eventloop", "epoll",
            "kqueue", "select", "poll", "async", "await", "promise", "future", "deferred",
            "callback", "closure", "lambda", "currying", "monad", "functor", "applicative",
            "recursion", "memoization", "backtracking", "greedy", "dynamic", "programming",
            "complexity", "bigo", "omega", "theta", "npcomplete", "nphard", "tsp", "knapsack",
            "sorting", "quicksort", "mergesort", "heapsort", "radix", "bucket", "bubble",
            "graph", "tree", "linkedlist", "stack", "queue", "deque", "priority", "heap",
            "trie", "patricia", "radix", "bloomfilter", "hyperloglog", "skiplist", "ringbuffer",
            "semaphore", "signal", "pipe", "fifo", "socket", "domain", "tcp", "udp", "quic",
            "http", "grpc", "protobuf", "avro", "thrift", "json", "yaml", "toml", "bson",
            "xml", "html", "css", "js", "ts", "wasm", "webgpu", "webgl", "canvas", "dom",
            "virtual", "shadow", "reactive", "observable", "subject", "stream", "pipe",
            "map", "filter", "reduce", "fold", "zip", "flatten", "flatMap", "compactMap",
            "currying", "partial", "composition", "monad", "monoid", "semigroup", "category",
            "theory", "logic", "boolean", "predicate", "quantifier", "lambda", "calculus",
            "turing", "machine", "halting", "decidability", "completeness", "soundness",
            "formal", "methods", "verification", "modelchecking", "alloy", "tla", "coq", "lean",
            "assembly", "machinecode", "microcode", "risc", "cisc", "arm", "x86", "riscv", "mips",
            "powerpc", "sparc", "alpha", "itanium", "cell", "gpu", "tpu", "npu", "fpga", "asic",
            "vga", "hdmi", "displayport", "usb", "thunderbolt", "pci", "nvme", "sata", "sas",
            "raid", "jbod", "san", "nas", "iscsi", "fiberchannel", "infiniband", "ethernet",
            "wifi", "bluetooth", "zigbee", "lorawan", "nfc", "rfid", "lte", "5g", "gnss", "gps",
            "sensor", "actuator", "firmware", "bios", "uefi", "bootloader", "grub", "kernel",
            "module", "driver", "shell", "bash", "zsh", "fish", "powershell", "cmd", "terminal",
            "emulator", "hypervisor", "kvm", "xen", "vmware", "virtualbox", "qemu", "bochs",
            "cloud", "aws", "azure", "gcp", "digitalocean", "heroku", "netlify", "vercel",
            "ci", "cd", "jenkins", "github", "gitlab", "bitbucket", "travis", "circleci",
            "git", "svn", "mercurial", "perforce", "cvs", "fossil", "darcs", "bazaar",
            "vim", "emacs", "vscode", "intellij", "sublime", "nano", "ed", "vi", "ex",
            "debugging", "gdb", "lldb", "pdb", "strace", "ltrace", "dtrace", "perf", "ebpf",
            "logging", "tracing", "monitoring", "alerting", "metrics", "grafana", "prometheus",
            "splunk", "elk", "datadog", "newrelic", "sentry", "pagerduty", "ops", "sre", "devops",
            "agile", "scrum", "kanban", "waterfall", "extreme", "programming", "tdd", "bdd",
            "refactoring", "technical", "debt", "legacy", "greenfield", "brownfield",
            "encryption", "decryption", "hashing", "signing", "verification", "zero", "knowledge",
            "proof", "blockchain", "bitcoin", "ethereum", "smart", "contract", "solidity",
            "ipfs", "p2p", "distributed", "decentralized", "federated", "matrix", "activitypub",
            "security", "privacy", "anonymity", "tor", "vpn", "firewall", "ids", "ips", "waf",
            "malware", "virus", "worm", "trojan", "ransomware", "spyware", "adware", "rootkit",
            "exploit", "payload", "shellcode", "injection", "overflow", "fuzzing", "pentest",
            "redteam", "blueteam", "soc", "noc", "cert", "incident", "response", "forensics",
            "ai", "ml", "neural", "network", "transformer", "bert", "gpt", "cnn", "rnn", "lstm",
            "overfitting", "underfitting", "bias", "variance", "loss", "optimizer", "epoch",
            "batch", "normalization", "dropout", "activation", "relu", "sigmoid", "tanh",
            "tensor", "pytorch", "tensorflow", "keras", "jax", "numpy", "pandas", "scipy",
            "data", "mining", "warehouse", "lake", "etl", "bi", "analytics", "visualization",
            "robotics", "cv", "nlp", "speech", "synthesis", "recognition", "vision", "lidar",
            "slam", "kinematics", "dynamics", "control", "theory", "pid", "kalman", "filter",
            "quantum", "computing", "qubit", "superposition", "entanglement", "gate", "circuit",
            "shor", "grover", "decoherence", "teleportation", "cryogenics", "photonics",
            "ar", "vr", "mr", "xr", "haptics", "telepresence", "holodeck", "metaverse",
            "gaming", "engine", "unity", "unreal", "godot", "rendering", "raytracing", "raster",
            "sprite", "pixel", "texel", "voxel", "shader", "hlsl", "glsl", "metal", "vulkan",
            "audio", "codec", "mp3", "aac", "flac", "wav", "midi", "sampling", "quantization",
            "video", "h264", "h265", "av1", "vp9", "bitrate", "framerate", "resolution",
            "compression", "lossy", "lossless", "entropy", "huffman", "lzw", "lz77", "lzma",
            "zlib", "gzip", "bzip2", "zstd", "brotli", "snappy", "lz4", "rle", "delta",
            "encoding", "utf8", "ascii", "unicode", "base64", "hex", "binary", "urlencode",
            "rest", "soap", "graphql", "webhook", "polling", "longpolling", "websocket", "sse",
            "mqtt", "amqp", "kafka", "redis", "memcached", "etcd", "consul", "zookeeper",
            "loadbalancer", "nginx", "apache", "haproxy", "caddy", "traefik", "gateway",
            "proxy", "reverse", "forward", "tunnel", "vpn", "ssh", "rdp", "vnc", "telnet",
            "ftp", "sftp", "scp", "rsync", "nfs", "smb", "cifs", "afs", "gluster", "ceph",
            "s3", "blob", "bucket", "object", "storage", "cold", "hot", "archival", "tape",
            "punchcard", "magnetic", "optical", "flash", "ssd", "hdd", "sdram", "ddr", "gddr",
            "hbm", "dma", "pio", "interrupt", "polling", "context", "switch", "scheduler",
            "priority", "fairness", "starvation", "deadlock", "livelock", "thrashing",
            "working", "set", "locality", "temporal", "spatial", "cache", "miss", "hit",
            "victim", "tag", "index", "offset", "way", "set", "associative", "direct", "mapped",
            "fully", "associative", "lru", "lfu", "mru", "pseudo", "random", "replacement",
            "pipeline", "stall", "hazard", "data", "control", "structural", "forwarding",
            "bypassing", "branch", "prediction", "btb", "ras", "tournament", "predictor",
            "speculation", "commit", "retirement", "reorder", "buffer", "reservation", "station",
            "functional", "unit", "alu", "fpu", "agp", "lsu", "decode", "fetch", "issue", "rename",
            "register", "file", "physical", "logical", "rat", "freelist", "checkpoint",
            "exception", "handling", "precise", "imprecise", "vector", "simd", "mimd", "sisd",
            "misd", "vliw", "epic", "gpgpu", "cuda", "opencl", "sycl", "oneapi", "rocm",
            "fabric", "interconnect", "mesh", "torus", "ring", "star", "bus", "crossbar",
            "latency", "bandwidth", "diameter", "bisection", "bandwidth", "routing", "algorithm",
            "deadlock", "freedom", "livelock", "freedom", "adaptive", "deterministic",
            "virtual", "channel", "flow", "control", "credit", "based", "on", "off", "backpressure",
            "wormhole", "switching", "cut", "through", "store", "and", "forward", "packet",
            "flit", "phit", "header", "payload", "trailer", "checksum", "crc", "error",
            "correction", "detection", "retry", "timeout", "retransmission", "window", "size",
            "congestion", "control", "slow", "start", "congestion", "avoidance", "fast",
            "retransmit", "fast", "recovery", "selective", "ack", "sack", "nack", "ecn", "red",
            "wred", "fair", "queuing", "weighted", "round", "robin", "leaky", "bucket",
            "token", "bucket", "shaping", "policing", "quality", "of", "service", "qos", "diffserv",
            "intserv", "rsvp", "mpls", "bgp", "ospf", "isis", "rip", "eigrp", "stp", "vlan",
            "vxlan", "nvgre", "geneve", "sdn", "openflow", "p4", "nfv", "dpdk", "xdp", "ebpf"
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
    val isAccessible = GlobalAppState.accessibilityMode.value
    val textColor = GlobalAppState.currentTextColor.value
    val font = if (isAccessible) FontFamily.Default else GlobalAppState.getRandomFont()

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
                                        modifier = if (isIpv7 && !isAccessible && Random.nextFloat() > 0.95f) Modifier.padding(Random.nextInt(4).dp) else Modifier
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
