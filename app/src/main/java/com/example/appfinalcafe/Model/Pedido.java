package com.example.appfinalcafe.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Pedido implements Serializable {
    private int id;
    private int mesaId;
    private   Mesa mesa;
    private int usuarioId;
    private  Usuario usuario;
    private int estado;
    private double precioTotal;
    private String  fecha;

    public Pedido() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMesaId(int mesaId) {
        this.mesaId = mesaId;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void setFecha(String  fecha) {
        this.fecha = fecha;
    }

    public String getEstadoNombre() {
        EnEstado estado = EnEstado.obtenerEstadoPorId(this.estado);
        return (estado != null) ? estado.getEstadoNombre() : "";
    }
    public Pedido(int id, int mesaId, int usuarioId, int estado, double precioTotal, String  fecha) {
        this.id = id;
        this.mesaId = mesaId;
        this.usuarioId = usuarioId;
        this.estado = estado;
        this.precioTotal = precioTotal;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", mesaId=" + mesaId +
                ", mesa=" + mesa +
                ", usuarioId=" + usuarioId +
                ", usuario=" + usuario +
                ", estado=" + estado +
                ", precioTotal=" + precioTotal +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getMesaId() {
        return mesaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getEstado() {
        return estado;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public String  getFecha() {
        return fecha;
    }
    public enum EnEstado {
        Solicitado(0),
        EnCocina(1),
        Consumiendo(2),
        Pago(3);

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

        public static EnEstado obtenerEstadoPorId(int id) {
            for (EnEstado estado : values()) {
                if (estado.getId() == id) {
                    return estado;
                }
            }
            return null; // Manejar el caso en que el ID no coincida con ningún estado
        }

    }
}
