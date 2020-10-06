package com.formacionbdi.microservicios.app.usuarios.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formacionbdi.microservicios.app.usuarios.services.AlumnoService;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.CommonController;

/*sobre el nombre de la clase vamos a añadir @RestController marca como un controlador de tipo REST
que ofrece una respuesta Jason (xml)*/
@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService>{
	
	@GetMapping("/alumnos-por-curso")
	public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
		return ResponseEntity.ok(service.findAllById(ids));
	}
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id){
		
		Optional<Alumno> o = service.findById(id);
		
		if (o.isEmpty() || o.get().getFoto() == null) {
			return ResponseEntity.notFound().build();
			
		}
		Resource imagen = new ByteArrayResource(o.get().getFoto());
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(imagen);
	}
	
//	@Autowired
//	private AlumnoService service;
	
	//Metodo que me permite listar(@GetMapping es para mapear) me devuelve una lista de AlumnoService
	//ok() es para controlar los mensajes de error.
//	@GetMapping
//	public ResponseEntity<?> listar(){
//		return ResponseEntity.ok().body(service.findAll());
//		
//	}
	
	
	
	/*Método que igual mapea mediante una ruta con la variable id de nuestra clase entity
	@PathVariable extrae la variable de la ruta antes mencionada con id de tipo Long
	En caso de que el nombre del PathVariable no fuera el mismo que el nombre del entity
	debemos señalar en @PathVariable(value ó name ) el nombre al cual estamos haciendo referencia*/
//	@GetMapping("/{id}")
//	public ResponseEntity<?> ver(@PathVariable Long id){
//		Optional<Alumno> o = service.findById(id);
//		if(o.isEmpty()) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(o.get());
//	}
	
	/*@PostMapping tambien sirve para mapear pero es para el crear un elemento en la entidad (post)
	@RequestBody sirve para preservar los datos en este caso de tipo alumno
	el objeto que venga los guarde en el objeto alumno
	vamos a cambiar el ok por el satatus porque necesito llamar a CREATED de tipo status*/
//	@PostMapping
//	public ResponseEntity<?> crear(@RequestBody Alumno alumno){
//		Alumno alumnoDb= service.save(alumno);
//		return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDb);
//	}
	
	/*creamos un metodo editat de tipo put 
	  el cual necesitamos obtener los datos de un alumno atravez de un id para eso usamos el 
	  @RequestBody y el @PathVariable
	  luego buscamos el alumno por el id, validamos que exista si existe lo obtenemos y 
	  cambiamos los datos  y lo persitimos con el metodo save*/
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno , BindingResult result, @PathVariable Long id){

		if (result.hasErrors()) {
			return this.validar(result);
					}
		
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
	
	/*Metodo eliminar de tipo DeleteMapping el cual necesita una variable id para buscar
	  para eso usamos unicamente @PathVariable
	  luego unicamente lo borramos , inyectamos el metodo deletById de la clase AlumnoService
	  y devolvemos un arespuesta vacia  */
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> eliminar(@PathVariable Long id){
//		service.deleteById(id);
//		
//		return ResponseEntity.noContent().build();
//	}
	
	//Este metodo tiene por nombre de ruta filtrar que requiere de una variable de tipo String
	//busca el  metodo creado en en el repositorio pero lo llama desde el service
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok(service.findByNombreOrApellido(term));
	}

	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
		if (!archivo.isEmpty()) {
			alumno.setFoto(archivo.getBytes());
			
		}
		return super.crear(alumno, result);
	}
	
	
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> editarConFoto(@Valid  Alumno alumno , BindingResult result, @PathVariable Long id,
			 @RequestParam MultipartFile archivo) throws IOException{

		if (result.hasErrors()) {
			return this.validar(result);
					}
		
		Optional<Alumno> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Alumno alumnoDb= o.get();
		alumnoDb.setNombre(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setMail(alumno.getMail());
		
		if (!archivo.isEmpty()) {
			alumnoDb.setFoto(archivo.getBytes());
			
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
	}
	
	
}
