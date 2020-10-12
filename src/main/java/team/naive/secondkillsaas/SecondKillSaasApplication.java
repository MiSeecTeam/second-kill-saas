package team.naive.secondkillsaas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@MapperScan("team.naive.secondkillsaas.Mapper")
public class SecondKillSaasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondKillSaasApplication.class, args);
	}

}
