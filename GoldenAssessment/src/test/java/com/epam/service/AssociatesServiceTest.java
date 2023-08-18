package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.dto.AssociatesDTO;
import com.epam.dto.BatchesDTO;
import com.epam.entity.Associates;
import com.epam.entity.Batches;
import com.epam.exceptions.AssociatesException;
import com.epam.repository.AssociatesRepository;
import com.epam.service.implementation.AssociatesServiceImpl;

@ExtendWith(MockitoExtension.class)
class AssociatesServiceTest {
	@Mock
	private AssociatesRepository associatesRepository;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private AssociatesServiceImpl associatesServiceImpl;
	
	@Test
	void getAssociatesByGender() throws AssociatesException {
		Associates associate = new Associates(1, "Name", "email","G","clg","ACTIVE",new Batches());
		AssociatesDTO dto = new AssociatesDTO(1, "Name", "email","G","clg","ACTIVE",new BatchesDTO());
		List<Associates> list = List.of(associate);
		Mockito.when(modelMapper.map(associate, AssociatesDTO.class)).thenReturn(dto);
		Mockito.when(associatesRepository.findByGender(anyString())).thenReturn(list);
		List<AssociatesDTO> savedMentee = associatesServiceImpl.getAssociatesByGender(anyString());
		assertEquals(savedMentee.size(),list.size());
		Mockito.verify(associatesRepository, times(1)).findByGender(anyString());
		
	}

	@Test
	void addAssociateSuccess() throws AssociatesException {
		Associates associate = new Associates(1, "Name", "email","G","clg","ACTIVE",new Batches());
		AssociatesDTO dto = new AssociatesDTO(1, "Name", "email","G","clg","ACTIVE",new BatchesDTO());
		Mockito.when(modelMapper.map(associate, AssociatesDTO.class)).thenReturn(dto);
		Mockito.when(associatesRepository.save(associate)).thenReturn(associate);
		Mockito.when(modelMapper.map(dto, Associates.class)).thenReturn(associate);
		AssociatesDTO addedAssociate = associatesServiceImpl.addAssociate(dto);
		assertEquals(dto, addedAssociate);
		Mockito.verify(associatesRepository, times(1)).save(any(Associates.class));
	}

	@Test
	void addAssociateFail() throws AssociatesException {
		Associates associate = new Associates();
		AssociatesDTO dto = new AssociatesDTO();
		Mockito.when(modelMapper.map(dto, Associates.class)).thenReturn(associate);
		Mockito.when(associatesRepository.save(any(Associates.class))).thenReturn(null);
		assertThrows(AssociatesException.class, () -> {
			associatesServiceImpl.addAssociate(dto);
		});
	}

	@Test
	void updateAssociateSuccess() throws AssociatesException {
		Associates associate = new Associates();
		AssociatesDTO dto = new AssociatesDTO(1, "Name", "email","G","clg","ACTIVE",new BatchesDTO());
		associate.setStatus("INACTIVE");
		associate.setBatches(new Batches());
		associate.setCollege("VFSTR University");
		associate.setEmail("email@email.com");
		associate.setGender("F");
		associate.setName("Jacoco");
		associate.setId(1);
		Mockito.when(modelMapper.map(associate, AssociatesDTO.class)).thenReturn(dto);
		Mockito.when(associatesRepository.save(associate)).thenReturn(associate);
		Mockito.when(modelMapper.map(dto, Associates.class)).thenReturn(associate);
		AssociatesDTO updateAssociate = associatesServiceImpl.updateAssociate(dto);
		assertEquals(dto, updateAssociate);
		Mockito.verify(associatesRepository, times(1)).save(any(Associates.class));
	}

	@Test
	void updateAssociateFail() throws AssociatesException {
		Associates associate = new Associates(1, "Name", "email","G","clg","ACTIVE",new Batches());
		AssociatesDTO dto = new AssociatesDTO(1, "Name", "email","G","clg","ACTIVE",new BatchesDTO());
		Mockito.when(modelMapper.map(dto, Associates.class)).thenReturn(associate);
		Mockito.when(associatesRepository.save(any(Associates.class))).thenReturn(null);
		assertThrows(AssociatesException.class, () -> {
			associatesServiceImpl.updateAssociate(dto);
		});
	}
	@Test
	void deleteAssociateSucesss() {
		Mockito.doNothing().when(associatesRepository).deleteById(anyInt());
		associatesServiceImpl.deleteAssociateById(anyInt());
		Mockito.verify(associatesRepository).deleteById(anyInt());
	}
}
