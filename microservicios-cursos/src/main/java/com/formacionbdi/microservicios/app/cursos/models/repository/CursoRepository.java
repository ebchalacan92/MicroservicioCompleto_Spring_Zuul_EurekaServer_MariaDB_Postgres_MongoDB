package com.formacionbdi.microservicios.app.cursos.models.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;

public interface  CursoRepository extends PagingAndSortingRepository<Curso, Long> {
	
	//Query que permite la busqueda del curso atravez del id del alumno 
	//el fetch se usa para poblar la lista de alumnos en una sola consulta  ya no es un proxy 
	// ademas para preguntar el alumno atravez del id
	@Query("select c from Curso c join  fetch c.cursoAlumnos a where a.alumnoId=?1")
	public Curso findCursoByAlumnoId(Long id);
	
	@Modifying
	@Query("delete from CursoAlumno ca where ca.alumnoId=?1")
	public void eliminarCursoAlumnoPorId(Long id);
}
