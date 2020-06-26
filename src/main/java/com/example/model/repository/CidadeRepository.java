package com.example.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.beans.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{

	List<Cidade> findByNomeStartingWith(String letra);
	List<Cidade> findByLatitudeAndLongitude(int latitude, int longitude);
	
}
