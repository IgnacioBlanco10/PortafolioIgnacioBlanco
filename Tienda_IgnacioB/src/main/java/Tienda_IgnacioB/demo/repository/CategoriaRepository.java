/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Tienda_IgnacioB.demo.repository;

import Tienda_IgnacioB.demo.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author nacho
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
    public List<Categoria> findByActivoTrue();
    
}
