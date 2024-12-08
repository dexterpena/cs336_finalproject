package com.rudbmsgroupproject.project_2;

import com.rudbmsgroupproject.project_2.cli.AppCommands;
import com.rudbmsgroupproject.project_2.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

	@Autowired
	private DatabaseService databaseService;

	@Autowired
	private AppCommands appCommands;

	public static void main(String[] args) {

		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Call the method to connect to the database
		databaseService.connectToDatabase();
		appCommands.menu();

		// Call the method to print the database schema
//		databaseService.printDatabaseSchema();
	}
}