import java.util.Arrays;
import java.util.Scanner;

class Flight {
    private String flightNumber;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private double fare;
    private boolean[] seats;

    public Flight(String flightNumber, String source, String destination, String departureTime,
                  String arrivalTime, double fare, int numSeats) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fare = fare;

        seats = new boolean[numSeats];
        Arrays.fill(seats, true);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public double getFare() {
        return fare;
    }

    public boolean[] getSeats() {
        return seats;
    }

    public boolean isSeatAvailable(int seatNumber) {
        return seats[seatNumber];
    }

    public void bookSeat(int seatNumber) {
        seats[seatNumber] = false;
    }
}

class Booking {
    private Flight flight;
    private int numOfPassengers;
    private double totalPrice;
    private int seatNumber;

    public Booking(Flight flight, int numOfPassengers, int seatNumber) {
        this.flight = flight;
        this.numOfPassengers = numOfPassengers;
        this.totalPrice = flight.getFare() * numOfPassengers;
        this.seatNumber = seatNumber;
    }

    public Flight getFlight() {
        return flight;
    }

    public int getNumOfPassengers() {
        return numOfPassengers;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}

class BookingSystem {
    private Flight[] flights;

    public BookingSystem() {
        // Initialize flights
        flights = new Flight[3];
        flights[0] = new Flight("F101", "New York", "London", "09:00", "16:00", 500.00, 50);
        flights[1] = new Flight("F202", "Paris", "Tokyo", "12:00", "22:00", 800.00, 50);
        flights[2] = new Flight("F303", "Dubai", "Sydney", "14:00", "06:00", 1200.00, 50);
    }

    public void displayFlights() {
        System.out.println("Available Flights:");
        System.out.println("Index\tFlight Number\tSource\t\tDestination\tDeparture Time\tArrival Time\tFare");
        for (int i = 0; i < flights.length; i++) {
            Flight flight = flights[i];
            System.out.println(i + "\t\t" + flight.getFlightNumber() + "\t\t" + flight.getSource() + "\t\t" +
                    flight.getDestination() + "\t\t" + flight.getDepartureTime() + "\t\t" +
                    flight.getArrivalTime() + "\t\t" + flight.getFare());
        }
        System.out.println();
    }

    public Booking bookFlight(int flightIndex, int numOfPassengers) {
        if (flightIndex < 0 || flightIndex >= flights.length) {
            System.out.println("Invalid flight selection.");
            return null;
        }

        if (numOfPassengers < 1 || numOfPassengers > 5) {
            System.out.println("Invalid number of passengers. Please enter a number between 1 and 5.");
            return null;
        }

        Flight selectedFlight = flights[flightIndex];

        int seatNumber = selectSeat(selectedFlight);
        if (seatNumber == -1) {
            System.out.println("No available seats. Flight booking failed.");
            return null;
        }

        selectedFlight.bookSeat(seatNumber);

        Booking booking = new Booking(selectedFlight, numOfPassengers, seatNumber);
        System.out.println("Flight booked successfully.");
        System.out.println("Flight Details:");
        System.out.println("Flight Number: " + selectedFlight.getFlightNumber());
        System.out.println("Source: " + selectedFlight.getSource());
        System.out.println("Destination: " + selectedFlight.getDestination());
        System.out.println("Departure Time: " + selectedFlight.getDepartureTime());
        System.out.println("Arrival Time: " + selectedFlight.getArrivalTime());
        System.out.println("Number of Passengers: " + booking.getNumOfPassengers());
        System.out.println("Total Price: $" + booking.getTotalPrice());
        System.out.println("Seat Number: " + booking.getSeatNumber());
        System.out.println();

        return booking;
    }

    private int selectSeat(Flight flight) {
        boolean[] seats = flight.getSeats();
        System.out.println("Available Seats:");
        for (int i = 0; i < seats.length; i++) {
            if (seats[i]) {
                System.out.print(i + "\t");
            } else {
                System.out.print("-\t");
            }
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Select a seat (enter seat number) or enter -1 to cancel: ");
        int seatNumber = scanner.nextInt();

        if (seatNumber == -1) {
            return -1; // Cancel seat selection
        }

        if (seatNumber < 0 || seatNumber >= seats.length || !seats[seatNumber]) {
            System.out.println("Invalid seat selection. Please try again.");
            return selectSeat(flight); // Retry seat selection
        }

        return seatNumber;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingSystem bookingSystem = new BookingSystem();

        while (true) {
            System.out.println("Welcome to the Flight Booking System");
            System.out.println("1. Display available flights");
            System.out.println("2. Book a flight");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    bookingSystem.displayFlights();
                    break;
                case 2:
                    bookingSystem.displayFlights();
                    System.out.print("Select a flight (enter flight index): ");
                    int flightIndex = scanner.nextInt();
                    System.out.print("Enter the number of passengers: ");
                    int numOfPassengers = scanner.nextInt();
                    bookingSystem.bookFlight(flightIndex, numOfPassengers);
                    break;
                case 3:
                    System.out.println("Thank you for using the Flight Booking System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
