package team.store.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import team.store.ComponentScanMarker;


@SpringBootApplication(scanBasePackageClasses = {ComponentScanMarker.class})
public class TeamStoreApp {
	public static void main(String[] args) {
		
		SpringApplication.run(TeamStoreApp.class, args); 

	}

}

