package com.live;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class NewBirdApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private RedisTemplate<String,Object> template;

    @Test
    public void savereids() {
        User u=new User(2,"王伟",21);
        template.opsForValue().set(u.getId()+"",u);
        User result = (User) template.opsForValue().get(u.getId()+"");
        System.out.println(result.toString());
    }

    @Test
    public void saveHashReids() throws InterruptedException {
//        for (int i = 2; i < 5; i++) {
//            User u = new User(i, "王伟", 21);
//            template.opsForHash().put("myCache", u.getId() + "",u);
//            System.out.print("成功");
//        }
//        User u = new User(12, "王伟", 21);
//        template.opsForHash().put("myCache","201801",u);
//        template.expire("myCache",10,TimeUnit.SECONDS);
//        Thread.sleep(11);
        List<Object> myCache = template.opsForHash().values("myCache");
        System.out.println(myCache);
    }

    @Test
    public void parseDate () throws ParseException {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = "2018-12-27 16:34:37";
        Date date = formatter.parse(data);
        System.out.print(date);
    }
}
