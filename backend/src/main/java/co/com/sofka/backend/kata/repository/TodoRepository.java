package co.com.sofka.backend.kata.repository;

import co.com.sofka.backend.kata.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
