package com.example.gym;


import com.example.gym.dto.ClientDTO;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.model.Client;
import com.example.gym.repository.ClientRepository;
import com.example.gym.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    void updateClient_WithValidData_ShouldUpdateAndReturnClientDTO() {
        // Подготовка тестовых данных
        Integer clientId = 1;
        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setIsActive(true);

        ClientDTO inputDTO = new ClientDTO();
        inputDTO.setName("Updated Name");
        inputDTO.setEmail("updated@example.com");

        Client updatedClient = new Client();
        updatedClient.setId(clientId);
        updatedClient.setName("Updated Name");
        updatedClient.setEmail("updated@example.com");
        updatedClient.setIsActive(true);

        ClientDTO outputDTO = new ClientDTO();
        outputDTO.setId(clientId);
        outputDTO.setName("Updated Name");
        outputDTO.setEmail("updated@example.com");
        outputDTO.setIsActive(true);

        // Настройка моков
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(existingClient)).thenReturn(updatedClient);

        // Важно: мокаем конкретные вызовы map с правильными аргументами
        doAnswer(invocation -> {
            ClientDTO source = invocation.getArgument(0);
            Client destination = invocation.getArgument(1);
            if (source != null && destination != null) {
                destination.setName(source.getName());
                destination.setEmail(source.getEmail());
            }
            return null;
        }).when(modelMapper).map(inputDTO, existingClient);

        when(modelMapper.map(updatedClient, ClientDTO.class)).thenReturn(outputDTO);

        // Вызов тестируемого метода
        ClientDTO result = clientService.updateClient(clientId, inputDTO);

        // Проверки
        assertNotNull(result);
        assertEquals(outputDTO, result);
        assertEquals("Updated Name", existingClient.getName());
        assertEquals("updated@example.com", existingClient.getEmail());
        verify(clientRepository, times(1)).save(existingClient);
    }

    // Остальные тесты остаются без изменений
    @Test
    void getAllActiveClients_ShouldReturnListOfActiveClients() {
        Client activeClient = new Client();
        activeClient.setIsActive(true);
        ClientDTO clientDTO = new ClientDTO();

        when(clientRepository.findByIsActiveTrue()).thenReturn(Collections.singletonList(activeClient));
        when(modelMapper.map(activeClient, ClientDTO.class)).thenReturn(clientDTO);

        List<ClientDTO> result = clientService.getAllActiveClients();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(clientRepository, times(1)).findByIsActiveTrue();
    }

    @Test
    void getClientById_WithActiveClient_ShouldReturnClientDTO() {
        Integer clientId = 1;
        Client client = new Client();
        client.setIsActive(true);
        ClientDTO clientDTO = new ClientDTO();

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(modelMapper.map(client, ClientDTO.class)).thenReturn(clientDTO);

        ClientDTO result = clientService.getClientById(clientId);

        assertNotNull(result);
        assertEquals(clientDTO, result);
    }

    @Test
    void createClient_ShouldSaveAndReturnClientDTO() {
        ClientDTO inputDTO = new ClientDTO();
        Client client = new Client();
        Client savedClient = new Client();
        ClientDTO outputDTO = new ClientDTO();

        when(modelMapper.map(inputDTO, Client.class)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(savedClient);
        when(modelMapper.map(savedClient, ClientDTO.class)).thenReturn(outputDTO);

        ClientDTO result = clientService.createClient(inputDTO);

        assertNotNull(result);
        assertEquals(outputDTO, result);
        assertTrue(client.getIsActive());
    }

    @Test
    void deactivateClient_ShouldSetActiveFalse() {
        Integer clientId = 1;
        Client client = new Client();
        client.setIsActive(true);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        clientService.deactivateClient(clientId);

        assertFalse(client.getIsActive());
        verify(clientRepository, times(1)).save(client);
    }
}