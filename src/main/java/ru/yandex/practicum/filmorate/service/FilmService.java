package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

    Film createFilm(Film film);

    Film editFilm(Film film);

    List<Film> getAllFilms();

    Film getFilmById(Long id);

    List<Film> getPopularFilms(int count, Integer genre, Integer year);

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    List<Film> getCommonFilms(long userId, long friendId);
}
