package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.Language;

public class DatabaseAccessorObjectRF implements DatabaseAccessorRF {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String password = "student";
	private static final String username = "student";

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Connection conn = DriverManager.getConnection(URL, username, password);
		String sqltxt = "SELECT id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features FROM film WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sqltxt);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		Film output = new Film();

		while (rs.next()) {
			output.setId(rs.getInt("id"));
			output.setTitle(rs.getString("title"));
			output.setDescription(rs.getString("description"));
			output.setReleaseYear(rs.getShort("release_year"));
			output.setLanguageId(rs.getInt("language_id"));
			output.setRentalDuration(rs.getInt("rental_duration"));
			output.setRentalRate(rs.getDouble("rental_rate"));
			output.setLength(rs.getInt("length"));
			output.setReplacementCost(rs.getDouble("replacement_cost"));
			output.setRating(rs.getString("rating"));
			output.setSpecialFeatures(rs.getString("special_features"));
			output.setActors(findActorsByFilmId(rs.getInt("id")));

		}
		if (output.getTitle() == null) {
			return null;
		}

		rs.close();
		stmt.close();
		conn.close();
		return output;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Connection conn = DriverManager.getConnection(URL, username, password);
		String sqltxt = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sqltxt);
		stmt.setInt(1, actorId);
		ResultSet rs = stmt.executeQuery();
		Actor output = new Actor();

		while (rs.next()) {
			output.setId(rs.getInt("id"));
			output.setFirstName(rs.getString("first_name"));
			output.setLastName(rs.getString("last_name"));

		}

		rs.close();
		stmt.close();
		conn.close();
		return output;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		Connection conn = DriverManager.getConnection(URL, username, password);
		String sqltxt = "SELECT * FROM actor JOIN film_actor ON actor.id = film_actor.actor_id WHERE film_id = ?";

		PreparedStatement stmt = conn.prepareStatement(sqltxt);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		List<Actor> outputList = new ArrayList<>();

		while (rs.next()) {
			int id = rs.getInt("id");
			String fn = rs.getString("first_name");
			String ln = rs.getString("last_name");
			Actor actor = new Actor(id, fn, ln);

			outputList.add(actor);

		}

		rs.close();
		stmt.close();
		conn.close();
		return outputList;
	}

	@Override
	public Language findLangByFilm(Film film) throws SQLException {
		Connection conn = DriverManager.getConnection(URL, username, password);
		String sqltxt = "SELECT * FROM language WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sqltxt);
		stmt.setInt(1, film.getLanguageId());
		ResultSet rs = stmt.executeQuery();
		Language output = new Language();

		while (rs.next()) {
			output.setId(rs.getInt("id"));
			output.setName(rs.getString("name"));

		}

		rs.close();
		stmt.close();
		conn.close();
		return output;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) throws SQLException {
		Connection conn = DriverManager.getConnection(URL, username, password);
		String sqltxt = "SELECT * FROM film WHERE film.title LIKE ? OR film.description LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(sqltxt);
		stmt.setString(1, "%" + keyword + "%");
		stmt.setString(2, "%" + keyword + "%");
		ResultSet rs = stmt.executeQuery();
		List<Film> outputList = new ArrayList<>();

		while (rs.next()) {
			Film output = new Film();
			output.setId(rs.getInt("id"));
			output.setTitle(rs.getString("title"));
			output.setDescription(rs.getString("description"));
			output.setReleaseYear(rs.getShort("release_year"));
			output.setLanguageId(rs.getInt("language_id"));
			output.setRentalDuration(rs.getInt("rental_duration"));
			output.setRentalRate(rs.getDouble("rental_rate"));
			output.setLength(rs.getInt("length"));
			output.setReplacementCost(rs.getDouble("replacement_cost"));
			output.setRating(rs.getString("rating"));
			output.setSpecialFeatures(rs.getString("special_features"));
			output.setActors(findActorsByFilmId(rs.getInt("id")));

			outputList.add(output);

		}

		rs.close();
		stmt.close();
		conn.close();
		return outputList;
	}
}
