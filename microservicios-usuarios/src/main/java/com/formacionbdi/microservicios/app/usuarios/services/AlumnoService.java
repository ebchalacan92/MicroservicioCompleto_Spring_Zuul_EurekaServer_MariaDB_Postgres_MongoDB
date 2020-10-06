package com.formacionbdi.microservicios.app.usuarios.services;

import java.util.List;



import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonService;

public interface AlumnoService extends CommonService<Alumno> {
	
	/* Se comento debido a que en el video 21 Creamos una clase generica de metodos
	 
	public Iterable<Alumno> findAll();
	
	public Optional<Alumno> findById(Long id);
	
	//Este método recibe el Alumno antes de que se guarde"Alumno" y manda el alumno ya guardado "alumno"
	public Alumno save(Alumno alumno);
	
	//Este método no retorna nada borra por el id del alumno 
	public void deleteById(Long id);
	*/
	
	public List<Alumno> findByNombreOrApellido(String term);
	
	public Iterable<Alumno> findAllById(Iterable<Long> ids);
	
	public void eliminarCursoAlumnoPorId(Long id);

}
