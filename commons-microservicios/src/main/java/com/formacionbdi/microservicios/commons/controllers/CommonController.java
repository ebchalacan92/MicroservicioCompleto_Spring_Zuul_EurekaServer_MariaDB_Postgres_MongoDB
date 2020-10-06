package com.formacionbdi.microservicios.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.commons.services.CommonService;

//import com.formacionbdi.microservicios.app.usuarios.models.entity.Alumno;
//import com.formacionbdi.microservicios.app.usuarios.services.AlumnoService;

/*sobre el nombre de la clase vamos a añadir @RestController marca como un controlador de tipo REST
que ofrece una respuesta Jason (xml)*/
//@RestController
//@CrossOrigin({"http://localhost:4200"})
public class CommonController<E, S extends CommonService<E>> {
	
	@Autowired
	protected S service;
	
	//Metodo que me permite listar(@GetMapping es para mapear) me devuelve una lista de AlumnoService
	//ok() es para controlar los mensajes de error.
	
	@GetMapping
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok().body(service.findAll());
		
	}
	//Paginacion
	@GetMapping("/pagina")
	public ResponseEntity<?> listar(Pageable pageable){
		return ResponseEntity.ok().body(service.findAll(pageable));
		
	}
	
	/*Método que igual mapea mediante una ruta con la variable id de nuestra clase entity
	@PathVariable extrae la variable de la ruta antes mencionada con id de tipo Long
	En caso de que el nombre del PathVariable no fuera el mismo que el nombre del entity
	debemos señalar en @PathVariable(value ó name ) el nombre al cual estamos haciendo referencia*/
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<E> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(o.get());
	}
	
	/*@PostMapping tambien sirve para mapear pero es para el crear un elemento en la entidad (post)
	@RequestBody sirve para preservar los datos en este caso de tipo alumno
	el objeto que venga los guarde en el objeto alumno
	vamos a cambiar el ok por el satatus porque necesito llamar a CREATED de tipo status*/
	@PostMapping
	public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result){
		
		if (result.hasErrors()) {
			return this.validar(result);
					}
		E entityDb= service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	/*creamos un metodo editat de tipo put 
	  el cual necesitamos obtener los datos de un alumno atravez de un id para eso usamos el 
	  @RequestBody y el @PathVariable
	  luego buscamos el alumno por el id, validamos que exista si existe lo obtenemos y 
	  cambiamos los datos  y lo persitimos con el metodo save*/
	
	/*
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Alumno alumno, @PathVariable Long id){
		Optional<Alumno> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Alumno alumnoDb= o.get();
		alumnoDb.setNombre(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setMail(alumno.getMail());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
	}
	*/
	
	/*Metodo eliminar de tipo DeleteMapping el cual necesita una variable id para buscar
	  para eso usamos unicamente @PathVariable
	  luego unicamente lo borramos , inyectamos el metodo deletById de la clase AlumnoService
	  y devolvemos un arespuesta vacia  */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String,Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err ->{
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
}
