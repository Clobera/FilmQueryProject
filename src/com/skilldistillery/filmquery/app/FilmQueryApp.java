package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.Language;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();

		app.launch();
//		app.test();

	}

	private void test() throws SQLException {
//		Film film = db.findFilmById(1);
//		List<Film> f = db.findFilmsByActorId(1);
//		List<Actor> a = db.findActorsByFilmId(4);
//		Actor a = db.findActorById(4);
//		Language l = db.findLangById(3);
//		List<Film> f = db.findFilmsBySearchWord(t);
//
//		System.out.println(l.getName());
	}

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);
		boolean keepGoing = true;

		do {
			try {

				showMenu();
				System.out.println("Please pick from the menu above: ");
				int menuChoice = input.nextInt();
				input.nextLine();

				if (menuChoice == 1) {

					try {
						System.out.println("Please enter a film ID you would like to search up: ");
						int filmID = input.nextInt();
						input.nextLine();
						searchFilmId(filmID);

					} catch (InputMismatchException e) {
						System.out.println("INVALID INPUT PLEASE TRY AGAIN. ENTER A NUMBER!");
						System.out.println();
						input.nextLine();
						continue;
					}

				} else if (menuChoice == 2) {
					System.out.println("Please enter a key word you would like to search a film by: ");
					String keyWord = input.nextLine();

					searchKeyWord(keyWord);

				} else if (menuChoice == 3) {
					System.out.println("goodbye!");
					keepGoing = false;
				} else {
					System.out.println("Invalid entry. Please enter a number from the menu!");
				}
			} catch (InputMismatchException e) {
				System.out.println("INVALID INPUT PLEASE TRY AGAIN. ENTER A NUMBER!");
				System.out.println();
				input.nextLine();
				continue;
			}

		} while (keepGoing);
		startUserInterface(input);

		input.close();
	}

	public void showMenu() {
		System.out.println("--------------------MENU-------------------");
		System.out.println("1.) Look up a film by its id");
		System.out.println("2.) Look up a film by a search keyword");
		System.out.println("3.) Exit the application");
		System.out.println("-------------------------------------------");
	}

	public void searchKeyWord(String word) throws SQLException {

		List<Film> films = db.findFilmsBySearchWord(word);

		if (films.isEmpty()) {
			System.out.println("There were no films with the key word \"" + word + "\" ");
			System.out.println();
		} else {
			System.out.println("--------- FILM(S) CONTAINING \"" + word.toUpperCase() + "\" --------");
			for (Film film : films) {
				Language lang = db.findLangById(film.getLanguageId());

				System.out.println(film.getTitle() + " (" + film.getReleaseYear() + ")- " + film.getDescription()
						+ ". \nRated: " + film.getRating() + " \nLanguage: " + lang.getName());
				System.out.println("Cast:");
				printActors(film.getId());
				System.out.println();
			}
		}

	}

	public void searchFilmId(int f) throws SQLException {
		Film film = db.findFilmById(f);
		Language lang = db.findLangById(film.getLanguageId());

		if (film.equals(null)) {
			System.out.println("Film not found");
			System.out.println();
		} else {
			System.out.println("---------FILM--------");
			System.out.println(film.getTitle() + " (" + film.getReleaseYear() + ")- " + film.getDescription()
					+ ". \nRated: " + film.getRating() + " \nLanguage: " + lang.getName());
			System.out.println("Cast:");
			printActors(film.getId());
			System.out.println();
		}
	}

	public void printActors(int filmId) {
		List<Actor> actors = db.findActorsByFilmId(filmId);

		for (Actor actor : actors) {
			System.out.println(actor.getFirstName() + " " + actor.getLastName());

		}

	}

	private void startUserInterface(Scanner input) {

	}

}
