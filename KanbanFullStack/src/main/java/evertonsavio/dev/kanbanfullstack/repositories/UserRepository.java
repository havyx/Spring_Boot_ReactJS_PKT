package evertonsavio.dev.kanbanfullstack.repositories;

import evertonsavio.dev.kanbanfullstack.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}