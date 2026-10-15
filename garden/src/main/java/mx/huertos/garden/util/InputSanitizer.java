package mx.huertos.garden.util;

import java.text.Normalizer;

public final class InputSanitizer {

    private InputSanitizer() {}

    public static String clean(String value) {
        if (value == null) return null;
        String s = Normalizer.normalize(value, Normalizer.Form.NFKC);
        s = s.replaceAll("<[^>]*>", "");
        s = s.replaceAll("[\\p{Cntrl}]", " ");
        s = s.replaceAll("\\s+", " ").trim();
        return s;
    }
}