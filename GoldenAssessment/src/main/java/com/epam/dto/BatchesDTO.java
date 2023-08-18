package com.epam.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BatchesDTO {
	private Integer id;
	@Size(min=3,max=15,message="name of batch should be between 3-15")
	private String name;
	@Size(min=3,max=20,message="practice of associate should be between 3-20")
	private String practice;
	@NotBlank(message="please mention the start date")
	private Date startDate;
	@NotBlank(message="please mention the end date")
	private Date endDate;
	
}
