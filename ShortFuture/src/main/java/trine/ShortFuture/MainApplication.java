package trine.ShortFuture;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
//public class MainApplication extends SpringBootServletInitializer{//生产环境
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(MainApplication.class);
//    }
//
//
//    public static void main(String[] args) {
//        SpringApplication.run(MainApplication.class, args);
//    }
//}

@SpringBootApplication
public class MainApplication {//开发环境
	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
