# System obsługi przychodni "MedApp"
### Medyczne systemy informacyjne

## Zespół
- Przemysław Sałek
  - https://github.com/ShocK1999
- Szymon Sala
  - https://github.com/szymix1999
- Aleksander Pitucha
  - https://github.com/PituchaAleksander
- Jan Starosta
  - https://github.com/janjanek
- Maciej Rydzak
  - https://github.com/kazdyrkeicam

## Technologie
- IntelliJ IDEA Community Edition 2019.2.3+
- Java 15.0.1 (Apache Maven, JavaFX, JDBC)
- Azure Database for MySQL

## Opis
### Legenda
~~zrobione~~/nie zrobione
### Wymagania 
- Przygotowanie środowiska oraz wybór technologii - **Szymon Sala**
- Uzytkownicy - **Przemysław Sałek**
  - ~~podstawowe tabele bazy danych~~ - **wszyscy**
  - ~~logowanie~~
  - ~~podział na typy uzytkowników~~ (~~aptekarz, lekarz, pacjent, gosc, administrator, osoby z rejestracji, ksiegowa, kierownik~~)
  - ~~w zaleznosci od typu uzytkownika, wyswietla sie inny panel - oprogramowanie wyswietlanego panelu nalezy do innych osób z projektu~~
  - ~~udostepnic mozliwa zmiane jezyka aplikacji~~ (~~ustawienie uzytkownika~~)
  - umozliwic ustawienie własnego menu (elementy menu w zaleznosci od uprawnien/typu uzytkownika)
  - ~~notatnik uzytkownika~~
  - ~~kalendarz uzytkownika~~ (~~moze byc to integracja z Google Calendar~~)
  - mozliwosc wydruku planu dnia (np. lekarza)
- Baza - **wszyscy**
  - baza medycznych danych pacjentów wg klasyfikacji ICD-10
  - aktualizacja informacji o danych na podstawie komunikacji z innymi bazami (poprzez projekt komunikacji z pozostałymi projektami)
  - analiza diagnostyczna (np. czy ten pacjent nie ma leków kumulujacych nadmiernie efekty, znoszacych sie, zdiagnozowane uczulenia)
  - ~~obrazy moga byc w postaci jpg, ale dla chetnych takze do opracowania DICOM lub jego elementy~~
  - ~~zapewnienie anonimizacji~~ (~~np. posrednio: nazwisk-¿id, id-¿dane;~~ kodowanie tresci diagnoz i danych uniejednoznaczniajacych), ~~nie nalezy pobierac ani wysyłac danych identyfikacyjnych z obcych baz - jedynie jako zapytania~~, ~~ew. przesyłac ich uogólnienie (np. 27 lat -> zakres 20-29 lat)~~, identyfikatory unikalne dla kazdego z pytajacych.
- Apteka - **Aleksander Pitucha**
  - ~~tabela mozliwych do kupienia leków~~
  - ~~wyszukiwarka leków~~
  - ~~stany magazynowe (dostawy, sprzedaz, zwroty, utylizacja)~~
  - ~~realizowanie recept~~
  - ~~znane zastepniki (na podstawie zapisów lub składu leków)~~
  - ~~zdjecia opakowan~~
  - ~~tworzenie nowych zamówien (pacjent, aptekarz) i wydruk paragonów~~ wraz z zatwierdzeniem
  - ~~realizacji recepty/zamówienia (aptekarz)~~
- Panel lekarza - **Maciej Rydzak**
  - ~~tabela lekarzy~~
  - wizyty zintegrowane z kalendarzem
  - dostep do danych medycznych pacjenta/karta pacjenta
  - mozliwosc wyszukania danych w innych bazach (na bazie interfejsu projektu komunikacji miedzy projektami)
  - diagnozy
  - wypisywanie recept
  - zlecenia medyczne/skierowania
- Pacjent - **Przemysław Sałek**
  - ~~tabela pacjentów~~
  - ~~zapisane wizyty~~ (~~zapisy~~/~~zmiany~~)
  - ~~reklamy~~ (~~miejce na gify i zasady wyswietlania~~)
  - ~~informacja o stanie zdrowia~~
  - ~~zapisane recepty~~
  - ~~mozliwosc dołaczenia nowych badan (zdjecia rentgenowskie), skierowan~~
  - ~~mozliwosc zbiorczej opłaty za wizyty~~, ~~badania~~, ~~leki~~
  - ~~mozliwosc wyszukania specjalisty w róznych przychodniach~~ (~~na bazie projektu komunikacji miedzy bazami~~)
- Administracja przychodni - **Szymon Sala**
  - ~~telefoniczna rejestracja pacjentów~~
  - ~~baza pracowników~~
  - ~~zatrudnianie/zwalnianie/wolne etaty~~
  - ~~wynagrodzenia/podwyzki/ciecia~~
  - umowy/typy umów/czesci etatów
  - ~~inwentaryzacja~~
- Administracja systemem - **Jan Starosta**
  - nadawanie uprawnien
  - modyfikacje kont
  - blokady systemu/uzytkowników
- Ksiegowosc - **nie przydzielone**
  - ~~przychody, dochody, koszty, odpisy, amortyzacja~~
  - składki
  - ~~raporty okresowe~~
  - forma: tabelaryczna + druga, dowolnie wybrana - wybór techniki na poziomie przychodni Formy i techniki ksiegowosci
- Komunikacja - **nie przydzielone**
  - wyszukiwanie danych o pacjentach (+wizyty, leki, skierowania, badania) i przekazywanie informacji o istnieniu/zródle tych danych
  - ~~wyszukiwanie danych o dostepnosci leków w aptekach róznych przychodni~~
  - wyszukiwanie danych o wolnych terminach specjalistów w danej dziedzinie w innych przychodniach
  - wyszukiwanie danych o wolnych etatach/zapotrzebowaniach na specjalistów w danej dziedzinie
