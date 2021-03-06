package lambdasinaction._02stream.basic2;

import lambdasinaction._02stream.basic1.Dish;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lambdasinaction._02stream.basic1.Dish.menu;

public class _03Mapping {

    public static void main(String...args){

        //1. map - Dish의 name 목록만
        menu.stream() //Stream<Dish>
                .map(Dish::getName) //Stream<String>
                .collect(toList())  //List<String>
                .forEach(System.out::println);

        //2. IntStream의 sum() 사용한 칼로리 합계
        int sum = menu.stream()
                .mapToInt(Dish::getCalories) //IntStream
                .sum();
        System.out.println("sum = " + sum);

        //2.1 Stream의 reduce() 사용한 칼로리 합계
        sum = menu.stream()
                .map(Dish::getCalories)
                .reduce((prev,curr) -> prev + curr)
                .orElse(0);
        System.out.println("sum = " + sum);

        sum = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, (prev,curr) -> prev + curr);
        System.out.println("sum = " + sum);

        // map
        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                                         .map(String::length)
                                         .collect(toList());
        System.out.println(wordLengths);

        //2. map - 중복된 문자 제거한 word 리스트
        List<String> stringList = List.of("Hello", "World");

        stringList.stream() //Stream<String>
                .map(word -> word.split("")) //Stream<String[]>
                .distinct()
                .collect(toList())
                .forEach(System.out::println);

        System.out.println("--- flatMap() 시작");
        //3.map(), flatMap() - 중복된 문자 제거가 word 리스트
        stringList.stream() //Stream<String>
                .map(word -> word.split("")) //Stream<String[]>
                //Arrays.stream() => Stream<T> stream(T[] array)
                .flatMap(wordArray -> Arrays.stream(wordArray)) //Stream<String>
                .distinct()
                .collect(toList())
                .forEach(System.out::print);

        System.out.println("\n--- flatMap() ");

        stringList.stream() //Stream<String>
                .flatMap(word -> Arrays.stream(word.split("")))
                .distinct()
                .collect(toList())
                .forEach(System.out::print);

        System.out.println("\n--- flatMap() 끝");


        // flatMap
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);
        List<int[]> pairs =
                        numbers1.stream()
                                .flatMap((Integer i) -> numbers2.stream()
                                                       .map((Integer j) -> new int[]{i, j})
                                 )
                                .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                                .collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));

        //----- flatMap 사용하기
        List<Customer> customers = List.of(new Customer(101, "john", "john@gmail.com", Arrays.asList("397937955", "21654725")),
                new Customer(102, "smith", "smith@gmail.com", Arrays.asList("89563865", "2487238947")),
                new Customer(103, "peter", "peter@gmail.com", Arrays.asList("38946328654", "3286487236")),
                new Customer(104, "kely", "kely@gmail.com", Arrays.asList("389246829364", "948609467")));

        //Stream<R> map(Function<? super T,? extends R> mapper)
        List<List<String>> phoneList = customers.stream()  //Stream<Customer>
                .map(customer -> customer.getPhoneNumbers()) //Stream<List<String>>
                .collect(toList());//List<List<String>>
        System.out.println("phoneList = " + phoneList);

        //Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
        List<String> phoneList2 = customers.stream()  //Stream<Customer>
                .flatMap(customer -> customer.getPhoneNumbers().stream()) //Stream<String>
                .collect(toList());//List<String>
        System.out.println("phoneList2 = " + phoneList2);

    }

    static class Customer {

        private int id;
        private String name;
        private String email;
        private List<String> phoneNumbers;
        

        public Customer(int id, String name, String email, List<String> phoneNumbers) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phoneNumbers = phoneNumbers;
        }

        public String getEmail() {
            return email;
        }

        public List<String> getPhoneNumbers() {
            return phoneNumbers;
        }
    }
}


