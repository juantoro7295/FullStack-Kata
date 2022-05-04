package co.com.sofka.backend.kata.controller;

import co.com.sofka.backend.kata.model.Todo;
import co.com.sofka.backend.kata.service.impl.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/todo")
public class TodoController {

    @Autowired
    private TodoServiceImpl todoServiceImpl;

    @GetMapping("/list")
    public Iterable<Todo> list(){
        return todoServiceImpl.list();
    }
    @PostMapping("/save")
    public Todo save( @RequestBody Todo todo){
        return todoServiceImpl.save(todo);
    }
    @PutMapping("/update")
    public Todo update (@RequestBody Todo todo){
        if(todo.getId() != null){
            todoServiceImpl.save(todo);
        }
        throw new RuntimeException("No existe el todo");
    }
    @GetMapping("/get/{id}")
    public Optional<Todo> getId(@PathVariable("id") Long id){
        return todoServiceImpl.getId(id);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        todoServiceImpl.delete(id);
    }





}
