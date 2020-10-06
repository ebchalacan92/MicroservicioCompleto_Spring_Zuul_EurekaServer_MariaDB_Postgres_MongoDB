package com.formacionbdi.microservicios.commons.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface CommonService<E> {
	
	public Iterable<E> findAll();
	
	public Page<E> findAll(Pageable pageable);
	
	public Optional<E> findById(Long id);
	
	//Este método recibe el Alumno antes de que se guarde"Alumno" y manda el alumno ya guardado "alumno"
	public E save(E entity);
	
	//Este método no retorna nada borra por el id del alumno 
	public void deleteById(Long id);

}
