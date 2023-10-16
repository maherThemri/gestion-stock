package com.thamri.gestionstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thamri.gestionstock.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>{

}
