package com.epam.service.implementation;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.AssociatesDTO;
import com.epam.entity.Associates;
import com.epam.exceptions.AssociatesException;
import com.epam.repository.AssociatesRepository;
import com.epam.service.interfaces.AssociatesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AssociatesServiceImpl implements AssociatesService {
	
	@Autowired
	private AssociatesRepository associatesRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AssociatesDTO addAssociate(AssociatesDTO newAssociatesDTO) throws AssociatesException {
		Associates newAssociate = Optional
				.ofNullable(associatesRepository.save(modelMapper.map(newAssociatesDTO, Associates.class)))
				.orElseThrow(() -> new AssociatesException("failed to add an associate"));
		log.info("associate {} got created", newAssociate);
		return modelMapper.map(newAssociate, AssociatesDTO.class);
	}

	@Override
	public List<AssociatesDTO> getAssociatesByGender(String gender) {
		List<Associates> associates = associatesRepository.findByGender(gender);
		log.info("retrieving list of associates on gender {}", associates);
		return associates.stream().map(associate -> modelMapper.map(associate, AssociatesDTO.class)).toList();
	}

	@Override
	public AssociatesDTO updateAssociate(AssociatesDTO updateAssociatesDTO) throws AssociatesException {
		Associates updatedAssociate = Optional
				.ofNullable(associatesRepository.save(modelMapper.map(updateAssociatesDTO, Associates.class)))
				.orElseThrow(() -> new AssociatesException("failed to add an associate"));
		log.info("associate {} got updated", updatedAssociate);
		return modelMapper.map(updatedAssociate, AssociatesDTO.class);
	}

	@Override
	public void deleteAssociateById(int id) {
		associatesRepository.deleteById(id);
		log.info("associate of id {} got deleted", id);

	}
}
