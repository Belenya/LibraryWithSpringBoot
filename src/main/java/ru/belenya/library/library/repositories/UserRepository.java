package ru.belenya.library.library.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.belenya.library.library.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
