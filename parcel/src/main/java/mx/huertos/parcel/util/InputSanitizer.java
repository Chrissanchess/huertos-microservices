package mx.huertos.parcel.util;

import java.text.Normalizer;

public final class InputSanitizer {

    private InputSanitizer() {}

    public static String clean(String value) {
        if (value == null) return null;
        String s = Normalizer.normalize(value, Normalizer.Form.NFKC); // unifica caracteres raros
        s = s.replaceAll("<[^>]*>", "");                                // quita etiquetas HTML
        s = s.replaceAll("[\\p{Cntrl}]", " ");                          // quita caracteres de control
        s = s.replaceAll("\\s+", " ").trim();                           // colapsa espacios
        return s;
    }
}