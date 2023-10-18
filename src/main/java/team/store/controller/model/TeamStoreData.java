package team.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import team.store.entity.Customer;
import team.store.entity.Employee;
import team.store.entity.TeamStore;

@Data 
@NoArgsConstructor
public class TeamStoreData {
	
	private Long teamStoreId;
	private String teamStoreType;
	private String teamStoreLocation;
	private String teamStoreLabel;
	private Set<TeamStoreEmployee> employees = new HashSet<>();
	private Set<TeamStoreCustomer> customers = new HashSet<>();
	
	
	public TeamStoreData(TeamStore teamStore) {
		teamStoreId = teamStore.getTeamStoreId();
		teamStoreType = teamStore.getTeamStoreType();
		teamStoreLocation = teamStore.getTeamStoreLocation();
		teamStoreLabel = teamStore.getTeamStoreLabel();
		
		for(Employee employee: teamStore.getEmployees()) {
			employees.add(new TeamStoreEmployee(employee));
		}
		
		for(Customer customer: teamStore.getCustomers()) {
			customers.add(new TeamStoreCustomer(customer));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class TeamStoreCustomer{
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		public TeamStoreCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName= customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class TeamStoreEmployee{
		private Long employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private Long employeePhone;
		private String employeeJobTitle;
		
		public TeamStoreEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
			employeeJobTitle = employee.getEmployeeJobTitle();
		}
	}
}
