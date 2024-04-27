package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.database.repository.UserRepository;
import org.example.dto.UserCreateEditDto;
import org.example.dto.UserFilter;
import org.example.dto.UserReadDto;
import org.example.mapper.UserCreateEditMapper;
import org.example.mapper.UserReadMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)//правила хорошего тона
//Добавляет оптимизацию на уровне бд и на уровне хабирнейта
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;

    public List<UserReadDto>findAll(UserFilter filter){

        return userRepository.findAllByFilter(filter)
                .stream()
                .map(userReadMapper::map)
                .toList();

    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id).map(userReadMapper::map);
    }


    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto user) {
        return userRepository.findById(id)
                .map(entity -> userCreateEditMapper.map(user, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }


}
