var sock = new SockJS('http://localhost:8080/app');

sock.onopen = async function() {
    logMessage('Connection open');

    
    logMessage("Registering ARDUINO-puzzle1");
    sock.send("ARDUINO-puzzle1.REGISTER_DEVICE");
    
    logMessage("Sleeping for 5 seconds...");
    await sleep(5000);

    logMessage("Sending puzzle complete");
    sock.send("ARDUINO-puzzle1.PUZZLE_COMPLETED")

};

sock.onmessage = function(e) {
    logEvent(e.data);
};

sock.onclose = function() {
    logMessage("Connection closed");
};

function logMessage(message){
    var logs = document.getElementsByClassName("logs");

    logs[0].appendChild(document.createTextNode(message));
    logs[0].appendChild(document.createElement("br"));
}

function logEvent(event){
    var events = document.getElementsByClassName("events");

    events[0].appendChild(document.createTextNode(event));
    events[0].appendChild(document.createElement("br"));
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
