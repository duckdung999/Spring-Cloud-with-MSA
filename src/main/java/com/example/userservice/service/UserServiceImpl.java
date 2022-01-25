package com.example.userservice.service;

import com.example.userservice.dto.ResponseOrder;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntitiy;
import com.example.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntitiy userEntitiy = mapper.map(userDto, UserEntitiy.class);
        userEntitiy.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd())); // 아직 미구현


        userRepository.save(userEntitiy);
        UserDto returnuserDto = mapper.map(userEntitiy, UserDto.class);
        // BT는 아님, return은 null or userId정도로 충분함
        return returnuserDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {  
        UserEntitiy userEntitiy = userRepository.findByUserId(userId);

        //user엔티티가 없다면
        if (userEntitiy == null)
            throw new UsernameNotFoundException("user not found");

        UserDto userDto = new ModelMapper().map(userEntitiy, UserDto.class);

        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public Iterable<UserEntitiy> getUserByAll() {
        return userRepository.findAll();
    }
}
