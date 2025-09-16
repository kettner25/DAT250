package com.example.demo;

import com.example.demo.Models.Poll;
import com.example.demo.Models.User;
import com.example.demo.Models.Vote;
import com.example.demo.Models.VoteOption;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.hibernate.cfg.JdbcSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);

        EntityManagerFactory emf = new PersistenceConfiguration("polls")
                .managedClass(Poll.class)
                .managedClass(User.class)
                .managedClass(Vote.class)
                .managedClass(VoteOption.class)
                .property(PersistenceConfiguration.JDBC_URL, "jdbc:h2:mem:polls")
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, "drop-and-create")
                .property(PersistenceConfiguration.JDBC_USER, "sa")
                .property(PersistenceConfiguration.JDBC_PASSWORD, "")
                .property(JdbcSettings.SHOW_SQL, true)
                .property(JdbcSettings.FORMAT_SQL, true)
                .property(JdbcSettings.HIGHLIGHT_SQL, true)
                .createEntityManagerFactory();

        emf.getSchemaManager().create(true);
    }
}

