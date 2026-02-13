package com.sparta.scheduler.service;

import com.sparta.scheduler.config.PasswordEncoder;
import com.sparta.scheduler.dto.Logindto.LoginRequest;
import com.sparta.scheduler.dto.Userdto.*;
import com.sparta.scheduler.entity.User;
import com.sparta.scheduler.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 회원가입
    @Transactional
    public CreateUserResponse signup(CreateUserRequest request) {
        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        User savedUser = userRepository.save(user);

        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPassword(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }
    // 단 건 조회
    @Transactional(readOnly = true)
    public GetOneUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        return new GetOneUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // 다 건 조회
    @Transactional(readOnly = true)
    public List<GetOneUserResponse> getAll() {
        List<User> users = userRepository.findAll();

        List<GetOneUserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            GetOneUserResponse dto = new GetOneUserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request, Long sessionUserId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        if (!userId.equals(sessionUserId)){
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        user.update(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long userId,Long sessionUserId) {
        boolean existence = userRepository.existsById(userId);

        if (!userId.equals(sessionUserId)){
            throw new IllegalArgumentException("권한이 없습니다.");
        }


        // 유저가 없는 경우
        if (!existence) {
            throw new IllegalStateException("없는 유저입니다.");
        }

        // 유저가 있는 경우 -> 삭제 가능!
        userRepository.deleteById(userId);
    }


    @Transactional(readOnly = true)
    public User login(LoginRequest request, HttpSession session) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        // 로그인 성공 처리
        // 세션에 사용자 등록
        session.setAttribute("loginUser", user);
        // 클라이언트한테 로그인성공 알리기
        return user;
    }

}
