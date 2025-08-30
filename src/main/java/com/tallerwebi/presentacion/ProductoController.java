package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductoController {

    private final List<Producto> productos;

    public ProductoController() {

        this.productos = new ArrayList<>();

        Producto p1 = new Producto();
        p1.setNombre("Azúcar");
        p1.setDescripcion("Marca Chango, 1Kg");
        p1.setPrecio(2000.0);
        this.productos.add(p1);

        Producto p2 = new Producto();
        p2.setNombre("Agua");
        p2.setDescripcion("Marca VillaManaos, 2L");
        p2.setPrecio(1000.0);
        this.productos.add(p2);
    }
    @GetMapping("/productos")
    public ModelAndView listarProductos() {

        ModelMap modelo = new ModelMap();

        modelo.put("productos", this.productos);

        return new ModelAndView("productos",modelo);
    }

    public List<Producto> listarProductosHardcodeados() {
        return this.productos;
    }
    @GetMapping("/productos/{id}")
    public ModelAndView verDetalle(@PathVariable Long id) {

        Producto producto = buscarProductoPorId(id);

        ModelMap modelo = new ModelMap();
        if (producto != null) {
            modelo.put("producto", producto);
        }

        return new ModelAndView("detalle",modelo);
    }

    private Producto buscarProductoPorId(Long id) {
        for (Producto producto : productos) {
            if (producto.getId().equals(id)) {
                return producto;
            }
        }
        return null;
    }
}
