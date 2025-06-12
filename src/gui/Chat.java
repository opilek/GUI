package gui; // Pakiet, w którym znajduje się ta klasa

// Importy potrzebnych klas z JavaFX
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class Chat extends Application
{

    // Tworzymy komponenty GUI jako pola klasy, aby mieć do nich dostęp w różnych metodach

    // Pole tekstowe, które będzie zawierało historię czatu
    private final TextArea textArea = new TextArea();

    // Pole, w które użytkownik wpisuje nową wiadomość
    private final TextField textField = new TextField();

    // Lista użytkowników czatu (na razie pusta, ale może być używana w przyszłości)
    private final ListView<String> listView = new ListView<>();

    // Przycisk do wysyłania wiadomości
    private final Button button = new Button("Wyślij");

    private final String username;

    public Chat(String username)
    {
        this.username=username;
    }

    // Metoda odpowiadająca za "wysłanie" wiadomości (czyli: dopisanie do okna czatu)
    private void sendMessage()
    {
        // Pobieramy tekst z pola tekstowego i usuwamy nadmiarowe spacje
        String message = textField.getText().trim();

        // Jeśli wiadomość nie jest pusta (czyli coś wpisano), wykonujemy akcję
        if (!message.isEmpty())
        {
            // Dodajemy wiadomość do głównego pola tekstowego (czat), na końcu z nową linią
            textArea.appendText(username + ": " + message + "\n");

            // Czyścimy pole wpisywania, by można było wpisać nową wiadomość
            textField.clear();
        }
    }

    // Główna metoda startująca GUI – wywoływana automatycznie przez JavaFX
    @Override
    public void start(Stage stage)
    {
        // Zablokowanie edycji textArea, bo to pole tylko do odczytu czatu
        textArea.setEditable(false);

        // Tworzymy dolny pasek z polem wpisywania i przyciskiem
        HBox bottomBox = new HBox(10, textField, button); // odstęp 10 pikseli między elementami
        bottomBox.setSpacing(10); // dodatkowo ustawiamy odstęp (niekonieczne, ale czytelne)
        bottomBox.setPadding(new Insets(10)); // odstęp od krawędzi kontenera

        // Używamy BorderPane jako głównego układu
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(textArea);  // środek – historia wiadomości
        borderPane.setRight(listView);   // prawa strona – lista użytkowników
        borderPane.setBottom(bottomBox); // dół – pole wpisywania i przycisk
        borderPane.setPadding(new Insets(10)); // marginesy całego BorderPane

        // Tworzymy scenę z głównym kontenerem i rozmiarami okna
        Scene scene = new Scene(borderPane, 600, 400);

        // Obsługa kliknięcia przycisku – po kliknięciu wywoła sendMessage()
        button.setOnAction(e -> sendMessage());

        // Obsługa naciśnięcia Enter w polu tekstowym – też wywoła sendMessage()
        textField.setOnAction(e -> sendMessage());

        // Ustawienie sceny i pokazanie okna
        stage.setScene(scene);
        stage.setTitle("Chat"); // Tytuł okna
        stage.show(); // Wyświetlenie okna
    }

    // Główna metoda uruchamiająca aplikację JavaFX
    public static void main(String[] args)
    {
        launch(); // Wywołuje metodę start() i uruchamia GUI
    }
}