package br.com.jluna.clients.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jluna.clients.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
