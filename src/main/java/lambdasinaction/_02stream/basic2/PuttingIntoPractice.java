package lambdasinaction._02stream.basic2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

public class PuttingIntoPractice{
    public static void main(String ...args){
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );


        // Query 1: Find all transactions from year 2011 and sort them by value (small to high).
        transactions.stream() //Stream<Transaction>
                .filter(tx -> tx.getYear() == 2011)
                .sorted(comparingInt(Transaction::getValue))
                .forEach(System.out::println);

        // Query 2: What are all the unique cities where the traders work? List<String>
        List<String> cityList = transactions.stream() //Stream<Transaction>
                .map(tx -> tx.getTrader().getCity()) //Stream<String>
                .distinct() //Stream<String>
                .collect(Collectors.toList()); //List<String>

        cityList.forEach(System.out::println);

        // Query 3: Find all traders from Cambridge and sort them by name. List<String>
        transactions.stream() //Stream<Transaction>
                .map(Transaction::getTrader) //Stream<Trader>
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // Query 4: Return a string of all traders names sorted alphabetically. String
        String names = transactions.stream() //Stream<Transaction>
                .map(tx -> tx.getTrader().getName()) //Stream<String>
                .distinct()
                .sorted()
                .reduce("", (s1, s2) -> s1 + s2);
        System.out.println("names = " + names);

        // Query 5: Are there any trader based in Milan? boolean
        boolean milanBased = transactions.stream()
                .anyMatch(tx -> tx.getTrader().getCity().equals("Milan"));
        System.out.println("milanBased = " + milanBased);

        // Query 6: Update all transactions so that the traders from Milan are set to Cambridge.
        transactions.forEach(System.out::println);

        transactions.stream()
                .map(Transaction::getTrader)
                .filter(tr -> tr.getCity().equals("Milan"))
                .forEach(tr -> tr.setCity("Cambridge"));
        System.out.println("--- After ");
        transactions.forEach(System.out::println);

        // Query 7: What's the highest value in all the transactions?
        int highValue = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max()
                .orElse(0);
        System.out.println("highValue = " + highValue);

    }
}