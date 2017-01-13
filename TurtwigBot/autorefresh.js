setInterval(function() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById('data').innerHTML = this.responseText;
        }
    };
    xmlhttp.open('GET', '$PATH', true);
    xmlhttp.send();
}, 50);