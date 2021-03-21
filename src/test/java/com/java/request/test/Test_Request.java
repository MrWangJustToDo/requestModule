package com.java.request.test;

import com.java.request.http.HttpClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean*.xml")
public class Test_Request {
    @Resource(name = "clientService")
    private HttpClientService service;

    @Test
    public void testGet() {
        if (service != null) {
            try {
                for (int i = 2000; i < 2012; i++) {
                    String res = service.doGet("https://api-test.castlery.com.au/cities?q=" + i + "&size=100");
                    System.out.println(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
