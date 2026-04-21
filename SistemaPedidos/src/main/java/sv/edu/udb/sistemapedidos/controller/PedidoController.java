package sv.edu.udb.sistemapedidos.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.sistemapedidos.model.pedido;
import sv.edu.udb.sistemapedidos.service.PedidoService;

import java.util.HashMap;
import java.util.Map;

// Controller principal para manejar pedidos via REST API
@AllArgsConstructor
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    // Inyectamos el servicio que contiene la lógica de negocio
    private PedidoService pedidoService;

    // Crea un nuevo pedido
    // POST /api/pedidos
    @PostMapping
    public ResponseEntity<?> crearPedido(@Valid @RequestBody pedido pedido) {
        try {
            // El servicio se encarga de guardar en BD y disparar eventos
            pedido guardado = pedidoService.crearPedido(pedido);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Si falla algo específico (ej: validación), devolvemos el error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Si falla algo inesperado, devolvemos error genérico
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtiene un pedido por su ID
    // GET /api/pedidos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPedidoPorId(@PathVariable Long id) {
        try {
            pedido pedido = pedidoService.obtenerPedidoPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            // El servicio lanza excepción si no encuentra el pedido
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtiene todos los pedidos
    // GET /api/pedidos
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosPedidos() {
        try {
            Iterable<pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualiza un pedido existente
    // PUT /api/pedidos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPedido(@PathVariable Long id, @Valid @RequestBody pedido pedido) {
        try {
            pedido actualizado = pedidoService.actualizarPedido(id, pedido);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Elimina un pedido por ID
    // DELETE /api/pedidos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long id) {
        try {
            pedidoService.eliminarPedido(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Pedido eliminado exitosamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Maneja errores de validación de campos
    // Se activa cuando @Valid falla en los endpoints
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Error de validación");
        
        // Recorremos todos los errores de validación y los agregamos al response
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Manejo global de cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalExceptions(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Error inesperado");
        error.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
