package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    @Override
    public User createUser(User user) {
        validateUser(user);
        return userStorage.createUser(user);
    }

    @Override
    public User editUser(User user) {
        validateUser(user);
        getUserById(user.getId());
        return userStorage.editUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @Override
    public User getUserById(long userId) {
        return userStorage.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователя с id %d не найдено", userId)));
    }

    @Override
    public void checkExistenceById(long userId) {
        if (!userStorage.checkExistenceById(userId)) {
            throw new FilmNotFoundException(String.format("Фильма с id %d не найдено", userId));
        }
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        checkExistenceById(userId);
        checkExistenceById(friendId);
        friendsStorage.addFriend(userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        checkExistenceById(userId);
        checkExistenceById(friendId);
        friendsStorage.deleteFriend(userId, friendId);
    }

    @Override
    public List<User> getFriends(long userId) {
        checkExistenceById(userId);
        return userStorage.getFriends(userId);
    }

    @Override
    public List<User> getCommonFriends(Long firstUserId, Long secondUserId) {
        return userStorage.getCommonFriends(firstUserId, secondUserId);
    }

    @Override
    public void deleteUser(long userId) {
        checkExistenceById(userId);
        userStorage.deleteUser(userId);
    }

    private void validateUser(User user) {
        if (user.getId() != null && user.getId() < 0) {
            throw new UserNotFoundException("Некорретный id");
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}
