package camila.davi.isabelly.yasmin.domos.bd;

import java.util.List;

public class NumDivCondominio {
    List<String> numeros;
    List<String> divisoes;

    public NumDivCondominio(List<String> numeros, List<String> divisoes) {
        this.numeros = numeros;
        this.divisoes = divisoes;
    }

    public List<String> getNumeros() {
        return numeros;
    }

    public List<String> getDivisoes() {
        return divisoes;
    }
}
