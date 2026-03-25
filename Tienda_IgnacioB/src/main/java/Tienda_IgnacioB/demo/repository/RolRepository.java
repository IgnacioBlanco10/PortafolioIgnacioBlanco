/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Tienda_IgnacioB.demo.repository;

import Tienda_IgnacioB.demo.domain.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nacho
 */
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    public Optional<Rol> findByRol(String rol);
    
}
