import com.google.gson.Gson;
//import dao.Sql2oFoodtypeDao;
//import dao.Sql2oRestaurantDao;
import exceptions.ApiException;
import models.Restaurant;
import models.Review;
import models.dao.Sql2oFoodtypeDao;
import models.dao.Sql2oRestaurantDao;
import models.dao.Sql2oReviewDao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        Sql2oFoodtypeDao foodtypeDao;
        Sql2oRestaurantDao restaurantDao;
        Sql2oReviewDao reviewDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/jadle";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "1234");

        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();

        post("/restaurants/new", "application/json", (req, res) -> { //accept a request in format JSON
            Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);//make with GSON
            restaurantDao.add(restaurant);//Do our thing with our DAO
            res.status(201);//everything went well - update the response status code
            res.type("application/json");
            return gson.toJson(restaurant);//send it back to be displayed
        });

        get("/restaurants", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            return gson.toJson(restaurantDao.getAll());//send it back to be displayed
        });

        get("/restaurants/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
//            res.type("application/json");
            int restaurantId = Integer.parseInt(req.params("id"));
            Restaurant restaurantToFind = restaurantDao.findById(restaurantId);
            if(restaurantToFind == null){
                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
            }
//            res.type("application/json");
            return gson.toJson(restaurantToFind);
        });
        post("/restaurants/:restaurantId/reviews/new", "application/json", (req, res) -> {
            int restaurantId = Integer.parseInt(req.params("restaurantId"));
            Review review = gson.fromJson(req.body(), Review.class);
            review.setCreatedat(); //I am new!
            review.setFormattedCreatedAt();
            review.setRestaurantId(restaurantId);
            reviewDao.add(review);
            res.status(201);
            return gson.toJson(review);
        });
        get("/restaurants/:id/sortedReviews", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted reviews or unsorted ones.
            int restaurantId = Integer.parseInt(req.params("id"));
            Restaurant restaurantToFind = restaurantDao.findById(restaurantId);
            List<Review> allReviews;
            if (restaurantToFind == null){
                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
            }
            allReviews = reviewDao.getAllReviewsByRestaurantSortedNewestToOldest(restaurantId);
            return gson.toJson(allReviews);
        });
        exception(ApiException.class, (exc, req,res)->{
            ApiException err = (ApiException) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("erroMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });
    }
}

//public class App {
//
//    public static void main(String[] args) {
//        Sql2oFoodtypeDao foodtypeDao;
//        Sql2oRestaurantDao restaurantDao;
//        Sql2oReviewDao reviewDao;
//        Connection conn;
//        Gson gson = new Gson();
//
//        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
//
//        restaurantDao = new Sql2oRestaurantDao(sql2o);
//        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
//        reviewDao = new Sql2oReviewDao(sql2o);
//        conn = sql2o.open();
//
//
//        post("/restaurants/new", "application/json", (req, res) -> {
//            Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);
//            restaurantDao.add(restaurant);
//            res.status(201);
//            res.type("application/json");
//            return gson.toJson(restaurant);
//        });
//    }
//}