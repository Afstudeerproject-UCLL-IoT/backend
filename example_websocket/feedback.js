// Create WebSocket connection.
const socket = new WebSocket('ws://localhost:8080/server');

// Connection opened
socket.addEventListener('open', async function (event) {
    console.log("Connection open");

    // register device
    addToSend("Send message: REGDEVF")
    socket.send("REGDEVF");
});

// Listen for messages
socket.addEventListener('message', function (event) {
    var message = event.data;
    addToReceived(message);
});

function addToSend(message) {
    var element = document.getElementById("send");

    var p = document.createTextNode(message);
    var br = document.createElement("br");

    element.appendChild(p);
    element.appendChild(br);
}


function addToReceived(message) {
    var element = document.getElementById("received");

    var p = document.createTextNode(message);
    var br = document.createElement("br");

    element.appendChild(p);
    element.appendChild(br);
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}