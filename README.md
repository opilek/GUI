## 📦 1. Struktura aplikacji JavaFX
 🧱 Szkielet aplikacji
Każda aplikacja JavaFX dziedziczy po klasie Application i implementuje metodę start.

```java
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MyApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // konfiguracja okna
    }

    public static void main(String[] args) {
        launch(); // wywołuje start()
    }
}
```
## 🧩 2. Podstawowe komponenty JavaFX (kontrolki)
Klasa	Opis
Label	Tekst statyczny (np. "Login:")
TextField	Jednoliniowe pole tekstowe
TextArea	Wieloliniowe pole tekstowe
Button	Przycisk do wywoływania akcji
ListView<T>	Lista elementów (np. użytkowników)
PasswordField	Pole tekstowe z ukrytym hasłem
CheckBox	Pole wyboru (tak/nie)
ComboBox<T>	Lista rozwijana

## 🧭 3. Układy (Layouts)
Klasa	Rozmieszczenie elementów
HBox	Poziomo (Horizontal Box)
VBox	Pionowo (Vertical Box)
BorderPane	Podział na regiony: top, bottom, center, left, right
GridPane	Siatka (wiersze x kolumny)
StackPane	Warstwowo (jeden na drugim)

# 📌 Przykład HBox i VBox
```java
HBox hbox = new HBox(10, new Label("Login:"), new TextField());
VBox vbox = new VBox(15, hbox, new Button("Zaloguj"));
```
## 🖱️ 4. Obsługa zdarzeń
# ✅ Obsługa kliknięcia
```java
Button button = new Button("Kliknij");
button.setOnAction(e -> System.out.println("Kliknięto!"));
```
# ✅ Obsługa Enter w TextField
```java
TextField field = new TextField();
field.setOnAction(e -> System.out.println("Wpisano: " + field.getText()));
```
## 🖼️ 5. Tworzenie okna (Stage, Scene)
```java
@Override
public void start(Stage stage) {
    Label label = new Label("Hello JavaFX!");
    Scene scene = new Scene(new VBox(label), 300, 200);
    stage.setScene(scene);
    stage.setTitle("Moje Okno");
    stage.show();
}
```
## 🔐 6. Przykład: Proste okno logowania

```java
@Override
public void start(Stage stage) {
    TextField login = new TextField();
    Button button = new Button("Zaloguj");
    Label label = new Label("Login:");

    button.setOnAction(e -> {
        String username = login.getText().trim();
        if (!username.isEmpty()) {
            System.out.println("Zalogowano jako: " + username);
        }
    });

    HBox layout = new HBox(10, label, login, button);
    Scene scene = new Scene(layout, 300, 100);
    stage.setScene(scene);
    stage.setTitle("Logowanie");
    stage.show();
}
```
## 📜 7. Przykład: Główne okno aplikacji (czat, edytor, itd.)

```java
TextArea output = new TextArea();
output.setEditable(false);

TextField input = new TextField();
Button send = new Button("Wyślij");

send.setOnAction(e -> {
    String msg = input.getText();
    if (!msg.isEmpty()) {
        output.appendText("Ty: " + msg + "\n");
        input.clear();
    }
});

input.setOnAction(e -> send.fire());

HBox bottom = new HBox(10, input, send);
BorderPane layout = new BorderPane();
layout.setCenter(output);
layout.setBottom(bottom);

Scene scene = new Scene(layout, 400, 300);
```
## 🧠 Dobre praktyki
✅ Grupuj komponenty w layoutach (VBox, HBox, BorderPane)
✅ Używaj setOnAction() do zdarzeń (np. kliknięcia)
✅ Stosuj TextArea.setEditable(false) do historii czatu
✅ Zawsze czyść pole po wysłaniu wiadomości: textField.clear()
✅ TextField.setOnAction(...) = ENTER działa jak przycisk

## 📁 Struktura typowej aplikacji GUI
less
Kopiuj
Edytuj
src/
├── Main.java           // punkt wejścia
├── LoginWindow.java    // okno logowania
├── ChatWindow.java     // główne okno czatu
├── ClientReceiver.java // odbieranie wiadomości (wątki)
## ✅ Gotowe szablony do wykorzystania
# 🔘 Przygotowanie Button
```java
Button btn = new Button("Kliknij mnie");
btn.setOnAction(e -> System.out.println("Kliknięto"));
```
# 🧾 Obsługa TextField i Enter
```java
TextField input = new TextField();
input.setOnAction(e -> System.out.println("Wpisano: " + input.getText()));
```
# 📐 Layout
```java
VBox vbox = new VBox(10, new Label("Witaj"), new TextField(), new Button("OK"));
Scene scene = new Scene(vbox, 300, 200);
stage.setScene(scene);
```
