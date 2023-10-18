package team.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



@Entity
@Data 
public class Employee {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private Long employeePhone;
	private String employeeJobTitle;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	
	@ManyToOne(cascade = CascadeType.ALL)
	
	@JoinColumn(name = "team_store_id")
	private TeamStore teamStore;
}

