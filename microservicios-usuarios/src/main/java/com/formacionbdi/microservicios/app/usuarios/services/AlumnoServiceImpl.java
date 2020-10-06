package com.formacionbdi.microservicios.app.usuarios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.app.usuarios.client.CursoFeignClient;
import com.formacionbdi.microservicios.app.usuarios.models.repository.AlumnoRepository;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonServiceImpl;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

	@Autowired
	private CursoFeignClient clientCurso;

	@Transactional(readOnly = true)
	@Override
	public List<Alumno> findByNombreOrApellido(String term) {

		return repository.findByNombreOrApellido(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return repository.findAllById(ids);
	}

	@Override
	public void eliminarCursoAlumnoPorId(Long id) {
		clientCurso.eliminarCursoAlumnoPorId(id);

	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id);
		this.eliminarCursoAlumnoPorId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAll() {
		
		return repository.findAllByOrderByIdAsc();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Alumno> findAll(Pageable pageable) {
		
		return repository.findAllByOrderByIdAsc(pageable);
	}
	
	

	/*
	 * @Autowired private AlumnoRepository repository;
	 * 
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public Iterable<Alumno> findAll() {
	 * 
	 * return repository.findAll(); }
	 * 
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public Optional<Alumno> findById(Long id) {
	 * 
	 * return repository.findById(id); }
	 * 
	 * @Override
	 * 
	 * @Transactional public Alumno save(Alumno alumno) {
	 * 
	 * return repository.save(alumno); }
	 * 
	 * @Override
	 * 
	 * @Transactional public void deleteById(Long id) {
	 * 
	 * repository.deleteById(id);
	 * 
	 * }
	 */
}
