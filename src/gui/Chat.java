package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Chat extends Application
{

    // Wieloliniowe pole do wyświetlania historii czatu
    private final TextArea textArea = new TextArea();

    // Jednoliniowe pole do wpisywania wiadomości
    private final TextField textField = new TextField();

    // Lista aktywnych użytkowników (po prawej stronie)
    private final ListView<String> users = new ListView<>();

    // Przycisk do wysyłania wiadomości
    private final Button button = new Button("Wyślij");

    // Login użytkownika
    private final String username;

    // Konstruktor przyjmujący login użytkownika
    public Chat(String username)
    {
        this.username = username;
    }

    @Override
    public void start(Stage stage)
    {
        // Zablokowanie edytowania historii czatu
        textArea.setEditable(false);

        // Panel na dole: pole + przycisk
        HBox bottomBox = new HBox(10, textField, button); // odstęp 10px
        bottomBox.setPadding(new Insets(10)); // margines wewnętrzny
        bottomBox.setSpacing(10); // odstęp między elementami

        // Główny układ aplikacji
        BorderPane pane = new BorderPane();
        pane.setCenter(textArea); // środek — czat
        pane.setRight(users);     // prawa strona — lista użytkowników
        pane.setBottom(bottomBox); // dół — pole i przycisk
        pane.setPadding(new Insets(10)); // marginesy zewnętrzne

        // Tworzenie sceny i przypisanie do okna
        Scene scene = new Scene(pane);

        // Obsługa przycisku „Wyślij”
        button.setOnAction(e -> sendMessage());

        // Obsługa wciśnięcia Enter w polu tekstowym
        textField.setOnAction(e -> sendMessage());

        // Konfiguracja i uruchomienie okna
        stage.setScene(scene);
        stage.setTitle("Prosty chat - " + username); // Nazwa okna z loginem
        stage.show();
    }

    // Wysyłanie wiadomości (z pola tekstowego do historii czatu)
    private void sendMessage()
    {
        String message = textField.getText().trim(); // Pobranie tekstu

        if (!message.isEmpty())
        {
            textArea.appendText(username + ": " + message + "\n"); // Dodanie do czatu
            textField.clear(); // Wyczyszczenie pola
        }
    }

    // Główna metoda JavaFX (uruchamiana tylko jeśli bez LoginWindow)
    public static void main(String[] args)
    {
        launch(); // Uruchamia aplikację
    }
}