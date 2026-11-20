package mx.huertos.garden.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyStore;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient parcelRestClient(@Value("${parcel-service.base-url}") String baseUrl,
                                @Value("${parcel-service.connect-timeout-ms}") int connectTimeout,
                                @Value("${parcel-service.read-timeout-ms}") int readTimeout,
                                @Value("${parcel-service.truststore-path}") Resource trustStore,
                                @Value("${parcel-service.truststore-password}") String trustStorePassword)
            throws Exception {

        // Confía SOLO en el certificado de Parcel (sin desactivar la validación en toda la JVM)
        KeyStore ks = KeyStore.getInstance("PKCS12");
        try (InputStream in = trustStore.getInputStream()) {
            ks.load(in, trustStorePassword.toCharArray());
        }
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        SSLContext ssl = SSLContext.getInstance("TLS");
        ssl.init(null, tmf.getTrustManagers(), null);

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof HttpsURLConnection https) {
                    https.setSSLSocketFactory(ssl.getSocketFactory());
                }
                super.prepareConnection(connection, httpMethod);
            }
        };
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return RestClient.builder().baseUrl(baseUrl).requestFactory(factory).build();
    }
}