package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;

	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
									 MyBean myBean,
								     MyBeanWithDependency myBeanWithDependency,
									 MyBeanWithProperties myBeanWithProperties,
								  	 UserPojo userPojo,
								     UserRepository userRepository,
								     UserService userService) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}
	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args)  { //throws Exception
		//previousClass();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		User test1 = new User("test1Transactional1", "TestTransactional1@google.com", LocalDate.now());
		User test2 = new User("test2Transactional1", "test2Transactional1@google.com", LocalDate.now());
		User test3 = new User("test3Transactional1", "test3Transactional1@google.com", LocalDate.now());
		User test4 = new User("test4Transactional1", "test4Transactional1@google.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4);
		userService.saveTransactional(users);

		userService.getAllUsers()
				.stream()
				.forEach(
					user -> LOGGER.info("this is the user inside the method transactional" + user)
				);
	}

	private void getInformationJpqlFromUser(){
//		LOGGER.info("User with method findByUserEmail" +
//				userRepository.findByUserEmail("daniela@google.com")
//						.orElseThrow( () -> new RuntimeException("It was not found user") ));
//
//		userRepository.findAndSort("user", Sort.by("id").ascending())
//				.stream()
//				.forEach(user -> LOGGER.info("user with method sort " + user));
//
//		userRepository.findByName("Luis")
//				.stream()
//				.forEach( user -> LOGGER.info("User with query method " + user) );
//
//		LOGGER.info("User with query method findByEmailAndName " + userRepository.findByEmailAndName("daniela@google.com", "Daniela")
//				.orElseThrow( () -> new RuntimeException( "User not found" ) ));
//
//		userRepository.findByNameLike("%u%")
//				.stream()
//				.forEach( user -> LOGGER.info("user findByNameLike " + user) );
//
//		userRepository.findByNameOrEmail("user5", "user10@google.com")
//				.stream()
//				.forEach( user -> LOGGER.info("user findByNameOrEmail " + user) );

		userRepository
				.findByBirthDateBetween(
						LocalDate.of(2021, 3, 1),
						LocalDate.of(2021, 4, 2)
				)
				.stream()
				.forEach( user -> LOGGER.info("user findByBirthDateBetween " + user) );

		userRepository
				.findByNameContainingOrderByIdDesc("user")
				.stream()
				.forEach( user -> LOGGER.info("user founded with like and order by " + user) );

		LOGGER.info("The user from named parameters are: " + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021, 07, 23),
		"daniela@google.com")
			.orElseThrow( ()-> new RuntimeException("user not found by named parameter") ));

	}

	private void saveUsersInDataBase(){
		User user1 = new User("Luis", "luis@google.com", LocalDate.of(2021, 03, 20));
		User user2 = new User("Zoey", "zoey@google.com", LocalDate.of(2021, 05, 21));
		User user3 = new User("Daniela", "daniela@google.com", LocalDate.of(2021, 07, 23));
		User user4 = new User("user4", "user4@google.com", LocalDate.of(2021, 11, 7));
		User user5 = new User("user5", "user5@google.com", LocalDate.of(2021, 2, 11));
		User user6 = new User("user6", "user6@google.com", LocalDate.of(2021, 3, 25));
		User user7 = new User("user7", "user7@google.com", LocalDate.of(2021, 4, 11));
		User user8 = new User("user8", "user8@google.com", LocalDate.of(2021, 05, 12));
		User user9 = new User("user9", "user9@google.com", LocalDate.of(2021, 8, 22));
		User user10 = new User("user10", "user10@google.com", LocalDate.of(2021, 1, 3));
		User user11 = new User("user11", "user11@google.com", LocalDate.of(2021, 2, 12));
		User user12 = new User("user12", "user12@google.com", LocalDate.of(2021, 2, 2));
		List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12);
		list.stream().forEach(userRepository::save);
	}

	private void previousClass(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());
		try {
			//error
			int value = 10/0;
			LOGGER.debug("My value :" + value);
		} catch (Exception e) {
			LOGGER.error("This is an error when dividing by zero: " + e.getMessage());
		}
	}
}
