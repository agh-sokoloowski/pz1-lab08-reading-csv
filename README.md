# Laboratorium 8 - województwa, powiaty, gminy, miejscowości (kontynuacja)

## Klasa BoundingBox v.2

Napisz docelową klasę `BoundingBox`. Jest to prostokąt otaczający jednostkę. Do zdefiniowania prostokąta wystarczą dwa przeciwległe punkty, zamiast pięciu.

Zaprojektuj `BoundingBox` tak, aby miał dwa stany:

- `BoundingBox` może być pusty
- albo definiuje prostokąt (w tym zdegenerowany do pojedynczego punktu)

**Sam(a) zadecyduj, jak je odróżnić, np. stosując specjalne wartości `Double.NaN` albo dodając flagę pusty/niepusty.**

```java
public class BoundingBox {
    double xmin = ???;
    double ymin = ???;
    double xmax = ???;
    double ymax = ???;
 
    /**
 * Powiększa BB tak, aby zawierał punkt (x,y) 
 * @param x - współrzędna x
 * @param y - współrzędna y
 */
    void addPoint(double x, double y) {}
 
    /**
 * Sprawdza, czy BB zawiera punkt (x,y)
 * @param x
 * @param y
 * @return
 */
    boolean contains(double x, double y) {
        return false;
    }
 
    /**
 * Sprawdza czy dany BB zawiera bb 
 * @param bb
 * @return
 */
    boolean contains(BoundingBox bb) {
        return false;
    }
 
    /**
 * Sprawdza, czy dany BB przecina się z bb
 * @param bb
 * @return
 */
    boolean intersects(BoundingBox bb) {
        return false;
    }
    /**
 * Powiększa rozmiary tak, aby zawierał bb oraz poprzednią wersję this
 * @param bb
 * @return
 */
    BoundingBox add(BoundingBox bb) {
        return this;
    }

    /**
 * Sprawdza czy BB jest pusty
 * @return
 */
    boolean isEmpty() {
        return true;
    }
 
    /**
 * Oblicza i zwraca współrzędną x środka
 * @return if !isEmpty() współrzędna x środka else wyrzuca wyjątek
 * (sam dobierz typ)
 */
    double getCenterX() {
        throw new RuntimeException("Not implemented");
    }

    /**
 * Oblicza i zwraca współrzędną y środka
 * @return if !isEmpty() współrzędna y środka else wyrzuca wyjątek
 * (sam dobierz typ)
 */
    double getCenterY() {
        throw new RuntimeException("Not implemented");
    }
 
    /**
 * Oblicza odległość pomiędzy środkami this bounding box oraz bbx
 * @param bbx prostokąt, do którego liczona jest odległość
 * @return if !isEmpty odległość, else wyrzuca wyjątek lub zwraca maksymalną możliwą wartość double
 * Ze względu na to, że są to współrzędne geograficzne, zamiast odległości użyj wzoru haversine
 * (ang. haversine formula)
 *
 * Gotowy kod można znaleźć w Internecie...
 */
    double distanceTo(BoundingBox bbx) {
        throw new RuntimeException("Not implemented");
    }
}
```

### Odczyt z pliku v.4

- Uzupełnij wartości `BoundingBox` podczas odczytu.
- Dodaj w klasie `BoundingBox` metodę `toString()` i uwzględnij jej wywołanie w `AdminUnit.toString()`
- Sprawdź, czy wartości współrzędnych czytane są poprawnie

### Opcjonalnie: Gdyby ktoś chciał zobaczyć BoundingBox na mapie...

Podczas czytania rekordów wydrukuj

```java
System.out.printf(
    Locale.US,
    "LINESTRING(%f %f, %f %f, %f %f, %f %f,%f %f)", 
    reader.getDouble("x1"),
    reader.getDouble("y1"),
    reader.getDouble("x2"),
    reader.getDouble("y2"),
    reader.getDouble("x3"),
    reader.getDouble("y3"),
    reader.getDouble("x4"),
    reader.getDouble("y4"),
    reader.getDouble("x5"),
    reader.getDouble("y5")
);
```

