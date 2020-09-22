package web.controllers;

import core.domain.Message;
import core.usecases.greeter.GreeterActions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreeterController {
    private final GreeterActions greeterActions;

    public GreeterController(GreeterActions greeterActions){
        this.greeterActions = greeterActions;
    }

    @GetMapping("receive")
    public List<Message> Receive(){
        return greeterActions.RetrieveMessages();
    }

    @PostMapping("greet")
    public void Greet(Message message){
        greeterActions.sendMessage(message);
    }
}
