package com.example.gym.controller;

import com.example.gym.dto.ClientDTO;
import com.example.gym.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Client Management", description = "Operations related to client management")
public class ClientController {

    private final ClientService clientService;

    @Operation(
            summary = "Get all active clients",
            description = "Retrieves a list of all currently active clients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of clients"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllActiveClients() {
        return ResponseEntity.ok(clientService.getAllActiveClients());
    }

    @Operation(
            summary = "Get client by ID",
            description = "Retrieves client details by their unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found and returned"),
                    @ApiResponse(responseCode = "404", description = "Client not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @Operation(
            summary = "Register new client",
            description = "Creates a new client record in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.createClient(clientDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update client information",
            description = "Updates existing client details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client successfully updated"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Client not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(
            @PathVariable Integer id,
            @Valid @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.updateClient(id, clientDTO));
    }

    @Operation(
            summary = "Deactivate client",
            description = "Marks a client as inactive in the system (soft delete)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Client successfully deactivated"),
                    @ApiResponse(responseCode = "404", description = "Client not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateClient(@PathVariable Integer id) {
        clientService.deactivateClient(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Search clients",
            description = "Searches clients by name or email with partial matching",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Search results returned"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<ClientDTO>> searchActiveClients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(clientService.searchActiveClients(name, email));
    }
}