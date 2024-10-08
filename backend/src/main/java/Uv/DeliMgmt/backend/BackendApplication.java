package Uv.DeliMgmt.backend;

import Uv.DeliMgmt.backend.Models.PermissionEntity;
import Uv.DeliMgmt.backend.Models.Role;
import Uv.DeliMgmt.backend.Models.RoleEntity;
import Uv.DeliMgmt.backend.Models.User;
import Uv.DeliMgmt.backend.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			PermissionEntity createPermission = PermissionEntity.builder()
					.Name("CREATE")
					.build();

			PermissionEntity readPermission = PermissionEntity.builder()
					.Name("READ")
					.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
					.Name("UPDATE")
					.build();

			PermissionEntity deletePermission = PermissionEntity.builder()
					.Name("DELETE")
					.build();

			PermissionEntity refactorPermission = PermissionEntity.builder()
					.Name("REFACTOR")
					.build();


			RoleEntity roleAdmin = RoleEntity.builder()
					.role(Role.ADMIN)
					.permissions(Set.of(createPermission,readPermission,updatePermission,deletePermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.role(Role.SELLER)
					.permissions(Set.of(createPermission,readPermission))
					.build();

			RoleEntity roleDeveloper = RoleEntity.builder()
					.role(Role.DEVELOPER)
					.permissions(Set.of(createPermission,readPermission,updatePermission,deletePermission,refactorPermission))
					.build();

			User userJulio = User.builder()
					.username("julio")
					.password("$2a$10$q6TYYsDw1kVAGYZDAx/6GuMUE4ic9knbPMuYflnBvHf/TBM0knkg6")
					.email("julio@gmail.com")
					.isEnabled(true)
					.roles(Set.of(roleDeveloper))
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.build();

			User userJuan = User.builder()
					.username("juan")
					.password("$2a$10$Km5tbHgWqx3NVTNUuxRbCuSAivM8EOY5Y6gmz10.QmZkLnXqruEVO")
					.email("juan@gmail.com")
					.isEnabled(true)
					.roles(Set.of(roleUser))
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.build();

			User userFelipe = User.builder()
					.username("Felipe")
					.password("43221")
					.email("Felipe@gmail.com")
					.isEnabled(true)
					.roles(Set.of(roleAdmin))
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.build();

			userRepository.saveAll(List.of(userJuan,userJulio,userFelipe));
		};
	}

}
