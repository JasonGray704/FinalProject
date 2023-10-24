package team.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import team.store.controller.model.TeamStoreData;
import team.store.controller.model.TeamStoreData.TeamStoreCustomer;
import team.store.controller.model.TeamStoreData.TeamStoreEmployee;
import team.store.service.TeamStoreService;

@RestController
@RequestMapping("/team_store")
@Slf4j
public class StoreController {

	@Autowired
	private TeamStoreService teamStoreService;

	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeamStoreData createTeamStore(@RequestBody TeamStoreData teamStoreData) {
		log.info("Creating store {}", teamStoreData);
		return teamStoreService.saveTeamStore(teamStoreData);
	}

	@PutMapping("/{teamStoreId}")
	public TeamStoreData updateTeamStore(@PathVariable Long teamStoreId, @RequestBody TeamStoreData TeamStoreData) {
		TeamStoreData.setTeamStoreId(teamStoreId);
		log.info("Updating team store {}", TeamStoreData);
		return teamStoreService.saveTeamStore(TeamStoreData);
	}

	@PostMapping("/{teamStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeamStoreEmployee addEmployee(@PathVariable Long teamStoreId, @RequestBody TeamStoreEmployee teamStoreEmployee) {
		log.info("Adding employee{} to team store with ID={}", teamStoreEmployee, teamStoreId);

		return teamStoreService.saveEmployee(teamStoreId, teamStoreEmployee);
	}

	@PostMapping("/{teamStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeamStoreCustomer addCustomer(@PathVariable Long teamStoreId, @RequestBody TeamStoreCustomer teamStoreCustomer) {
		log.info("Adding customer {} to team store with ID={}", teamStoreCustomer, teamStoreId);
		return teamStoreService.saveCustomer(teamStoreId, teamStoreCustomer);
	}

	@GetMapping
	public List<TeamStoreData> listAllTeamStores() {
		log.info("Listing all team stores");
		return teamStoreService.retrieveAllTeamStores();
	}

	@GetMapping("/{teamStoreId}")
	public TeamStoreData returnTeamStoreById(@PathVariable Long teamStoreId) {
		log.info("Retrieving team store with ID={}", teamStoreId);
		return teamStoreService.returnTeamStoreById(teamStoreId);
	}

	@DeleteMapping("/{teamStoreId}")
	public Map<String, String> deleteTeamStoreById(@PathVariable Long teamStoreId) {
		log.info("Removing team store with ID={}", teamStoreId);
		teamStoreService.deleteTeamStoreById(teamStoreId);
		return Map.of("message", "Successfully deleted team store with ID=" + teamStoreId);
	}

	@PutMapping("/{teamStoreId}/employee/{employeeId}")
	public TeamStoreEmployee updateEmployee(@PathVariable Long teamStoreId, @PathVariable Long employeeId,
			@RequestBody TeamStoreEmployee teamStoreEmployee) {
		log.info("Updating employee with ID={} at team store with ID={}", employeeId, teamStoreId);
		teamStoreEmployee.setEmployeeId(employeeId);
		return teamStoreService.saveEmployee(teamStoreId, teamStoreEmployee);
	}
	@DeleteMapping("/{teamStoreId}/employee/{employeeId}")
	public Map<String, String> deleteEmployeeById(@PathVariable Long teamStoreId, @PathVariable Long employeeId,
			@RequestBody TeamStoreEmployee teamStoreEmployee) {
		log.info("Removing team store with ID={}", employeeId);
		teamStoreService.deleteEmployeeById(employeeId, teamStoreId);
		return Map.of("message", "Successfully deleted team store with ID=" + teamStoreId);
	}
}
