package calendario;

public class Persona {
    private String nombre;
    private boolean asistencia;



    public Persona(String nombre, boolean asistencia) {
        this.nombre = nombre;
        this.asistencia = asistencia;
    }

    public Persona(String nombreInvitado) {
        this.nombre=nombreInvitado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }
}
