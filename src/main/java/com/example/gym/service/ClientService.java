package com.example.gym.service;

import com.example.gym.dto.ClientDTO;
import com.example.gym.model.Client;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public List<ClientDTO> getAllActiveClients() {
        return clientRepository.findByIsActiveTrue().stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    public ClientDTO getClientById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        if (!client.getIsActive()) {
            throw new ResourceNotFoundException("Client not found with id: " + id);
        }
        return modelMapper.map(client, ClientDTO.class);
    }

    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);
        client.setIsActive(true);
        Client savedClient = clientRepository.save(client);
        return modelMapper.map(savedClient, ClientDTO.class);
    }

    @Transactional
    public ClientDTO updateClient(Integer id, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        if (existingClient.getIsActive() == null || !existingClient.getIsActive()) {
            throw new ResourceNotFoundException("Client not found with id: " + id);
        }

        modelMapper.map(clientDTO, existingClient);
        Client updatedClient = clientRepository.save(existingClient);
        return modelMapper.map(updatedClient, ClientDTO.class);
    }

    @Transactional
    public void deactivateClient(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        client.setIsActive(false);
        clientRepository.save(client);
    }

    public List<ClientDTO> searchActiveClients(String name, String email) {
        return clientRepository.findByNameContainingOrEmailContainingAndIsActiveTrue(name, email).stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }
}