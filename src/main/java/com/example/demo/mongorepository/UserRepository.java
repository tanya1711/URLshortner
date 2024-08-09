package com.example.demo.mongorepository;

import com.example.demo.entityclass.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{
    User findByEmail(String email);

}

