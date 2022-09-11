package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserProperties;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private UserProperties userProperties;
    private UserRepository userRepository;
	private UserService userService;
	@Autowired
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
								  MyBean myBean,
								  MyBeanWithDependency myBeanWithDependency,
								  MyBeanWithProperties myBeanWithProperties,
								  UserProperties userProperties,
								  UserRepository userRepository,
								  UserService userService) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userProperties = userProperties;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	// Ejecuta una aplicacion
	@Override
	public void run(String... args) {
		saveUsersInDataBase();
		getInformationJpqLFromUser();
		saveWithErrorTransactional();
	}
	/*odos los usuarios que contienen la “U”, no importa si al incio o al final,
porque se usa los comodines “%%”,
para los que inicien con determinada letra es “letra%”,
para los que finalicen es “%letra”.*/
	private void getInformationJpqLFromUser(){
		/*
		LOGGER.info("usuario enontrado con el metodo findbyuseremail" +

				userRepository.findByUserEmail("user1@domain.com")
				.orElseThrow(()-> new RuntimeException("No se encontró el usuario")));

		userRepository.findAndSort("user", Sort.by("id").descending())
				.stream()//stream es bueno si se desea hacer operaciones complejas, pero si solo se va a iterar es mejor un foreach
				.forEach(LOGGER::info);
		//forEach(user -> LOGGER.info("Usuario con método sort" + user));

		userRepository.findByName("user2")
				.stream()
				.forEach(user->LOGGER.info("Usuario con query method"+user));

		LOGGER.info("usuario encontrado por email y nombre"
				+ userRepository.findByEmailAndName("user7@domain.com", "user7")
				.orElseThrow(()-> new RuntimeException("user not found")));
				//.stream()
				//.forEach(user -> LOGGER.info("usuario enontrado por email y nombre"+ user));
		userRepository.findByNameLike("%u%")
				.stream()
				.forEach(user -> LOGGER.info("usuario findByNameLike "+ user));

		userRepository.findByNameOrEmail(null,"user1@domain.com")
				.stream()
				.forEach(user -> LOGGER.info("usuario findByNameOrEmail "+ user));

		userRepository.findByBirthdateBetween(LocalDate.of(2021, 3, 1), LocalDate.of(2021, 8, 2))
				.stream()
				.forEach(user -> LOGGER.info("usuario con intervalo de fechas " + user));

		userRepository.findByNameContainingOrderByIdDesc("user")
				.stream()
				.forEach(user -> LOGGER.info("usuarios findByNameContainingOrderByIdDesc " + user));

		LOGGER.info("usersdto by getAllByBirthDateAndEmail " + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021, 9, 6),
						"marcos@domain.com")
				.orElseThrow(()->
						new RuntimeException("NO SE ENCONTRO EL USUARIO APARTIR DEL NAME PARAMETER")));
				//.stream()
				//.forEach(userDto -> LOGGER.info("usersdto by getAllByBirthDateAndEmail " + userDto));
	*/

	}

	private void saveWithErrorTransactional() {
		User test1 = new User("Test_1_Transactional", "Test_1_Transactional@domain.com", LocalDate.now());
		User test2 = new User("Test_2_Transactional", "Test_2_Transactional@domain.com", LocalDate.now());
		User test3 = new User("Test_3_Transactional", "Test_3_Transactional@domain.com", LocalDate.now());
		User test4 = new User("Test_4_Transactional", "Test_4_Transactional@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4);
		try {
			userService.saveTransactional(users);
		} catch (Exception e) {
			LOGGER.error("This is an exception in transactional method " + e);
		}
		userService.getAllUsers().stream()
				.forEach(user ->
				LOGGER.info("This is the user in transactional method" + user));
	}
	private void saveUsersInDataBase() {
		User user1 = new User("marcos", "marcos@domain.com", LocalDate.of(2021, 3, 13));
		User user2 = new User("juan", "juan@domain.com", LocalDate.of(2021, 12, 8));
		User user3 = new User("raquel", "raquel@domain.com", LocalDate.of(2021, 9, 6));
		User user4 = new User("user4", "user4@domain.com", LocalDate.of(2021, 6, 18));
		User user5 = new User("user5", "user5@domain.com", LocalDate.of(2021, 1, 1));
		User user6 = new User("user6", "user6@domain.com", LocalDate.of(2021, 7, 7));
		User user7 = new User("user7", "user7@domain.com", LocalDate.of(2021, 11, 12));
		User user8 = new User("user8", "user8@domain.com", LocalDate.of(2021, 2, 27));
		User user9 = new User("apolos", "apolos@domain.com", LocalDate.of(2021, 4, 10));
		List<User> list = Arrays.asList(user1,user2,user3, user4, user5, user6, user7, user8, user9);
		userRepository.saveAll(list);
		//list.forEach(userRepository::save);
	}


	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userProperties.getEmail() + " "+ userProperties.getPassword()+ userProperties.getAge());

		try{
			//error
		//	int value = 10/0;
		//	LOGGER.debug("Mi valor :" + value);
		}catch (Exception e){
		//	LOGGER.error("Esto es un error al dividir por 0" + e.getMessage());
		}
	}
}

