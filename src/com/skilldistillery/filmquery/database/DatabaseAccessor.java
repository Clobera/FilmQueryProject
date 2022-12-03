package com.skilldistillery.filmquery.database;

import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.Language;

public interface DatabaseAccessor {
	public Film findFilmById(int filmId) throws SQLException;

	public Actor findActorById(int actorId) throws SQLException;

	public List<Actor> findActorsByFilmId(int filmId);

	public List<Film> findFilmsBySearchWord(String keyWord);

	public Language findLangById(int languageId) throws SQLException;

	public List<Film> findFilmsByActorId(int actorId); // this one not req for hw

}
