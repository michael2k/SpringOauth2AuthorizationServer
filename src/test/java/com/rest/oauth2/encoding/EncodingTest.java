package com.rest.oauth2.encoding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncodingTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void encodeTest() {
    	/* {bcrypt}$2a$10$Kh.nPv5Zhzanl6dQF4N.le6ibWkSzk/IYyho53LPuESKpkdQ7D9qq */
        System.out.printf("testSecret : %s", passwordEncoder.encode("testSecret"));
        System.out.println();
    }
}
