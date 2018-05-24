package fr.softview.springboot;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import fr.softview.springboot.model.entity.OperationEntity;
import fr.softview.springboot.repository.OperationRepository;

@RunWith(SpringRunner.class)
public class OperationRepositoryTests {
	
	@Mock private OperationRepository operationRepo;
	OperationEntity operation;
	@Before
	public void setup() {
		operation = new OperationEntity();
		operation.setId(1);
		operation.setAccount("0001");
		operation.setAmount(20);
		operation.setBalance(30);
		operation.setType("DEPOSIT");
		operation.setDate("24/05/2018");
		
	}
	@Test
	public void saveTest() {
		Mockito.when(operationRepo.save(operation)).thenReturn(operation);
		assertEquals(operation, operationRepo.save(operation));
		
	}
	
	@Test
	public void findByAccountTest() {
		List<OperationEntity> operations = new ArrayList<OperationEntity>();
		operations.add(operation);
		Mockito.when(operationRepo.findByAccount("0001")).thenReturn(operations);
		assertEquals(operations, operationRepo.findByAccount("0001"));
		
	}

}
