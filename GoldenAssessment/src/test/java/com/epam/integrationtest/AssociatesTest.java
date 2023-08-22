package com.epam.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.epam.dto.AssociatesDTO;
import com.epam.dto.BatchesDTO;
import com.epam.entity.Associates;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssociatesTest {

	    @LocalServerPort
	    private int port;
	    private String baseUrl = "http://localhost";
	    private static RestTemplate restTemplate;
	    @Autowired
	    private AssocaiatesTestRepository associatesTestRepository;
	    @Autowired
	    private BatchesTestRepository batchesTestRepository;
	    
	    @BeforeAll
	    public static void init() {
	        restTemplate = new RestTemplate();
	    }

	    @BeforeEach
	    public void setUp() {
	    	associatesTestRepository.deleteAll();
	        batchesTestRepository.deleteAll();
	        baseUrl = baseUrl.concat(":").concat(port + "").concat("/rd/associates");
	    }

	    @Test
	    void testAddBatches() {
	        BatchesDTO batchesDTO=new BatchesDTO(1,"name","practice",new Date(),new Date());
	        AssociatesDTO associatesDTO = new AssociatesDTO(1,"Name","name@gmail.com","F","clg","ACTIVE",batchesDTO);
	        AssociatesDTO response = restTemplate.postForObject(baseUrl, associatesDTO,AssociatesDTO.class);
	        assertEquals("Name", response.getName());
	        assertEquals(1, associatesTestRepository.findAll().size());
	    }

	    @Test
	    @Sql(statements = "insert into associates (id, college, email, gender, name, status) values (14,'VFSTR','name1@gmail.com','M','name1','INACTIVE')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	    @Sql(statements = "DELETE FROM associates WHERE id = 14", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	    void getAssociatesByGender() {
	        List<AssociatesDTO> response = restTemplate.getForObject(baseUrl + "/{gender}", List.class, "M");
	        assertEquals(response.size(),associatesTestRepository.findAll().size());
	    }


//	    @Test
//	    @Sql(statements = "insert into associates (id, college, email, gender, name, status) values (14,'VFSTR','name1@gmail.com','M','name1','INACTIVE')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//		@Sql(statements = "update associates set email='name2@gmail.com' where id =14", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//		void updateAssociatesByGender() {
//	        BatchesDTO batchesDTO=new BatchesDTO(4,"name1","practice",new Date(),new Date());
//	        AssociatesDTO associatesDTO = new AssociatesDTO(14,"name1","name2@gmail.com","F","clg","ACTIVE",batchesDTO);
//	        restTemplate.put(baseUrl, associatesDTO,AssociatesDTO.class);
//	        Associates associates = associatesTestRepository.findById(14).get();
//	        assertEquals("name2@gmail.com", associates.getEmail());
//	    }

	    @Test
	    @Sql(statements = "INSERT INTO associates (id, college, email, gender, name, status) VALUES (5, 'VFSTR', 'name3@gmail.com', 'F', 'name3', 'ACTIVE')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	    void deleteAssociate() throws Exception {
	        restTemplate.delete(baseUrl+"/{id}", 6);
	        assertEquals(Optional.empty(),associatesTestRepository.findById(6));
	    }
}
