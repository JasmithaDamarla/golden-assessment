package com.epam.restapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.AssociatesDTO;
import com.epam.dto.BatchesDTO;
import com.epam.exceptions.AssociatesException;
import com.epam.service.interfaces.AssociatesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(AssociatesRestApi.class)
class AssociatesRestApiTest {
	@MockBean
	private AssociatesService associatesService;
	@Autowired
	private MockMvc mockMvc;
	
	@Test
    void viewAssociatesByGender() throws Exception {
		AssociatesDTO dto = new AssociatesDTO(1, "Name", "email","M","clg","ACTIVE",new BatchesDTO());
		List<AssociatesDTO> list = List.of(dto);
        Mockito.when(associatesService.getAssociatesByGender("M")).thenReturn(list);
        mockMvc.perform(get("/rd/associates/{gender}","M")).andExpect(status().isOk());
    }
	
	@Test
	void addAssociate() throws Exception {
		AssociatesDTO associatesDTO = new AssociatesDTO(1, "Name", "email","M","clg","ACTIVE",new BatchesDTO());
        Mockito.when(associatesService.addAssociate(any(AssociatesDTO.class))).thenReturn(associatesDTO);
        mockMvc.perform(post("/rd/associates").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(associatesDTO))).andExpect(status().isCreated());
	}
	
	@Test
	void updateAssociate() throws Exception {
		AssociatesDTO associatesDTO = new AssociatesDTO(1, "Name", "email","M","clg","ACTIVE",new BatchesDTO());
		Mockito.when(associatesService.updateAssociate(any(AssociatesDTO.class))).thenReturn(associatesDTO);
        mockMvc.perform(put("/rd/associates").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(associatesDTO))).andExpect(status().isOk());
	}
	
	@Test
	void deleteAssociateById() throws Exception {
        Mockito.doNothing().when(associatesService).deleteAssociateById(anyInt());
        mockMvc.perform(delete("/rd/associates/{id}",anyInt())).andExpect(status().isNoContent());
	}
	
	
	@Test
	void associatesException() throws Exception {
		AssociatesDTO associatesDTO = new AssociatesDTO(1, "Name", "email","M","clg","ACTIVE",new BatchesDTO());
		Mockito.doThrow(new AssociatesException("not found")).when(associatesService).updateAssociate(any());
		mockMvc.perform(put("/rd/associates").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(associatesDTO))).andExpect(status().isBadRequest());
	}
	
	@Test
	void methodArgumentNotValidException() throws Exception {
		AssociatesDTO associatesDTO = new AssociatesDTO();
		Mockito.when(associatesService.addAssociate(any(AssociatesDTO.class))).thenReturn(associatesDTO);
		mockMvc.perform(post("/rd/associates").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(associatesDTO)))
				.andExpect(status().isNotAcceptable());
	}
	
	@Test
	void methodTypeMismatchException() throws Exception {
		Mockito.doNothing().when(associatesService).deleteAssociateById(anyInt());
		mockMvc.perform(delete("/rd/associates/{id}","io").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void dataIntegrityException() throws Exception {
		AssociatesDTO associatesDTO = new AssociatesDTO(1, "Name", "email","M","clg","ACTIVE",new BatchesDTO());
		Mockito.when(associatesService.addAssociate(any(AssociatesDTO.class))).thenThrow(new DataIntegrityViolationException("FK exception"));
		mockMvc.perform(post("/rd/associates").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(associatesDTO)))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	void runTimeException() throws Exception {
		AssociatesDTO associatesDTO = new AssociatesDTO(1, "Name", "email","M","clg","ACTIVE",new BatchesDTO());
		Mockito.when(associatesService.addAssociate(any(AssociatesDTO.class))).thenThrow(new RuntimeException("run time exception"));
		mockMvc.perform(post("/rd/associates").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(associatesDTO)))
				.andExpect(status().isInternalServerError());
	}
	
	
}
