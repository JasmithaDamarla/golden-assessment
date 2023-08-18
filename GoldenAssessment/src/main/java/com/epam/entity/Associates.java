package com.epam.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Entity
public class Associates {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private String gender;
	private String college;
	private String status;
//	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH})//(targetEntity = Batches.class)
//	@JoinColumn(name = "batchFK")
	@ManyToOne
	private Batches batches;	
	
}
