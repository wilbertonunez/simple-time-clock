package com.paychex.clock.repo;
import org.springframework.data.repository.CrudRepository;

import com.paychex.clock.domain.*;

public interface UserRepository extends CrudRepository<User, Integer>{

}
