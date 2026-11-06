package prototype;

import java.util.HashMap;
import java.util.Map;

public class RegistroPrototipos {
    private Map<String, ProductoBase> registro = new HashMap<>();

    public void registrar(String clave, ProductoBase prototipo) {
        registro.put(clave, prototipo);
    }

    public ProductoBase clonar(String clave, String nuevoId) {
        ProductoBase prototipo = registro.get(clave);
        if (prototipo != null) {
            return prototipo.copiar(nuevoId);
        }
        throw new IllegalArgumentException("Prototipo no encontrado: " + clave);
    }
}