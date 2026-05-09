package com.auth.authservice.repository;

import com.auth.authservice.entity.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {

    /*NOTE for Mentors -
    * using allow filtering is not a right solution as this query scans full table :
    * correct way is to create an index on email column but that will be complicated to setup for mentors
    * I'm using this method so they don't have to create index. in production setup indexing clear choice
    * */
    @Query("SELECT * FROM users WHERE email = ?0 ALLOW FILTERING")
    Optional<User> findByEmail(String email);
}