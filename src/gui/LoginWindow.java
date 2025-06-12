package gui;

// Importujemy klasy potrzebne do tworzenia GUI w JavaFX
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

// Klasa LoginWindow rozszerza Application - jest to główny punkt startowy aplikacji JavaFX
public class LoginWindow extends Application
{
    // Metoda start jest wywoływana automatycznie przy uruchomieniu aplikacji
    @Override
    public void start(Stage stage)
    {
        // Tworzymy pole tekstowe do wpisywania loginu użytkownika
        TextField login = new TextField();

        // Ustawiamy tekst podpowiedzi wyświetlany w pustym polu login (tzw. placeholder)
        login.setPromptText("Podaj login:");

        // Tworzymy przycisk "Zaloguj"
        Button button = new Button("Zaloguj");


        // Dodajemy obsługę zdarzenia na polu tekstowym - gdy użytkownik naciśnie Enter,
        // to wywołamy zdarzenie kliknięcia przycisku (czyli "fire" buttona)
        login.setOnAction(e -> button.fire());

        // Obsługa zdarzenia kliknięcia przycisku "Zaloguj"
        button.setOnAction(e ->
        {
            // Pobieramy wpisany przez użytkownika login i usuwamy spacje z początku i końca
            String username = login.getText().trim();

            // Sprawdzamy czy login nie jest pusty
            if (!username.isEmpty())
            {
                // Tworzymy nowy obiekt klasy Chat i przekazujemy login jako argument (np. do identyfikacji użytkownika)
                Chat chatApp = new Chat(username);

                // Tworzymy nową scenę i okno (Stage) dla aplikacji czatu
                Stage chatStage = new Stage();

                try
                {
                    // Uruchamiamy czat na nowym oknie
                    chatApp.start(chatStage);
                }
                catch (Exception ex)
                {
                    // Jeśli wystąpi błąd podczas uruchamiania czatu, wypisujemy go na konsolę
                    ex.printStackTrace();
                }

                // Zamykamy okno logowania (bo już mamy uruchomiony czat)
                stage.close();
            }
            else
            {
                // Jeśli login jest pusty, pokazujemy użytkownikowi ostrzeżenie w oknie dialogowym
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nazwa użytkownika nie może być pusta!");
                alert.showAndWait(); // czekamy aż użytkownik zamknie alert
            }
        });

        // Tworzymy etykietę "Login:" obok pola tekstowego
        Label label = new Label("Login:");

        // Układ poziomy (HBox) z odstępami 10 pikseli pomiędzy elementami,
        // który zawiera etykietę, pole do wpisywania loginu oraz przycisk "Zaloguj"
        HBox hbox = new HBox(10, label, login, button);

        // Tworzymy scenę (okno aplikacji) o wymiarach 400x100 pikseli,
        // która zawiera nasz poziomy układ hbox
        Scene scene = new Scene(hbox, 400, 100);

        // Ustawiamy scenę na głównym oknie (stage)
        stage.setScene(scene);

        // Ustawiamy tytuł okna na "Logowanie do chatu"
        stage.setTitle("Logowanie do chatu");

        // Pokazujemy okno użytkownikowi
        stage.show();
    }

    // Metoda main jest punktem startowym programu
    // Wywołuje launch(), która uruchamia aplikację JavaFX i wywołuje start(Stage)
    public static void main(String[] args)
    {
        launch();
    }
}