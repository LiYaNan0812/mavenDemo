package com.lyn;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class HttpDemo {
    static HttpClient httpClient = HttpClient.newBuilder().build();
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
//        before8Method();
//        after11Method();
        String url = "http://www.example.com/login";
        String body="username=bob&password=1234";
        HttpRequest request = HttpRequest.newBuilder(new URI(url))
                .header("Accept", "*/*")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        System.out.println(new String(response.body(),StandardCharsets.UTF_8));

    }

    private static void after11Method() throws URISyntaxException, IOException, InterruptedException {
        String url = "https://www.sina.com.cn";
        HttpRequest request = HttpRequest.newBuilder(new URI(url))
                .header("User-Agent", "Java HttpClient")
                .header("Accept", "*/*")
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, List<String>> headers = response.headers().map();
        for(String header : headers.keySet()){
            System.out.println(header + ": " + headers.get(header));
        }
        System.out.println(response.body());
    }

    private static void before8Method() throws IOException {
        URL url = new URL("http://www.baidu.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        conn.connect();
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("bad response");
        }
        Map<String, List<String>> headerFields = conn.getHeaderFields();
        for (String key : headerFields.keySet()) {
            System.out.println(key + ": " + headerFields.get(key));
        }
        InputStream inputStream = conn.getInputStream();
        StringBuffer sb = new StringBuffer();
        byte[] b = new byte[1024];
        int len = 0;
        while((len = inputStream.read(b) )!=-1){
            sb.append(new String(b,0,len,"UTF-8"));
        }
        System.out.println(sb.toString());
    }
}
