package com.example.appfinalcafe.Model;


import java.io.Serializable;
import java.util.Date;

public class Producto implements Serializable {
    private int id;
    private String nombre;
    private double precio;
    private int categoria;

    private int estado;

    public Producto(int id, String nombre, double precio, int categoria, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    public enum EnEstado {
        EnStock(0),
        SinStock(1);

        private final int id;

        EnEstado(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getEstadoNombre() {
            return this.toString();
        }

        public static Producto.EnEstado obtenerEstadoPorId(int id) {
            for (Producto.EnEstado estado : values()) {
                if (estado.getId() == id) {
                    return estado;
                }
            }
            return null; // Manejar el caso en que el ID no coincida con ningún estado
        }
    }

    public Producto() {

    }

    public String getEstadoNombre() {
        Producto.EnEstado estado = Producto.EnEstado.obtenerEstadoPorId(this.estado);
        return (estado != null) ? estado.getEstadoNombre() : "";
    }
    public enum EnCategoria {
        Cocina(0),
        Barra(1);

        private final int id;

        EnCategoria(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getCategoriaNombre() {
            return this.toString();
        }

        public static Producto.EnCategoria obtenerCategoriaPorId(int id) {
            for (Producto.EnCategoria categoria : values()) {
                if (categoria.getId() == id) {
                    return categoria;
                }
            }
            return null; // Manejar el caso en que el ID no coincida con ningún estado
        }
    }
    public String getCategoriaNombre() {
        Producto.EnCategoria categoria = Producto.EnCategoria.obtenerCategoriaPorId(this.categoria);
        return (categoria != null) ? categoria.getCategoriaNombre() : "";
    }
}

