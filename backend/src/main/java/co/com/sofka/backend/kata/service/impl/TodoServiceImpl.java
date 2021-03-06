package co.com.sofka.backend.kata.service.impl;

import co.com.sofka.backend.kata.model.Todo;
import co.com.sofka.backend.kata.repository.TodoRepository;
import co.com.sofka.backend.kata.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    //listar servicio
    @Override
    public Iterable<Todo> list() {
        return todoRepository.findAll();
    }

    //guardar servicio
    @Override
    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    //eliminar servicio
    @Override
    public String delete(Long id) {

        todoRepository.deleteById(id);
        return "";
    }

    //obtener servicio
    @Override
    public Optional<Todo> getId(Long id) {
        return todoRepository.findById(id);
    }

    @Override
    public Todo update(Todo todo) {
        Todo exitsTodo = todoRepository.findById(todo.getId()).orElse(null);
        exitsTodo.setName(todo.getName());
        exitsTodo.setIsCompleted(todo.getIsCompleted());
        return todoRepository.save(exitsTodo);
    }
}
