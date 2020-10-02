package com.ucll.afstudeer.IoT;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.service.device.DeviceService;
import com.ucll.afstudeer.IoT.service.game.GameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IoTApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoTApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(DeviceService deviceService, GameService gameService) {
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
			device1 = deviceService.registerDeviceWithPuzzle(device1);
			device2 = deviceService.registerDeviceWithPuzzle(device2);
			device3 = deviceService.registerDeviceWithPuzzle(device3);

			// create game
			var game1 = new Game.Builder()
					.withName("Game1")
					.build();

			// create game with subscriptions (puzzle1 <- puzzle2 <- puzzle3)
			gameService.createGame(game1);
			gameService.addPuzzleSubscription(game1, device1, null, 1);
			gameService.addPuzzleSubscription(game1, device2, device1.getPuzzle() ,2);
			gameService.addPuzzleSubscription(game1, device3, device2.getPuzzle(), 3);

			// create other game with no subscriptions
			gameService.createGame(new Game.Builder().withName("Game2").build());
		};
	}
}
