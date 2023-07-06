# change-making-problem-with-limits

## About

This project is a solution to the following task:

>Napisz program konsolowy, który przyjmuje na wyjściu nieokreśloną liczbę reszt do
>wydania. Na starcie kasa drobnych do wydawania ma następujący stan:\
>5 zł: 1 szt.\
>2 zł: 3 szt.\
>1 zł: 5 szt.\
>50 gr: 10 szt.\
>20 gr: 20 szt.\
>10 gr: 200 szt.\
>5 gr: 100 szt.\
>2 gr: 100 szt.\
>1 gr: 10000 szt.
>
>Program ma wypisać jakie monety mają być użyte dla wydania poszczególnych reszt,
>aby użyć jak najmniej monet (pamiętając o tym, że poprzednie monety schodzą ze
>stanu i nie mogą być używane przy wydawaniu kolejnych reszt).
>
>Przykład:\
>Dla reszty 1.30 zł:\
>Wydaj 1 monet 1 zł\
>Wydaj 1 monet 20 gr\
>Wydaj 1 monet 10 gr
> 
>Dla reszty 11.70 zł:\
>Wydaj 1 monet 5 zł\
>Wydaj 3 monet 2 zł\
>Wydaj 1 monet 50 gr\
>Wydaj 1 monet 20 gr
> 
>Dla reszty 6.70 zł:\
>Wydaj 4 monet 1 zł\
>Wydaj 5 monet 50 gr\
>Wydaj 1 monet 20 gr
> 
>Dla reszty 4.30 zł:\
>Wydaj 4 monet 50 gr\
>Wydaj 11 monet 20 gr\
>Wydaj 1 monet 10 gr

The solution makes use of the dynamic programming technique.

## Running the application

The project is a standard Maven project. It requires **Java 8**.\
You can import the project to your IDE of choice as you would with any
Maven project and run it directly there.\
The project contains unit tests that can be run in order to check the solution.

You can run the app using included JAR: `target/change-making.jar`

## Example
```
java -jar target/change-making.jar 1.30 11.70 6.70 4.30
Dla reszty 1.3 zł:
Wydaj 1 monet 1 zł
Wydaj 1 monet 20 gr
Wydaj 1 monet 10 gr

Dla reszty 11.7 zł:
Wydaj 1 monet 5 zł
Wydaj 3 monet 2 zł
Wydaj 1 monet 50 gr
Wydaj 1 monet 20 gr

Dla reszty 6.7 zł:
Wydaj 4 monet 1 zł
Wydaj 5 monet 50 gr
Wydaj 1 monet 20 gr

Dla reszty 4.3 zł:
Wydaj 4 monet 50 gr
Wydaj 11 monet 20 gr
Wydaj 1 monet 10 gr
```

## Notes

- You can pass values as input arguments,
- You can run the app without input arguments in order to activate interactive mode.
