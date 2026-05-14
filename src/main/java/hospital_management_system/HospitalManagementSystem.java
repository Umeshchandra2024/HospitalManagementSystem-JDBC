package hospital_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found. Build with Maven so mysql-connector-j is on the classpath.");
            e.printStackTrace();
            return;
        }

        var passwordOpt = DatabaseConfig.getPassword();
        if (passwordOpt.isEmpty()) {
            System.err.println("Missing HOSPITAL_DB_PASSWORD. Set it in your environment (see README).");
            return;
        }

        try (Scanner scanner = new Scanner(System.in);
                Connection connection = DriverManager.getConnection(
                        DatabaseConfig.getUrl(),
                        DatabaseConfig.getUsername(),
                        passwordOpt.get())) {

            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            while (true) {
                System.out.println();
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter a number.");
                    scanner.nextLine();
                    continue;
                }
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatients();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner);
                        break;
                    case 5:
                        System.out.println("Thank you!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.print("Enter Patient Id: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid patient id.");
            scanner.nextLine();
            return;
        }
        int patientId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Doctor Id: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid doctor id.");
            scanner.nextLine();
            return;
        }
        int doctorId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
            if (checkAvailability(doctorId, date, connection)) {
                String query = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, date);
                    if (preparedStatement.executeUpdate() > 0) {
                        System.out.println("Appointment Booked!");
                    } else {
                        System.out.println("Failed to book appointment!");
                    }
                } catch (SQLException e) {
                    System.err.println("Could not book: " + e.getMessage());
                }
            } else {
                System.out.println("Doctor not available on this date!");
            }
        } else {
            System.out.println("Patient or Doctor does not exist!");
        }
    }

    public static boolean checkAvailability(int doctorId, String date, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, date);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Availability check failed: " + e.getMessage());
        }
        return false;
    }
}
