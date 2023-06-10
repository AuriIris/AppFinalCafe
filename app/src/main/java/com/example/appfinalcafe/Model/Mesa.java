package com.example.appfinalcafe.Model;

import java.io.Serializable;
import java.util.Objects;

public class Mesa implements Serializable {
    private int id;
    private int sucursalId;

    private  Sucursal sucursal;
    private int numero;
    private int estado;

    public Mesa(int id, int sucursalId, Sucursal sucursal, int numero, int estado) {
        this.id = id;
        this.sucursalId = sucursalId;
        this.sucursal = sucursal;
        this.numero = numero;
        this.estado = estado;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Mesa(int id, int sucursalId, int numero, int estado) {
        this.id = id;
        this.sucursalId = sucursalId;
        this.numero = numero;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int mesaId) {
        this.id = mesaId;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    public enum EnEstado {
        Libre(0),
        Ocupada(1);

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
    public String getEstadoNombre() {
        EnEstado estado = EnEstado.obtenerEstadoPorId(this.estado);
        return (estado != null) ? estado.getEstadoNombre() : "";
    }
    @Override
    public String toString() {
        return  "N°: " + numero +
                ", Estado: " + getEstadoNombre() ;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Mesa other = (Mesa) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
