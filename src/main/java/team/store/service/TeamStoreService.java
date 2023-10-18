package team.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.store.controller.model.TeamStoreData;
import team.store.controller.model.TeamStoreData.TeamStoreCustomer;
import team.store.controller.model.TeamStoreData.TeamStoreEmployee;
import team.store.dao.CustomerDao;
import team.store.dao.EmployeeDao;
import team.store.dao.TeamStoreDao;
import team.store.entity.Customer;
import team.store.entity.Employee;
import team.store.entity.TeamStore;

@Service
public class TeamStoreService {

	@Autowired
	private TeamStoreDao teamStoreDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private CustomerDao customerDao;

	public TeamStoreData saveTeamStore(TeamStoreData teamStoreData) {

		TeamStore teamStore = findOrCreateTeamStore(teamStoreData.getTeamStoreId());
		copyTeamStoreFields(teamStore, teamStoreData);

		TeamStore dbTeamStore = teamStoreDao.save(teamStore);
		return new TeamStoreData(dbTeamStore);
	}

	private void copyTeamStoreFields(TeamStore teamStore, TeamStoreData teamStoreData) {
		teamStore.setTeamStoreId(teamStoreData.getTeamStoreId());
		teamStore.setTeamStoreType(teamStoreData.getTeamStoreType());
		teamStore.setTeamStoreLocation(teamStoreData.getTeamStoreLocation());
		teamStore.setTeamStoreLabel(teamStoreData.getTeamStoreLabel());
	}

	private TeamStore findOrCreateTeamStore(Long teamStoreId) {
		TeamStore teamStore;

		if (Objects.isNull(teamStoreId)) {
			teamStore = new TeamStore();
		} else {
			teamStore = findTeamStoreById(teamStoreId);
		}

		return teamStore;
	}

	private TeamStore findTeamStoreById(Long teamStoreId) {
		return teamStoreDao.findById(teamStoreId)
				.orElseThrow(() -> new NoSuchElementException("Team store with ID=" + teamStoreId + " does not exist."));
	}
	
	@Transactional(readOnly = true)
	public TeamStoreData retrieveTeamStoreById(Long teamStoreId) {
		return new TeamStoreData(findTeamStoreById(teamStoreId));
	}

	private Employee findEmployeeById(Long teamStoreId, Long employeeId) {
		Employee dbEmployee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " does not exist."));

		if (dbEmployee.getTeamStore().getTeamStoreId() != teamStoreId) {
			throw new IllegalArgumentException(
					"Employee with ID=" + employeeId + " is not an employee at team store with ID=" + teamStoreId + ".");
		} else {
			return dbEmployee;
		}
	}

	private Employee findOrCreateEmployee(Long employeeId, Long teamStoreId) {
		Employee employee;

		if (Objects.isNull(employeeId)) {
			employee = new Employee();
		} else {
			employee = findEmployeeById(teamStoreId, employeeId);
		}
		return employee;
	}

	private void copyEmployeeFields(Employee employee, TeamStoreEmployee teamStoreEmployee) {
		employee.setEmployeeFirstName(teamStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(teamStoreEmployee.getEmployeeLastName());
		employee.setEmployeeJobTitle(teamStoreEmployee.getEmployeeJobTitle());
		employee.setEmployeePhone(teamStoreEmployee.getEmployeePhone());
		employee.setEmployeeId(teamStoreEmployee.getEmployeeId());
	}

	@Transactional(readOnly = false)
	public TeamStoreEmployee saveEmployee(Long teamStoreId, TeamStoreEmployee teamStoreEmployee) {
		TeamStore teamStore = findTeamStoreById(teamStoreId);

		Employee employee = findOrCreateEmployee(teamStoreEmployee.getEmployeeId(), teamStoreId);
		copyEmployeeFields(employee, teamStoreEmployee);
		employee.setTeamStore(teamStore);
		teamStore.getEmployees().add(employee);
		Employee dbEmployee = employeeDao.save(employee);
		return new TeamStoreEmployee(dbEmployee);
	}

	
	private Customer findCustomerById(Long customerId, Long teamStoreId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " does not exist."));
		boolean found = false;
		for (TeamStore teamStore : customer.getTeamStores()) {
			if (teamStore.getTeamStoreId()==(teamStoreId)) {
				found = true;
				break;
			} 
		} 
		if(!found) {
			throw new IllegalArgumentException(
					"Team Store with ID=" + teamStoreId + " not found for the Customer with ID=" + customerId);
		}
		return customer;
	}
	
	private Customer findOrCreateCustomer(Long customerId, Long teamStoreId) {
		Customer customer;

		if (Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(customerId, teamStoreId);
		}
		return customer;
	}
	
	private void copyCustomerFields(Customer customer, TeamStoreCustomer teamStoreCustomer) {
		customer.setCustomerFirstName(teamStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(teamStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(teamStoreCustomer.getCustomerEmail());
		customer.setCustomerId(teamStoreCustomer.getCustomerId());
	}
	
	@Transactional(readOnly = false)
	public TeamStoreCustomer saveCustomer(Long teamStoreId, TeamStoreCustomer teamStoreCustomer) {
		TeamStore teamStore = findTeamStoreById(teamStoreId);
		Long customerId = teamStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId, teamStoreId);
		copyCustomerFields(customer, teamStoreCustomer);
		teamStore.getCustomers().add(customer);
		customer.getTeamStores().add(teamStore);
		Customer dbCustomer = customerDao.save(customer);
		return new TeamStoreCustomer(dbCustomer);
	}
	
	@Transactional(readOnly = true)
	public List<TeamStoreData> retrieveAllTeamStores() {
		List<TeamStore> teamStores = teamStoreDao.findAll();
		List<TeamStoreData> result = new LinkedList<>();
		
		for(TeamStore teamStore: teamStores) {
			TeamStoreData psd = new TeamStoreData(teamStore);
			
			psd.getCustomers().clear();
			psd.getEmployees().clear();
			
			result.add(psd);
		}
		return result;
	}

	public TeamStoreData returnTeamStoreById(Long teamStoreId) {
		TeamStore TeamStore = findTeamStoreById(teamStoreId);
		return new TeamStoreData(TeamStore);
	}

	public void deleteTeamStoreById(Long teamStoreId) {
		TeamStore teamStore = findTeamStoreById(teamStoreId);
		teamStoreDao.delete(teamStore);
	}
}


