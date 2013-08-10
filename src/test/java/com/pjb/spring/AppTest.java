package com.pjb.spring;

import com.pjb.spring.config.AsyncTask;
import com.pjb.spring.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class AppTest {

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        ctx.register(SpringConfig.class);
        ctx.refresh();

        AsyncTask task= ctx.getBean(AsyncTask.class);
        task.doAsyncTask();
    }
}
