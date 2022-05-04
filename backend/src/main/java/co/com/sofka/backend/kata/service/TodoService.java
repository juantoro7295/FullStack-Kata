package co.com.sofka.backend.kata.service;

import co.com.sofka.backend.kata.model.Todo;

import java.util.Optional;

public interface TodoService {
    //listar
    Iterable<Todo> list();
    //guardar
    Todo save(Todo todo);
    //eliminar
    String delete(Long id);
    //obtener
    Optional<Todo> getId(Long id);
    //actualizar
    Todo update(Todo todo);

}
