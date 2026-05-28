package net.notipv6.ipv5

import kotlin.random.Random

object MoreFeatures {
    fun dnsRoulette(domain: String) = listOf("myspace.com", "geocities.com", "altavista.digital.com").random()
    fun quantumPacketLoss(packet: String) = if (Random.nextBoolean()) null else packet
    fun blockchainArp() = "Resolving... ETA: 10 minutes"
    fun udpHandshake() = "SYN-ACK (UDP Edition)"
    fun tcpWindowBreaker() = Int.MAX_VALUE
    fun http09Downgrade(request: String) = "GET / HTTP/0.9\r\n"
    fun bgpRoulette() = "Advertising 127.0.0.1/8 to AS1"
    fun icmpScream() = ByteArray(65535) { Random.nextInt().toByte() }
    fun subnetMaskGenerator() = "255.0.255.128"
    fun wifiDeauthenticator() = "Disconnecting..."
    fun localhostLoadBalancer() = if (Random.nextBoolean()) "127.0.0.1" else "0.0.0.0"
    fun ipv4ExhaustionSimulator(packet: String) = if (Random.nextInt(100) < 30) null else packet
    fun ipv5Literal() = "5.5.5.5"
    fun sslTlsDowngrader() = "SSLv2 Enabled"
    fun macAddressRandomizer() = "00:${Random.nextInt(10, 100)}:${Random.nextInt(10, 100)}:XX:YY:ZZ"
    fun packetFragmentationMaximizer(packet: String) = packet.chunked(1)
    fun tracerouteVisualizer() = "Map generated. You are in the ocean."
    fun portKnocker() = listOf(80, 443, 22, 21, 23).joinToString(" knock ")
    fun dhcpRejector() = "DHCPNACK to all"
    fun pingOfLife() = "Resurrecting router at 192.168.1.1"
    fun cloudLatencyInjector(ms: Int) = ms + 500
    fun ethernetOverDns() = "TXT record tunnel established"
    fun bluetoothLeWebServer() = "Serving index.html via BLE (1KB/s)"
    fun vpnToNull() = "Routing all traffic to /dev/null. Highly secure."
    fun natTransversal() = "Please physically press the reset button on your router."
    fun synFloodSelfDefense() = "Self-SYN flooding initiated."
    fun mtuPathDiscoveryDenier() = "ICMP Fragmentation Needed? No."
    fun bgpHijacker() = "8.8.8.8 is ours now."
    fun wepEncryptionEnforcer() = "Downgraded to WEP. Password is 'apple123'."
    fun ipOverAvianCarriersSimulator() = "Packet dispatched via pigeon. ETA: 4 hours."
    fun tcpKeepAliveSpammer() = "Are you there? Are you there? Are you there?"
    fun proxyChainLoop() = "Proxy 1 -> Proxy 2 -> Proxy 3 -> You"
    fun dnssecInvalidator() = "Signatures mangled."
    fun qosMinimizer() = "Priority: Lowest."
    fun snmpPublicCommunityStringer() = "public"
    fun ipv6ToIpv4Translator(ipv6: String) = "192.168.1.1 (Close enough)"
}
