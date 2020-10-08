// Data
const puzzle = "Puzzle2"
const solution = "0.99"
const device = "ARDUINO_" + puzzle + "_" + solution;
gameSession = "0";

// Create WebSocket connection.
const socket = new WebSocket('ws://localhost:8080/server');


// puzzle attempt success
document.getElementById("success").onclick = function () {
    addToSend("Send message: " + "PATMPT_" + puzzle + "_" + gameSession + "_true")
    socket.send("PATMPT_" + puzzle + "_" + gameSession + "_true");
};

// puzzle attempt fail
document.getElementById("fail").onclick = function () {
    addToSend("Send message: " + "PATMPT_" + puzzle + "_" + gameSession + "_false")
    socket.send("PATMPT_" + puzzle + "_" + gameSession + "_false");
};

// Connection opened
socket.addEventListener('open', async function (event) {
    console.log("Connection open");

    // register device
    addToSend("Send message: " + "REGDEVP_" + device)
    socket.send("REGDEVP_" + device);    
});

// Listen for messages
socket.addEventListener('message', function (event) {
    var message = event.data;
    var event = message.split("_")[0];

    if(event === "STARTGAME"){
        gameSession = message.split("_")[1];
    }

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