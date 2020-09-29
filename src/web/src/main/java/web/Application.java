package web;

import core.domain.Device;
import core.domain.Game;
import core.domain.Puzzle;
import core.usecases.device.DeviceUseCases;
import core.usecases.game.GameUseCases;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(DeviceUseCases deviceUseCases, GameUseCases gameUseCases) {
		return (args) -> {
			// create 3 devices with puzzles
			var device1 = new Device.Builder()
					.withoutId()
					.fromDeviceName("ARDUINO-Puzzle1")
					.build();

			var device2 = new Device.Builder()
					.withoutId()
					.fromDeviceName("ARDUINO-Puzzle2")
					.build();

			var device3 = new Device.Builder()
					.withoutId()
					.fromDeviceName("ARDUINO-Puzzle3")
					.build();

			// register 3 puzzles
			device1 = deviceUseCases.registerDeviceWithPuzzle(device1);
			device2 = deviceUseCases.registerDeviceWithPuzzle(device2);
			device3 = deviceUseCases.registerDeviceWithPuzzle(device3);

			// create game
			var game1 = new Game.Builder()
					.withName("Game1")
					.build();

			// create game with subscriptions (puzzle1 <- puzzle2 <- puzzle3)
			gameUseCases.createGame(game1);
			gameUseCases.addPuzzleSubscription(game1, device2, device1.getPuzzle());
			gameUseCases.addPuzzleSubscription(game1, device3, device2.getPuzzle());
		};
	}
}
