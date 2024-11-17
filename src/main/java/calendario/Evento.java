package calendario;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Evento {
    private String id;
    private String ubicacion;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private boolean esAudioVisual;
    private boolean tieneCatering;
    private Set<Persona> personasInvitadas;
    private Set<Salon> salones;

    public Evento(String ubicacion, String nombre, String descripcion, Date fecha, boolean esAudioVisual, boolean tieneCatering, Set<Persona> personasInvitadas, Set<Salon> salones) {
        this.id = UUID.randomUUID().toString();
        this.ubicacion = ubicacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.esAudioVisual = esAudioVisual;
        this.tieneCatering = tieneCatering;
        this.personasInvitadas = personasInvitadas != null ? personasInvitadas : new HashSet<>();
        this.salones = salones != null ? salones : new HashSet<>();
    }

    public Evento(Date fecha, String ubicacion, String descripcion, Set<Persona> personasInvitadas, Set<Salon> salones) {
        this.id = UUID.randomUUID().toString();
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.personasInvitadas = personasInvitadas != null ? personasInvitadas : new HashSet<>();
        this.salones = salones != null ? salones : new HashSet<>();
    }

    public Evento(String ubicacion, String nombre, String descripcion, Date fecha, boolean esAudioVisual, boolean tieneCatering) {
        this.id = UUID.randomUUID().toString();
        this.ubicacion = ubicacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.esAudioVisual = esAudioVisual;
        this.tieneCatering = tieneCatering;
        this.personasInvitadas = new HashSet<>();
        this.salones = new HashSet<>();
    }
    public String getId() { return id; }

    public void agregarInvitado(Persona persona) {
        if (persona != null) {
            personasInvitadas.add(persona);
        } else {
            throw new IllegalArgumentException("La persona no puede ser null.");
        }
    }

    public void eliminarInvitado(Persona persona) {
        personasInvitadas.remove(persona);
    }

    public Set<Persona> getPersonasInvitadas() {
        return personasInvitadas;
    }

    public void agregarSalon(Salon salon) {
        salones.add(salon);
    }

    public void editarSalon(Salon salon, String nombre, int capacidad, boolean esAudiovisual, boolean tieneCatering) {
        salon.editarSalon(nombre, capacidad, esAudiovisual, tieneCatering);
    }

    public String mostrarSalon(Salon salon) {
        return salon.mostrarSalon();
    }

    public Set<Salon> getSalones() {
        return salones;
    }

    // Getters y Setters
    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isEsAudioVisual() {
        return esAudioVisual;
    }

    public void setEsAudioVisual(boolean esAudioVisual) {
        this.esAudioVisual = esAudioVisual;
    }

    public boolean isTieneCatering() {
        return tieneCatering;
    }

    public void setTieneCatering(boolean tieneCatering) {
        this.tieneCatering = tieneCatering;
    }

    public void setPersonasInvitadas(Set<Persona> personasInvitadas) {
        this.personasInvitadas = personasInvitadas;
    }


    public void asignarPersonasInvitadas(Persona personaInvitada) {
        this.personasInvitadas.add(personaInvitada);
    }

    public void eliminarPersonas(Persona personaInvitada) {
        this.personasInvitadas.remove(personaInvitada);
    }

    public int cantidadDeInvitados() {
        return this.personasInvitadas.size();
    }

    public boolean esProximo() {
        LocalDate fechaActual= LocalDate.now();
        return this.fecha.toInstant().
                atZone(ZoneId.systemDefault()).
                toLocalDate().
                isAfter(fechaActual);
    }

    void setId(String id) {
        this.id=UUID.randomUUID().toString();
    }
}
