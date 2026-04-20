package sv.edu.udb.sistemapedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.sistemapedidos.model.pedido;

public interface PedidoRepository extends JpaRepository<pedido, Long> {
}
