package com.example.userservice.repository;

import com.example.userservice.entity.UserEntitiy;
import org.springframework.data.repository.CrudRepository;


// crud를 상속받는 인터페이스를 설정해 놓으면 JPA가 알아서 CRUD관련 기능을 구현해서 인스턴스를 만들어 빈에 등록해줌.
public interface UserRepository extends CrudRepository<UserEntitiy, Long> {
    UserEntitiy findByUserId(String userId);
}
