package gui;

import java.util.*;

public class ClientReceiver
{

    // Lista zalogowanych użytkowników
    private Set<String> onlineUsers = new HashSet<>();

    // Analiza i obsługa typu wiadomości z serwera
    public void processMessage(String line)
    {
        if (line.startsWith("MSG:"))
        {
            handleBroadcast(line.substring(4).trim()); // wiadomość ogólna
        }
        else if (line.startsWith("LOGIN:"))
        {
            handleLoginBroadcast(line.substring(6).trim()); // dołączenie
        }
        else if (line.startsWith("LOGOUT:"))
        {
            handleLogoutBroadcast(line.substring(7).trim()); // wyjście
        }
        else if (line.startsWith("ONLINE:"))
        {
            handleOnline(line.substring(7).trim()); // lista online
        }
        else
        {
            System.out.println("[?] " + line); // wiadomość nieznanego typu
        }
    }

    // Obsługa ogólnej wiadomości (broadcast)
    public void handleBroadcast(String message)
    {
        System.out.println("[Czat] " + message);
    }

    // Obsługa logowania nowego użytkownika
    public void handleLoginBroadcast(String message)
    {
        System.out.println("[Dołączył] " + message);
        onlineUsers.add(message);
        printOnline();
    }

    // Obsługa wyjścia użytkownika
    public void handleLogoutBroadcast(String message)
    {
        System.out.println("[Opuścił czat] " + message);
        onlineUsers.remove(message);
        printOnline();
    }

    // Odbiór i ustawienie listy online od serwera
    public void handleOnline(String message)
    {
        onlineUsers.clear(); // czyszczenie starej listy
        String[] users = message.split(","); // rozdzielanie loginów
        Collections.addAll(onlineUsers, users); // dodanie do zbioru
        printOnline();
    }

    // Wyświetlanie aktualnej listy użytkowników
    private void printOnline()
    {
        System.out.println("👥 Online: " + String.join(", ", onlineUsers));
    }
}