package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessorObjectRF;
import com.skilldistillery.filmquery.database.DatabaseAccessorRF;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryAppRF {
	DatabaseAccessorRF db = new DatabaseAccessorObjectRF();

	public static void main(String[] args) throws SQLException {
		FilmQueryAppRF app = new FilmQueryAppRF();
//	    app.test();
		app.launch();
	}

//	  private void test() {
//	    Film film;
//	    Actor actor;
//		try {
//			film = db.findFilmById(1);
//			actor = db.findActorById(1);
//			
//		} catch (SQLException e) {
//			System.out.println("THERE WAS AN ERROR RETIREVING THE FILM");
//			e.printStackTrace();
//			film = null;
//			actor = null;
//		}
//	    System.out.println(film);
//	    System.out.println(actor);
//	  }

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) throws SQLException {
		boolean keepGoing = true;
		int userInput = 0;

		while (keepGoing) {
			System.out.println("********** MENU **********");
			System.out.println("*     LOOKUP FILM BY:    *");
			System.out.println("*  1.) ID                *");
			System.out.println("*  2.) KEYWORD           *");
			System.out.println("*  3.) EXIT              *");
			System.out.println("**************************");
			System.out.println("");
			System.out.println("");

			try {
				userInput = input.nextInt();
				input.nextLine();
			} catch (Exception e) {
				System.out.println("PLEASE ENTER A VALID NUMBER");
				input.nextLine();
			}

			if (userInput == 1) {
				int filmId = 0;
				System.out.println("Please enter Film ID: ");
				System.out.println();
				filmId = input.nextInt();
				input.nextLine();

				Film output = db.findFilmById(filmId);
				String language = db.findLangByFilm(output).getName();

				if (output == null) {
					System.out.println("THERE WAS NO FILM FOUND BY THE ID: " + filmId);
					System.out.println();
					System.out.println();

				} else {
					
					System.out.println("------- " + output.getTitle() + " (" + output.getReleaseYear() + ") " + "Rated " + output.getRating() + " -------");
					System.out.println();
					System.out.println(output.getDescription());
					System.out.println("* " + language);
					System.out.println("* CAST");
					for (Actor actor : output.getActors()) {
						System.out.println("    - " + actor.getFirstName() + " " + actor.getLastName());
					}
					System.out.println();
					System.out.println();
					submenu(input, output);
					

				}

			} else if (userInput == 2) {
				System.out.println("Please enter a keyword: ");
				System.out.println();
				String keyword = input.nextLine();

				List<Film> inputList = db.findFilmByKeyword(keyword);

				if (inputList.size() <= 0) {
					System.out.println("THERE WAS NO FILMS FOUND BY THE KEYWORD: " + keyword);
					System.out.println();
					System.out.println();

				} else {
					
					for (Film film : inputList) {
						String language = db.findLangByFilm(film).getName();
						System.out.println("------- " + film.getTitle() + " (" + film.getReleaseYear() + ") " + "Rated " + film.getRating() + " -------");
						System.out.println();
						System.out.println(film.getDescription());
						System.out.println("* " + language);
						System.out.println("* CAST");
						for (Actor actor : film.getActors()) {
							System.out.println("    - " + actor.getFirstName() + " " + actor.getLastName());
						}
						System.out.println();
						System.out.println();
						
					}

				}
				

			} else if (userInput == 3) {
				keepGoing = false;

			} else {
				System.out.println("PLEASE ENTER A NUMBER BETWEEN 1 AND 3");
			}
		}

	}
	
	public void submenu(Scanner sc, Film film) throws SQLException {
		int userInput = 0;
		boolean keepGoing = true;
		String language = db.findLangByFilm(film).getName();

		while(keepGoing) {
			System.out.println("------ WOULD YOU LIKE TO: ------");
			System.out.println("1.) RETURN TO MAIN MENU");
			System.out.println("2.) VIEW ALL FILM DETAILS");
			System.out.println();
			System.out.println();
			userInput = sc.nextInt();
			sc.nextLine();
			
			if(userInput == 1) {
				keepGoing = false;
			}else if(userInput == 2) {
				System.out.println("------- " + film.getTitle() + " (" + film.getReleaseYear() + ") " + "Rated " + film.getRating() + " -------");
				System.out.println();
				System.out.println(film.getDescription());
				System.out.println("* " + language);
				System.out.println("* CAST");
				for (Actor actor : film.getActors()) {
					System.out.println("   - " + actor.getFirstName() + " " + actor.getLastName());
				}
				System.out.println("* SPECS");
				System.out.println("   - " + film.getRentalDuration() + " days");
				System.out.println("   - $" + film.getRentalRate() + " to rent");
				System.out.println("   - $" + film.getReplacementCost() + " to replace");
				System.out.println("   - Special Features: " + film.getSpecialFeatures() );
				System.out.println();
				System.out.println();
				
			}
			
		}
		
		
		
		
	}
	
	
}
