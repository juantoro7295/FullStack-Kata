package co.com.sofka.backend.kata.controller;

import co.com.sofka.backend.kata.model.Todo;
import co.com.sofka.backend.kata.service.impl.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/todo")
public class TodoController {

    @Autowired
    private TodoServiceImpl todoServiceImpl;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        Iterable<Todo> list = todoServiceImpl.list();
        return (list != null) ? new ResponseEntity<>(list, HttpStatus.OK) :
                new ResponseEntity<>("no hay informacion", HttpStatus.NOT_FOUND);

    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Todo todo) {
        return (todo != null) ? new ResponseEntity<>(todoServiceImpl.save(todo), HttpStatus.CREATED):
                new ResponseEntity<>("No se encuentra el cuerpo del todo", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Todo todo) {
       Optional<Todo> todoExist = todoServiceImpl.getId(todo.getId());
       return (todoExist.isPresent()) ? new ResponseEntity<>(todoServiceImpl.update(todo), HttpStatus.OK) :
               new ResponseEntity<>("No existe el todo a actualizar", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getId(@PathVariable("id") Long id) {
        Optional<Todo> todoId = todoServiceImpl.getId(id);
        return (todoId.isPresent()) ? new ResponseEntity<>(todoId, HttpStatus.OK) :
                new ResponseEntity<>("No existe el id que ingresaste", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Todo> todoId = todoServiceImpl.getId(id);
        return (todoId.isPresent()) ? new ResponseEntity<>(todoServiceImpl.delete(id), HttpStatus.NO_CONTENT):
                new ResponseEntity<>("No existe el id que quieres eliminar", HttpStatus.NOT_FOUND);

    }


}
