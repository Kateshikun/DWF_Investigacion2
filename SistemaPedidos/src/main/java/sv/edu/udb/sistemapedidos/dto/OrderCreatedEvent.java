package sv.edu.udb.sistemapedidos.dto;

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {
    private Long id;
    private String cliente;
    private String producto;
    private Double total;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(Long id, String cliente, String producto, Double total) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.total = total;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}