Np. dla Krakowa będzie to

```
LINESTRING(19.7922353426102 49.9676667885393,19.7922353426102 50.1261382575516,20.2173454438402 50.1261382575516,20.2173454438402 49.9676667885393,19.7922353426102 49.9676667885393)
```

Skopiuj wynik i wklej na stronie http://arthur-e.github.io/Wicket/sandbox-gmaps3.html

Oczywiście możesz też wypisać odpowiedni tekst na podstawie współrzędnych w `BoundingBox`, a nawet dodać metodę, która to robi do klasy. Metoda np. może nazywać się getWKT() - WKT (Well Known Text) to format reprezentacji wektorów https://en.wikipedia.org/wiki/Well-known_text.

## Szukamy sąsiadów dla AdminUnit

Zdefiniujmy wpierw pojęcie sąsiada. W typowym przypadku sąsiadem danej jednostki ma być jednostka

- na tym samym poziomie hierarchii (czyli sąsiadami województw mają być województwa, powiatów – powiaty, gmin – gminy, a miejscowości - leżące w pobliżu miejscowości
- podstawowym kryterium (dla województw, powiatów i gmin) ma być pokrywanie się `BoundingBox` testowane za pomocą metody `intersects()`

Miejscowości mogą być oddalone i ich granice nie będą się pokrywały. Stąd kryterium pokrywania nie wystarczy. Przyjmiemy więc, że sąsiadujące miejscowości położone są w odległości mniejszej niż pewien konfigurowalny parametr `maxdistance`, np. równy 15 km. Odległości mają być liczone od środków `BoundingBox`

Napisz funkcję klasy `AdminUnitList`

```java
/**
 * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level. 
 * Czyli sąsiadami wojweództw są województwa, powiatów - powiaty, gmin - gminy, miejscowości - inne miejscowości
 * @param unit - jednostka, której sąsiedzi mają być wyznaczeni
 * @param maxdistance - parametr stosowany wyłącznie dla miejscowości, maksymalny promień odległości od środka unit, 
 *                    w którym mają sie znaleźć punkty środkowe BoundingBox sąsiadów
 * @return lista wypełniona sąsiadami
 */
AdminUnitList getNeighbors(AdminUnit unit, double maxdistance) {
    throw new RuntimeException("Not implemented");
}
```

Zademonstruj w kodzie (np. testach), że jesteś w stanie:

- znaleźć wybraną jednostkę na danym poziomie hierarchii
- wyznaczyć i wypisać listę sąsiadów
- sprawdź wyniki na mapie...

Oblicz czas wyszukiwania sąsiadów

```java
double t1 = System.nanoTime()/1e6;
// wywołanie funkcji
double t2 = System.nanoTime()/1e6;
System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
```

## Usprawnienie algorytmu - punkty dodatkowe

Najprostsza metoda realizacji algorytmu wyszukiwania sąsiadów polega na iteracji przez wszystkie elementy listy i sprawdzenie, czy są sąsiadami (zgodnie ze zdefiniowanymi kryteriami). Gdybyśmy chcieli wyznaczyć sąsiadów dla wszystkich obiektów AdminUnit złożoność obliczeniowa będzie O(n<sup>2</sup>). Biorąc pod uwagę, że n~15000, da to 22500000 testów sąsiedztwa, czyli raczej dużo...

Dysponujemy jednak hierarchią obiektów (`parent` → `children`) - potencjalnie drzewem z nieistniejącym korzeniem, którego potomkami są województwa, dalej powiaty i gminy. Jeżeli z niej skorzystamy - pozwoli to nam zapewne przyspieszyć obliczenia.

Zaimplementuj algorytm wyszukiwania dla R-tree opisany w [Wikipedii (sekcja Search)](https://en.wikipedia.org/wiki/R-tree#Search). Dla województwa, powiatu lub gminy użyj zdefiniowanego `BoundingBox`. Dla miejscowości - po prostu rozszerz środek o `maxdistance` w każdym kierunku (ale zastosuj kryterium odległości). Porównaj czasy wykonania dla wersji liniowej i wykorzystującej drzewo `parent` → `children`.
