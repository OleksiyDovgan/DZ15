import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

public class SimpleServer {

    public static void main(String[] args) {
        int port = 8081;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Hello! Please provide a greeting.");

            String clientGreeting = in.readLine();
            System.out.println("Client says: " + clientGreeting);

            if (containsRussianLetters(clientGreeting)) {
                out.println("What is паляниця?");
                String clientResponse = in.readLine();

                if (clientResponse.equalsIgnoreCase("Traditional Ukrainian dish")) {
                    out.println("Current date and time: " + LocalDate.now() + " " + LocalTime.now());
                } else {
                    out.println("Incorrect answer. Goodbye!");
                    clientSocket.close();
                }
            } else {
                out.println("This is not a Russian greeting. Goodbye!");
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean containsRussianLetters(String str) {
        for (char ch : str.toCharArray()) {
            if (ch >= 'а' && ch <= 'я' || ch >= 'А' && ch <= 'Я') {
                return true;
            }
        }
        return false;
    }
}