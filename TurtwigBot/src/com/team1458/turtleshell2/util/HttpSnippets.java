package com.team1458.turtleshell2.util;

/**
 * @author asinghani
 */
public class HttpSnippets {
	public static final String refreshScript = "setInterval(function() {var xmlhttp = new XMLHttpRequest();xmlhttp.onreadystatechange = function() {if (this.readyState == 4 && this.status == 200) {document.getElementById('data').innerHTML = this.responseText;}};xmlhttp.open('GET', '$PATH', true);xmlhttp.send();}, 50);";

	public static final String logHead = "<html><head><title>Log</title><script>$REFRESHSCRIPT</script></head><body style=\"background-color: black; font-family: monospace; font-size: 20px;\"><div id=\"data\">";

	public static final String logEnd = "</div></body></html>";
}
