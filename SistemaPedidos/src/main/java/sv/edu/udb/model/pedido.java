package sv.edu.udb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;
    @NotNull(message = "Especifique cuanto es el total")
    private double total;
    //Esta es opcional, pero es recomendable ocuparla
    private String fechaPedido;
    @NotBlank(message = "Especifique el estado del pedido")
    private String estado;
}
