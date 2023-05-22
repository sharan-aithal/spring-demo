package com.example.springdemo;

import com.example.springdemo.entity.home.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringDemoApplication.class, args);

        /*
		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.println(beanDefinitionName);
		}

		BookService service = context.getBean("bookServiceImpl", BookService.class);
		service.getBook(1);

		Book book = context.getBean("bookBean", Book.class);
		System.out.println(book);
		Book book2 = context.getBean("bookBean", Book.class);
		System.out.println(book2);

		Book loadBook = (Book) context.getBean("loadBookBean", 2, "It is Astronaut Job", "Lily Bake");
		System.out.println(loadBook);
		Book loadAnotherBook = context.getBean("loadAnotherBookBean", Book.class);
		System.out.println(loadAnotherBook);
		 */
    }

    @Bean
    public Book getBookBean() {
        System.out.println("getting bean for getBookBean");
        return new Book(2, "Overwrite Yourself", "Mack James");
    }

}
