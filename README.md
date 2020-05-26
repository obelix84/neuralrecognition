# neuralrecognition Java + MATLAB
Распознвание номерного знака с использованием Неокогнитрона Фукушимы на Java + MATLAB.
Для соединения контуров используется алгоритм "пьяного жука", для заливки контуров клеточный автомат.
Так же содержится вспомогательные классы для чтения изображений из BMP файлов и работы с ними.
В папке Images вспомогательные сценарии на MATLAB, для формирования обучающей выборки и процессинга изображений.
В папке src исходный код Java.
### Некогнитрон Фукушимы:
- ComplexNeuron.java
- Cplane.java
- SimpleNeuron.java
- Splane.java
- NeoClassic.java

Конфигурация неокогнитрона в папке Config

### Клеточный автомат для заливки контуров
- CellularAutomata.java
- Prim.java
- PrimInside.java

### Алгоритм пьяного жука
- DrunkenBug.java
- Contour.java
- PointOfContour.java
