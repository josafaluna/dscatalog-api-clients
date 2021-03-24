package br.com.jluna.clients.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.jluna.clients.dto.ClientDTO;
import br.com.jluna.clients.entities.Client;
import br.com.jluna.clients.repository.ClientRepository;
import br.com.jluna.clients.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	// paginação
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPagened(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(c -> new ClientDTO(c));
	}

	@Transactional(readOnly = true)
	public List<ClientDTO> listar() {
		List<Client> list = repository.findAll();
		return list.stream().map(c -> new ClientDTO(c)).collect(Collectors.toList());
	}

	// por ID
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> opt = repository.findById(id);
		Client cli = opt.orElseThrow(() -> new ResourceNotFoundException("ID Not Found: " + id));
		return new ClientDTO(cli);
	}

	// novo
	@Transactional
	public ClientDTO insert(@RequestBody ClientDTO dto) {
		var client = new Client();
		copyToEntity(dto, client);
		client = repository.save(client);

		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {

		try {
			var client = repository.getOne(id);
			copyToEntity(dto, client);
			client = repository.save(client);

			return new ClientDTO(client);

		} catch (Exception e) {
			throw new ResourceNotFoundException("ID not found " + id);
		}

	}

	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);

		} catch (Exception e) {
			throw new ResourceNotFoundException("ID not found " + id);
		}

	}

	private void copyToEntity(ClientDTO dto, Client client) {
		client.setBirthDate(dto.getBirthDate());
		client.setChildren(dto.getChildren());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setName(dto.getName());
	}

}
