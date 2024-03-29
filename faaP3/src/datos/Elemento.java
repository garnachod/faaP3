/**
 *
 * @author Daniel Garnacho y Diego Castaño
 */

package datos;

public  abstract class Elemento{
    public abstract String getValorNominal();
    public abstract double getValorContinuo();
    public abstract void setValorContinuo(double valor);
    @Override
    public abstract int hashCode();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }
    
    public abstract TiposDeAtributos getTipo();
    public abstract double diferencia (Elemento e);
}
