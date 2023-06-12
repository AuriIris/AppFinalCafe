package com.example.appfinalcafe.Model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nombre;
    private int rol;
    private int permiso;
    private String mail;
    private String clave;

    public Usuario(int id, String nombre, int rol, int permiso, String mail, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.permiso = permiso;
        this.mail = mail;
        this.clave = clave;
    }

    @Override
    public String toString() {
        return  ", Nombre: '" + nombre + '\'' +
                ", Rol: '" + getRolNombre() + '\'';
    }

    public Usuario(String usuario, String clave) {
        this.mail = usuario;
        this.clave = clave;
    }
    public Usuario(String usuario) {
        this.mail = usuario;
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

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getPermiso() {
        return permiso;
    }

    public void setPermiso(int permiso) {
        this.permiso = permiso;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    public enum EnRol {
        Cocina(0),
        Mozo(1),
        Barra(2);

        private final int id;

        EnRol(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getRolNombre() {
            return this.toString();
        }

        public static Usuario.EnRol obtenerRolPorId(int id) {
            for (Usuario.EnRol rol : values()) {
                if (rol.getId() == id) {
                    return rol;
                }
            }
            return null; // Manejar el caso en que el ID no coincida con ningún estado
        }
    }
    public String getPermisoNombre() {
        Usuario.EnPermiso rol = Usuario.EnPermiso.obtenerPermisoPorId(this.rol);
        return (rol != null) ? rol.getPermisoNombre() : "";
    }
    public enum EnPermiso {
        Admin(0),
        Empleado(1);

        private final int id;

        EnPermiso(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getPermisoNombre() {
            return this.toString();
        }

        public static Usuario.EnPermiso obtenerPermisoPorId(int id) {
            for (Usuario.EnPermiso permiso : values()) {
                if (permiso.getId() == id) {
                    return permiso;
                }
            }
            return null; // Manejar el caso en que el ID no coincida con ningún estado
        }
    }
    public String getRolNombre() {
        Usuario.EnRol rol = Usuario.EnRol.obtenerRolPorId(this.rol);
        return (rol != null) ? rol.getRolNombre() : "";
    }
}
