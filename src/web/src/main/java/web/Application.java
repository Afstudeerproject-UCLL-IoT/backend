package web;

import core.domain.Puzzle;
import core.usecases.device.DeviceUseCases;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(DeviceUseCases deviceUseCases) {
		return (args) -> {
			// register 2 puzzles
			deviceUseCases.registerDeviceWithPuzzle("ARDUINO-Puzzle1");
			deviceUseCases.registerDeviceWithPuzzle("ARDUINO-Puzzle2");
			deviceUseCases.registerDeviceWithPuzzle("ARDUINO-Puzzle3");

			// subscribe puzzle 2 to puzzle 1
			deviceUseCases.subscribeToPuzzle("ARDUINO-Puzzle2", "Puzzle1");
			deviceUseCases.subscribeToPuzzle("ARDUINO-Puzzle3", "Puzzle2");
		};
	}
}
