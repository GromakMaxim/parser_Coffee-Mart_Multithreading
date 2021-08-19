package org.gromak.db;

import org.gromak.entity.Good;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryExecutor implements Runnable {
    private Good good;
    private Connection connection;

    public QueryExecutor(Good good){
        this.good = good;
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db?useSSL=false&amp&serverTimezone=UTC","bestuser","bestuser");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void run() {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into my_db.goods(code, title, price, descr, country, weight, producer, color, materialType, cardStatus, link) values " +
                    "(?,?,?,?,?,?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, good.getCode());
            preparedStatement.setString(2, good.getTitle());
            preparedStatement.setDouble(3, good.getPrice());
            preparedStatement.setString(4, good.getDescription());
            preparedStatement.setString(5, good.getCountry());
            preparedStatement.setString(6, good.getWeight());
            preparedStatement.setString(7, good.getProducer());
            preparedStatement.setString(8, good.getColor());
            preparedStatement.setString(9, good.getMaterialType());
            preparedStatement.setString(10, good.getCardStatus());
            preparedStatement.setString(11, good.getLink());

            preparedStatement.execute();
            System.out.println(Thread.currentThread().getName() + " SAVE: " + good);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
}
