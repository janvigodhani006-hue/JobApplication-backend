package com.example.jobApplication.service;

import com.example.jobApplication.Entity.User;
import com.example.jobApplication.dto.UserDto;
import com.example.jobApplication.exception.DuplicateResourceException;
import com.example.jobApplication.exception.ResourceNotFoundException;
import com.example.jobApplication.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userDto){
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new DuplicateResourceException(
                    "Product with id " + userDto.getEmail() + "already exits"
            );
        }
        else {
            User user= mapToEntity(userDto);
            User saveProduct = userRepository.save(user);
            return mapToDTO(saveProduct);
        }
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToDTO(user);
    }

    private UserDto mapToDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    private User mapToEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .passwordHash(userDto.getPasswordHash())
                .createdAt(userDto.getCreatedAt())
                .updatedAt(userDto.getUpdatedAt())
                .build();
    }

}