package lambdasinaction._01lambda.basic2;

import java.util.*;
import java.util.function.Predicate;

public class FilteringApples{

    public static void main(String ... args){

        List<Apple> inventory = Arrays.asList(new Apple(80,"green"),
                new Apple(155, "green"),
                new Apple(120, "red"),
                new Apple(160, "brown"));

        Predicate<Apple> greenApple = apple -> apple.getColor().equals("green");

        // 람다식 사용 [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        filterApples(inventory, greenApple).forEach(System.out::println);

        System.out.println("--- GreenHeavyApples 람다식");
        // 람다식 사용[Apple{color='green', weight=155}]
        Predicate<Apple> greenHeavyApples = greenApple.and(apple -> apple.getWeight() > 150);
        filterApples(inventory, greenHeavyApples).forEach(apple -> System.out.println("apple = " + apple));

        System.out.println("--- GreenHeavyApples Method Reference");
        // Method Reference 사용 [Apple{color='green', weight=155}]
        filterApples(inventory, greenHeavyApples).forEach(System.out::println);

        // []
        Predicate<Apple> lightApple = apple ->  apple.getWeight() < 150;
        Predicate<Apple> brownOrlightApple = lightApple.or(apple -> apple.getColor().equals("brown"));

        List<Apple> weirdApples = filterApples(inventory, brownOrlightApple);
        System.out.println(weirdApples);
    }


    public static boolean isGreenApple(Apple apple) {
        return true;
    }

    public static boolean isHeavyApple(Apple apple) {
        return true;
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> predicate){
        List<Apple> appleList = new ArrayList<>();
        inventory.forEach(apple -> {if (predicate.test(apple)) appleList.add(apple);});
        return appleList;
    }
}
