
import java.util.ArrayList;
import java.util.Scanner;

class Show {
    private String title;
    private String genre;
    private double rating;

    public Show(String title, String genre, double rating) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public double getRating() { return rating; }

    @Override
    public String toString() {
        return String.format("%s (%s) - %.1f/5.0", title, genre, rating);
    }
}

class User {
    private String username;
    private String password;
    private ArrayList<Show> watchlist;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.watchlist = new ArrayList<Show>();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public ArrayList<Show> getWatchlist() { return watchlist; }

    public void addToWatchlist(Show show) {
        watchlist.add(show);
    }

    public void removeFromWatchlist(int index) {
        if (index >= 0 && index < watchlist.size()) {
            watchlist.remove(index);
        }
    }
}

class Netflix {
    private ArrayList<Show> shows;
    private ArrayList<User> users;
    private User currentUser;
    private Scanner scanner;

    public Netflix() {
        shows = new ArrayList<Show>();
        users = new ArrayList<User>();
        scanner = new Scanner(System.in);
        initializeShows();
    }

    private void initializeShows() {
        shows.add(new Show("Stranger Things", "Sci-Fi", 4.8));
        shows.add(new Show("The Crown", "Drama", 4.5));
        shows.add(new Show("Black Mirror", "Sci-Fi", 4.7));
        shows.add(new Show("Money Heist", "Crime", 4.6));
        shows.add(new Show("Wednesday", "Fantasy", 4.4));
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n=== Netflix CLI ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid option!");
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== Welcome, " + currentUser.getUsername() + "! ===");
        System.out.println("1. Browse Shows");
        System.out.println("2. View Watchlist");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                browseShows();
                break;
            case 2:
                viewWatchlist();
                break;
            case 3:
                currentUser = null;
                System.out.println("Logged out successfully!");
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful!");
                return;
            }
        }
        System.out.println("Invalid credentials!");
    }

    private void register() {
        System.out.print("Choose username: ");
        String username = scanner.nextLine();
        System.out.print("Choose password: ");
        String password = scanner.nextLine();

        users.add(new User(username, password));
        System.out.println("Registration successful!");
    }

    private void browseShows() {
        System.out.println("\n=== Available Shows ===");
        for (int i = 0; i < shows.size(); i++) {
            System.out.println((i + 1) + ". " + shows.get(i));
        }
        System.out.println("Enter show number to add to watchlist (0 to go back): ");
        
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= shows.size()) {
            currentUser.addToWatchlist(shows.get(choice - 1));
            System.out.println("Show added to watchlist!");
        }
    }

    private void viewWatchlist() {
        ArrayList<Show> watchlist = currentUser.getWatchlist();
        if (watchlist.isEmpty()) {
            System.out.println("Your watchlist is empty!");
            return;
        }

        System.out.println("\n=== Your Watchlist ===");
        for (int i = 0; i < watchlist.size(); i++) {
            System.out.println((i + 1) + ". " + watchlist.get(i));
        }
        System.out.println("Enter show number to remove (0 to go back): ");

        int choice = scanner.nextInt();
        if (choice > 0 && choice <= watchlist.size()) {
            currentUser.removeFromWatchlist(choice - 1);
            System.out.println("Show removed from watchlist!");
        }
    }
}

public class GicaNetflixCLI {
    public static void main(String[] args) {
        Netflix netflix = new Netflix();
        netflix.start();
    }
}
