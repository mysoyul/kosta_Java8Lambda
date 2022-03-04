package lambdasinaction._02stream.basic2;

import lambdasinaction._02stream.basic1.Dish;

import java.util.Optional;

public class _04Finding {

    public static void main(String...args){
        if(isVegetarianFriendlyMenu()){
            System.out.println("Vegetarian friendly");
        }

        System.out.println(isHealthyMenu());
        System.out.println(isHealthyMenu2());
        
        Optional<Dish> optionalDish = findVegetarianDish();
        System.out.println(optionalDish.isPresent());
        optionalDish.ifPresent(d -> System.out.println(d.getName()));
    }

    //1. anyMatch
    private static boolean isVegetarianFriendlyMenu(){
        return Dish.menu.stream().anyMatch(Dish::isVegetarian);
    }
    //2.allMatch
    private static boolean isHealthyMenu(){
        return Dish.menu.stream().allMatch(dish -> dish.getCalories() <= 800);
    }

    //3. noneMatch
    private static boolean isHealthyMenu2(){
        return Dish.menu.stream().noneMatch(dish -> dish.getCalories() > 800);
    }
    //4. findAny
    private static Optional<Dish> findVegetarianDish(){
        //return Optional.empty();
        return Dish.menu.stream()
                .filter(Dish::isVegetarian)
                .findFirst();
                //.findAny();
    }
    
}
