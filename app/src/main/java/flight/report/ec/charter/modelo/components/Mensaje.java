package flight.report.ec.charter.modelo.components;

public class Mensaje {
    private String mesaje;
    private String urlFoto;
    private String nombre;
    private String fotoperfil;
    private String type_mensaje;
    private Long hora;

    public Mensaje() {
    }

    public Mensaje(String mesaje, String nombre, String fotoperfil, String type_mensaje, Long hora) {
        this.mesaje = mesaje;
        this.nombre = nombre;
        this.fotoperfil = fotoperfil;
        this.type_mensaje = type_mensaje;
        this.hora = hora;
    }

    public Mensaje(String mesaje, String urlFoto, String nombre, String fotoperfil, String type_mensaje, Long hora) {
        this.mesaje = mesaje;
        this.urlFoto = urlFoto;
        this.nombre = nombre;
        this.fotoperfil = fotoperfil;
        this.type_mensaje = type_mensaje;
        this.hora = hora;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getMesaje() {
        return mesaje;
    }

    public void setMesaje(String mesaje) {
        this.mesaje = mesaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}
