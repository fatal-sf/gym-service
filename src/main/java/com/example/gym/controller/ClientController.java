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
@Tag(name = "Управление клиентами", description = "API для управления клиентами тренажерного зала")
public class ClientController {

    private final ClientService clientService;

    @Operation(
            summary = "Получить всех активных клиентов",
            description = "Возвращает список всех активных клиентов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получен список клиентов"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllActiveClients() {
        return ResponseEntity.ok(clientService.getAllActiveClients());
    }

    @Operation(
            summary = "Получить клиента по его идентификатору",
            description = "Возвращает клиента по его идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Клиент найден и получен"),
                    @ApiResponse(responseCode = "404", description = "Клиент не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @Operation(
            summary = "Регистрация нового клиента",
            description = "Создает запись о клиенте в системе",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Клиент успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.createClient(clientDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление информации о клиенте",
            description = "Обновляет данные существующих клиентов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Клиент успешно обновлен"),
                    @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
                    @ApiResponse(responseCode = "404", description = "Клиент не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(
            @PathVariable Integer id,
            @Valid @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.updateClient(id, clientDTO));
    }

    @Operation(
            summary = "Деактивировать клиента",
            description = "Отмечает клиента как неактивного в системе (soft delete)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Клиент успешно деактивирован"),
                    @ApiResponse(responseCode = "404", description = "Клиент не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateClient(@PathVariable Integer id) {
        clientService.deactivateClient(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Поиск клиентов",
            description = "Поиск клиентов по имени или адресу электронной почты с частичным совпадением",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Результаты поиска возвращены"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<ClientDTO>> searchActiveClients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(clientService.searchActiveClients(name, email));
    }
}