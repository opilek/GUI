package gui;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer
{

    // Numer portu, na którym działa serwer
    private static final int PORT = 12345;

    // Mapa trzymająca login -> output stream klienta
    private static Map<String, PrintWriter> clientWriters = new ConcurrentHashMap<>();

    public static void main(String[] args)
    {
        System.out.println("Chat Server is running on port " + PORT);

        // Tworzymy pulę wątków (np. do 50 klientów jednocześnie)
        ExecutorService pool = Executors.newFixedThreadPool(50);

        try (ServerSocket listener = new ServerSocket(PORT))
        {
            // Serwer działa w pętli — czeka na połączenia
            while (true)
            {
                // Dla każdego nowego klienta uruchamiamy nowy wątek
                pool.execute(new ClientHandler(listener.accept()));
            }
        }
        catch (IOException e)
        {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    // Obsługa pojedynczego klienta
    private static class ClientHandler implements Runnable
    {
        private Socket socket;
        private BufferedReader in;   // Odczyt z klienta
        private PrintWriter out;     // Wysyłanie do klienta
        private String userName;     // Nazwa klienta

        public ClientHandler(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            try
            {
                // Inicjalizacja strumieni
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Odczyt nazwy użytkownika
                userName = in.readLine();

                // Walidacja loginu
                if (userName == null || userName.trim().isEmpty())
                {
                    out.println("NAME_INVALID");
                    return;
                }

                // Sprawdzenie unikalności loginu
                synchronized (clientWriters)
                {
                    if (clientWriters.containsKey(userName))
                    {
                        out.println("NAME_TAKEN");
                        return;
                    }
                    // Dodanie klienta do listy aktywnych
                    clientWriters.put(userName, out);
                }

                // Informacja o nowym użytkowniku
                System.out.println(userName + " joined.");
                broadcast("LOGIN:" + userName);

                // Wysłanie listy online użytkowników do nowego klienta
                sendOnlineList();

                // Główna pętla — odbieranie wiadomości od klienta
                String message;

                while ((message = in.readLine()) != null)
                {
                    // Rozsyłanie wiadomości do innych
                    broadcast("MSG:" + userName + ": " + message);
                }

            }
            catch (IOException e)
            {
                System.err.println("Error for " + userName + ": " + e.getMessage());
            }
            finally
            {
                // Usuwanie klienta z listy przy rozłączeniu
                if (userName != null)
                {
                    synchronized (clientWriters)
                    {
                        clientWriters.remove(userName);
                    }
                    broadcast("LOGOUT:" + userName);
                    System.out.println(userName + " left.");
                }

                // Zamknięcie połączenia
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    System.err.println("Closing socket error: " + e.getMessage());
                }
            }
        }

        // Wysyłanie listy zalogowanych użytkowników nowemu klientowi
        private void sendOnlineList()
        {
            StringBuilder usersList = new StringBuilder();

            for (String user : clientWriters.keySet())
            {
                usersList.append(user).append(",");
            }

            // Usunięcie ostatniego przecinka
            if (usersList.length() > 0)
                usersList.setLength(usersList.length() - 1);

            // Wysłanie listy do klienta
            out.println("ONLINE:" + usersList.toString());
        }

        // Wysłanie wiadomości do wszystkich klientów
        private void broadcast(String message)
        {
            synchronized (clientWriters)
            {
                for (PrintWriter writer : clientWriters.values())
                {
                    writer.println(message);
                }
            }
        }
    }
}