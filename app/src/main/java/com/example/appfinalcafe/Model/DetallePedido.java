package com.example.appfinalcafe.Model;


import java.io.Serializable;

public class DetallePedido implements Serializable {
    private int id;
    private int pedidoId;
    private  Pedido pedido;
    private int productoId;
    private  Producto producto;
    private int cantidad;
    private double precio;

    public DetallePedido() {
    }

    public DetallePedido(int id, int pedidoId, Pedido pedido, int productoId, Producto producto, int cantidad, double precio) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.pedido = pedido;
        this.productoId = productoId;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public DetallePedido(int id, int pedidoId, int productoId, int cantidad, double precio) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", pedidoId=" + pedidoId +
                ", pedido=" + pedido +
                ", productoId=" + productoId +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }
}
