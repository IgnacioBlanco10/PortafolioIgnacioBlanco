/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tienda_IgnacioB.demo.controller;

import Tienda_IgnacioB.demo.domain.Categoria; // Lo añadi semana 4
import Tienda_IgnacioB.demo.service.CategoriaService;
import jakarta.validation.Valid; // Lo añadi semana 4
import java.util.Locale; // Lo añadi semana 4
import java.util.Optional; // Lo añadi semana 4
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource; // Lo añadi semana 4
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // Lo añadi semana 4
import org.springframework.web.bind.annotation.PostMapping; // Lo añadi semana 4
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // Lo añadi semana 4
import org.springframework.web.multipart.MultipartFile; // Lo añadi semana 4
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Lo añadi semana 4
/**
 *
 * @author nacho
 */
@Controller
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        return "/categoria/listado";
    }
    
    @Autowired
    private MessageSource messageSource;

    @PostMapping("/guardar")
    public String guardar(@Valid Categoria categoria,@RequestParam MultipartFile imagenFile, RedirectAttributes redirectAttributes) {
        
        categoriaService.save(categoria,imagenFile);        
        redirectAttributes.addFlashAttribute("todoOk",messageSource.getMessage("mensaje.actualizado",null,Locale.getDefault()));
        
        return "redirect:/categoria/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idCategoria, RedirectAttributes redirectAttributes) {
        String titulo="todoOk";
        String detalle="mensaje.eliminado";
        try {
          categoriaService.delete(idCategoria);          
        } catch (IllegalArgumentException e) {            
            titulo="error"; // Captura la excepción de argumento inválido para el mensaje de "no existe"
            detalle="cateogira.error01";
        } catch (IllegalStateException e) {            
            titulo="error"; // Captura la excepción de estado ilegal para el mensaje de "datos asociados"
            detalle="cateogira.error02";            
        } catch (Exception e) {            
            titulo="error";  // Captura cualquier otra excepción inesperada
            detalle="cateogira.error03";
        }
        redirectAttributes.addFlashAttribute(titulo,messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}")    
    public String modificar(@PathVariable("idCategoria") Integer idCategoria, Model model, RedirectAttributes redirectAttributes) {
        Optional<Categoria> categoriaOpt = categoriaService.getCategoria(idCategoria);
        if (categoriaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("categoria.error01", null, Locale.getDefault()));
            return "redirect:/categoria/listado";
        }
        model.addAttribute("categoria", categoriaOpt.get()); //me ayuda a conectar el html con el código
        return "/categoria/modifica";
    }
}
