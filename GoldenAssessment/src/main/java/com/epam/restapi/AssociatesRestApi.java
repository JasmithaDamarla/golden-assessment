package com.epam.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.AssociatesDTO;
import com.epam.exceptions.AssociatesException;
import com.epam.service.interfaces.AssociatesService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("rd/associates")
@RestController
public class AssociatesRestApi {
	
	@Autowired
	private AssociatesService associatesService;

	@PostMapping
	public ResponseEntity<AssociatesDTO> addAssociate(@Valid @RequestBody AssociatesDTO associatesDTO)
			throws AssociatesException {
		log.info("adding associate {}", associatesDTO.toString());
		return new ResponseEntity<>(associatesService.addAssociate(associatesDTO), HttpStatus.CREATED);
	}

	@GetMapping("/{gender}")
	public ResponseEntity<List<AssociatesDTO>> getAssociatesByGender(@PathVariable String gender)
			throws AssociatesException {
		log.info("retrieving associates list by gender {}", gender);
		return new ResponseEntity<>(associatesService.getAssociatesByGender(gender), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<AssociatesDTO> updateAssociate(@Valid @RequestBody AssociatesDTO associatesDTO)
			throws AssociatesException {
		log.info("updating associate {}", associatesDTO.toString());
		return new ResponseEntity<>(associatesService.updateAssociate(associatesDTO), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteAssociateById(@PathVariable int id) throws AssociatesException {
		log.info("deleting associate of {}", id);
		associatesService.deleteAssociateById(id);
		return new ResponseEntity<>("Associate deleted successfully", HttpStatus.NO_CONTENT);
	}

}
