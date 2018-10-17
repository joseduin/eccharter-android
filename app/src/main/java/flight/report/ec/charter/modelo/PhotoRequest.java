package flight.report.ec.charter.modelo;

public class PhotoRequest {

    private String nameid;
    private String tipo;
    private String path;

    public PhotoRequest(String nameid, String tipo, String path) {
        this.nameid = nameid;
        this.tipo = tipo;
        this.path = path;
    }

    public PhotoRequest() {}

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
