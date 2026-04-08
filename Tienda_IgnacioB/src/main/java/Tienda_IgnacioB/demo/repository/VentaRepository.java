package Tienda_IgnacioB.demo.repository;

import Tienda_IgnacioB.demo.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Integer>{
    
}
