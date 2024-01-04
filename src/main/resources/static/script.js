document.getElementById("get-result").addEventListener("click", function() {
    fetch("/hello")
        .then(response => response.text())
        .then(data => {
            document.getElementById("result").innerHTML = data
        })
});