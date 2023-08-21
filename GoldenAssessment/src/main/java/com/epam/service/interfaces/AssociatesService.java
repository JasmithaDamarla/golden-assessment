package com.epam.service.interfaces;

import java.util.List;

import com.epam.dto.AssociatesDTO;
import com.epam.exceptions.AssociatesException;

public interface AssociatesService {
	AssociatesDTO addAssociate(AssociatesDTO newAssociatesDTO) throws AssociatesException;
	List<AssociatesDTO> getAssociatesByGender(String gender);
	AssociatesDTO updateAssociate(AssociatesDTO updateAssociatesDTO) throws AssociatesException;
	void deleteAssociateById(int id);
}
