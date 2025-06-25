package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoginWindow extends Application
{

    @Override
    public void start(Stage stage)
    {
        // Pole do wpisania loginu
        TextField login = new TextField();
        login.setPromptText("Podaj login: "); // Podpowiedź w polu tekstowym

        // Przycisk do logowania
        Button button = new Button("Zaloguj");

        // Obsługa wciśnięcia Enter — działa tak jak kliknięcie przycisku
        login.setOnAction(e -> button.fire());

        // Obsługa kliknięcia przycisku
        button.setOnAction(e ->
        {
            String username = login.getText().trim(); // Pobranie loginu

            // Jeśli login nie jest pusty
            if (!username.isEmpty())
            {
                Chat chatApp = new Chat(username); // Tworzymy okno czatu z nazwą użytkownika
                Stage chatStage = new Stage();     // Nowa scena (okno) dla czatu

                try
                {
                    chatApp.start(chatStage); // Uruchamiamy GUI czatu
                }
                catch (Exception ex)
                {
                    ex.printStackTrace(); // Obsługa błędów
                }

                stage.close(); // Zamykamy okno logowania
            }
        });

        // Etykieta opisująca pole tekstowe
        Label label = new Label("Login: ");

        // Ułożenie w poziomie: Label, TextField, Button
        HBox hBox = new HBox(10, label, login, button); // odstęp między elementami: 10 px

        // Tworzenie sceny
        Scene scene = new Scene(hBox, 400, 100);

        // Konfiguracja i pokazanie okna
        stage.setTitle("Logowanie");
        stage.setScene(scene);
        stage.show();
    }

    // Punkt startowy aplikacji JavaFX
    public static void main(String[] args)
    {
        launch(); // Wywołuje metodę start()
    }
}