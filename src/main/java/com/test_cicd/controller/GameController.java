package com.test_cicd.controller;

import com.binance.connector.client.impl.SpotClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("api/game")
public class GameController {
    @Autowired
    private RestTemplate restTemplate;
    private static String FIREBASE_URL = "https://love-pop-834eb-default-rtdb.firebaseio.com/Users/";
    private static String BINANCE_API_KEY = "PxdHf4pwh7Fs1sCMTBKxXlUX0ereEFuDgK2I4ehqZH5y4PnVDHIkYBSGk8g3sdZN";
    private static String BINANCE_SECRET = "GGrlwDHIp0kTwM8U3o19T7Pu1AstwjvkL0O9TvN59EFTI5JjHdS4rfbes5PWuGax";
    private LinkedHashMap<String, Object> parameters;

    @GetMapping("ip")
    public String getBaseIP() throws IOException {
        String urlString = "http://totasportsapp.us-east-1.elasticbeanstalk.com/";
        String totaSportsAWS = "";
        URL url = new URL(urlString);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            totaSportsAWS =  br.readLine();
        }

        ResponseEntity<String> ipAddressIP64 = restTemplate.getForEntity("https://ipv4.icanhazip.com/", String.class);
        ResponseEntity<String> ipAddress = restTemplate.getForEntity("http://myexternalip.com/raw", String.class);
        ResponseEntity<String> ipAddress2 = restTemplate.getForEntity("https://ipecho.net/plain", String.class);

        return "IP: " + totaSportsAWS + "\n >>> IP-64: " + ipAddressIP64.getBody() +
                "\n >>> IP-86: " + ipAddress.getBody() + "\n >>>  IP-Plain: " + ipAddress2.getBody();
    }

    @GetMapping("api-permission")
    public String getApiPermission() {
        parameters = new LinkedHashMap<>();

        SpotClientImpl client = getBinanceSpotClient();
        return  client.createWallet().apiPermission(parameters);
    }

    private SpotClientImpl getBinanceSpotClient() {

        return new SpotClientImpl(BINANCE_API_KEY, BINANCE_SECRET);
    }

    @GetMapping("binance-fast-withdrawal")
    public String enableFastWithdraw() {
        parameters = new LinkedHashMap<>();
        SpotClientImpl client = getBinanceSpotClient();

        return client.createWallet().enableFastWithdraw(parameters);
    }

    @GetMapping("binance-withdraw/{address}")
    public String withdrawal(@PathVariable("address") String address) {
        parameters = new LinkedHashMap<>();
        parameters.put("coin", "ETH");
        parameters.put("address", address);
        parameters.put("amount", "0.00000004");

        SpotClientImpl client = getBinanceSpotClient();
        return client.createWallet().withdraw(parameters);
    }
}
