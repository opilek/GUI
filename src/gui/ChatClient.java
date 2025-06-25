package gui;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient
{

    // Adres serwera
    private static final String SERVER_ADDRESS = "localhost";

    // Port serwera
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args)
    {
        try (
                // Połączenie z serwerem
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

                // Strumień wejściowy
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Strumień wyjściowy (auto-flush = true)
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Czytnik od użytkownika
                Scanner scanner = new Scanner(System.in)
        )
        {
            System.out.println("Połączono z serwerem.");

            // Wprowadzenie loginu
            String userName;
            while (true)
            {
                System.out.print("Podaj login: ");
                userName = scanner.nextLine();

                if (!userName.trim().isEmpty())
                {
                    out.println(userName); // Wysłanie loginu do serwera
                    break;
                }
            }

            // Sprawdzenie odpowiedzi od serwera
            String firstResponse = in.readLine();

            if ("NAME_TAKEN".equals(firstResponse))
            {
                System.out.println("Login zajęty. Zakończono.");
                return;
            }

            // Inicjalizacja odbiornika wiadomości
            ClientReceiver receiver = new ClientReceiver();

            // Wątek do odbierania wiadomości z serwera
            new Thread(() ->
            {
                String line;
                try
                {
                    while ((line = in.readLine()) != null)
                    {
                        receiver.processMessage(line);
                    }
                }
                catch (IOException e)
                {
                    System.err.println("Rozłączono z serwerem.");
                }
            }).start();

            // Wątek główny: wysyłanie wiadomości od użytkownika
            while (scanner.hasNextLine())
            {
                String input = scanner.nextLine();
                out.println(input); // Wysyłanie wiadomości do serwera
            }

        }
        catch (IOException e)
        {
            System.err.println("Nie można połączyć się: " + e.getMessage());
        }
    }
}