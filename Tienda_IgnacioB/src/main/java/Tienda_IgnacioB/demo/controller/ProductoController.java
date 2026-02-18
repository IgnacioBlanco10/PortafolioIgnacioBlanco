/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tienda_IgnacioB.demo.controller;

import Tienda_IgnacioB.demo.domain.Categoria; //tuve que añadirlo en semana 5
import Tienda_IgnacioB.demo.domain.Producto; // Lo añadi semana 4
import Tienda_IgnacioB.demo.service.CategoriaService;
import Tienda_IgnacioB.demo.service.ProductoService;
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
@RequestMapping("/producto")
public class ProductoController { 
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private CategoriaService categoriaService;
    private final MessageSource messageSource;

    public ProductoController(ProductoService productoService,
                              CategoriaService categoriaService,
                              MessageSource messageSource) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.messageSource = messageSource;
    }
    
    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);
        
        
        return "/producto/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Producto producto,
                          @RequestParam MultipartFile imagenFile,
                          RedirectAttributes redirectAttributes) {
        productoService.save(producto, imagenFile);
        redirectAttributes.addFlashAttribute(
                "todoOK",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault())
        );
        return "redirect:/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idProducto,
                           RedirectAttributes redirectAttributes) {
        String titulo = "todoOK";
        String detalle = "mensaje.eliminado";
        try {
            productoService.delete(idProducto);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "categoria.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "categoria.error02";
        } catch (Exception e) {
            titulo = "error";
            detalle = "categoria.error03";
        }
        redirectAttributes.addFlashAttribute(
                titulo,
                messageSource.getMessage(detalle, null, Locale.getDefault())
        );
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String modificar(@PathVariable("idProducto") Integer idProducto,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        Optional<Producto> productoOpt = productoService.getProducto(idProducto);
        if (productoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    messageSource.getMessage("producto.error01", null, Locale.getDefault())
            );
            return "redirect:/producto/listado";
        }
        model.addAttribute("producto", productoOpt.get());
        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);
        return "/producto/modifica";
    }
}