package pl.kulbat.monitorowaniejednostekmorskich;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MonitorowanieJednostekMorskichApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorowanieJednostekMorskichApplication.class, args);
    }

}
