
package clases;

/**
 *
 * @author Rael
 */
public class CategoriaItem {
    private final int id;
    private final String nombre;

    public CategoriaItem(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
        public String getNombre() {
        return nombre;
    }

    public int getId() { return id; }
    @Override public String toString() { return nombre; }   // Lo que verá el usuario
    
}
