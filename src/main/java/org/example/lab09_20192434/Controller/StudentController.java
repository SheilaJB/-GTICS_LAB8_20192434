package org.example.lab09_20192434.Controller;


import org.example.lab09_20192434.Entity.StudentsEntity;
import org.example.lab09_20192434.Repository.StudentsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    final StudentsRepository studentsRepository;

    public StudentController(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    //Listar estudiantes
    @GetMapping(value = {"/list", ""})
    public List<StudentsEntity> listarEstudiantes(){

        return studentsRepository.findAllStudents();
    }

    //Obtener
    @GetMapping(value = "/{id}")
    public ResponseEntity<HashMap<String, Object>> buscarEstudiante(@PathVariable int id){

        try{
            Optional<StudentsEntity> studentsEntity = studentsRepository.findById(id);
            HashMap<String, Object> response = new HashMap<>();

            if(studentsEntity.isPresent()){
                response.put("id", studentsEntity.get().getId());
                response.put("nombre", studentsEntity.get().getNombre());

            }else {
                response.put("id", "no se encuentra registrado");
            }
            return ResponseEntity.ok(response);
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().build();
        }
    }

    //Agregar estudiante
    @PostMapping(value = {"/"})
    public ResponseEntity<HashMap<String, Object>> crearEstudiante(
            @RequestBody StudentsEntity studentsEntity,
            @RequestParam(value = "id", required = false) boolean id){
        HashMap<String, Object> response = new HashMap<>();
        studentsRepository.save(studentsEntity);
        if(id){
            response.put("id", studentsEntity.getId());
        }

        response.put("estado", "creado");
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Actualizar
    @PutMapping(value = {"", "/studentAct"}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<HashMap<String, Object>> modificarEstudiante(@RequestBody  StudentsEntity students){
        HashMap<String, Object> response = new HashMap<>();

        if( students.getId()>0){
            Optional<StudentsEntity> opt = studentsRepository.findById(students.getId());
            if(opt.isPresent()){
                studentsRepository.save(students);
                response.put("id", students.getId());
                response.put("nombre", students.getNombre());
                response.put("GPA", "actualizado");
                return ResponseEntity.ok(response);
            }else {
                response.put("id", "no se encuentra registrado");
                response.put("GPA", "error");
                return ResponseEntity.badRequest().body(response);
            }
        }else{
            response.put("GPA", "error");
            response.put("id", "no se encuentra registrado");
            return ResponseEntity.badRequest().body(response);
        }
    }

    //Borrar
    @DeleteMapping("")
    public ResponseEntity<HashMap<String, Object>> eliminarEstudiante(@RequestParam int id){

        try{

            HashMap<String, Object> response = new HashMap<>();
            Optional<StudentsEntity> studentsEntity = studentsRepository.findById(id);
            if(studentsEntity.isPresent()){
                studentsRepository.delete(studentsEntity.get());
                response.put("result", "Todo bien");
            }else {
                response.put("result", "No esta bien");
                response.put("id", "no se encuentra registrado");

            }
            //actualizar la lista

            return ResponseEntity.ok(response);
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(null);
        }
    }


}
