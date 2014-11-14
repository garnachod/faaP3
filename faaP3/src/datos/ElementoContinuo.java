/**
 *
 * @author Daniel Garnacho y Diego Castaño
 */

package datos;

public class ElementoContinuo extends Elemento {
    private double valor;
    public ElementoContinuo (double valor) {
       this.valor = valor; 
    }
    @Override
    public String getValorNominal() {
        return "";
    }
    @Override
    public double getValorContinuo() {
        return this.valor;
    }

    @Override
    public int hashCode() {
        return (new Double(valor)).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.getValorContinuo() == ((ElementoContinuo)obj).getValorContinuo();
    }

    @Override
    public TiposDeAtributos getTipo() {
        return TiposDeAtributos.Continuo;
    }
    
    @Override
    public double diferencia(Elemento e) {
        return Math.pow(e.getValorContinuo() - this.getValorContinuo(), 2);
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public void setValorContinuo(double valor) {
        this.valor = valor;
    }
} 
