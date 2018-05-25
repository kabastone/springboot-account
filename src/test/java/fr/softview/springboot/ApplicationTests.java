package fr.softview.springboot;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({AccountControllerTest.class, AccountServiceTests.class, 
	AccountRepositoryTests.class, OperationRepositoryTests.class})
public class ApplicationTests {


}
