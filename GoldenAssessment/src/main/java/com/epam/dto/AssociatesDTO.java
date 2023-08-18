package com.epam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssociatesDTO {
	private Integer id;
	@Size(min=3,max=30,message="name of associate should be between 3-30")
	private String name;
	@Size(min=3,max=30,message="email of associate should be between 3-30")
	private String email;
	@Size(min=1,max=1,message="gender should be charater either of M or F")
	private String gender;
	@NotBlank(message="need to specify college")
	private String college;
	@Size(min=6,max=8,message="status must be either ACTIVE or INACTIVE")
	private String status;
	@NotNull(message = "batch should not be empty")
	private BatchesDTO batches;
}
