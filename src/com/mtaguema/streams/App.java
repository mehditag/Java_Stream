package com.mtaguema.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {

  public static void main(String[] args) throws Exception {
    List<BankAccount> bankAccounts = List.of(
      new BankAccount("A", 20,BankAccountType.SAVINGS),
      new BankAccount("B", 50, BankAccountType.CURRENT),
      new BankAccount("C", -100,BankAccountType.CURRENT)
    );

    //Imperative, expliquer comment
    var count1 = 0;
    for (var bankAccount : bankAccounts) {
      if (bankAccount.getBalance() < 0) count1++;
    }

    //Declarative -> Quoi
    var count2 = bankAccounts
      .stream()
      .filter(bankAccount -> bankAccount.getBalance() < 0)
      .count();

    System.out.println(count1);
    System.out.println(count2);

    //Création d'un stream
    //A partir d'une collection
    List<Integer> myNumbers = new ArrayList<>();
    myNumbers.stream();

    //A partir d'un tableau
    int[] myNum = { 1, 2, 3, 4, 5 };
    Arrays.stream(myNum);

    //Directement
    Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);

    //Stream infini
    //1ere methode
    Stream<Double> stream2 = Stream.generate(() -> Math.random());
    stream2.limit(5).forEach(a -> System.out.println(a));

    //2eme methode
    Stream.iterate(1, x -> x + 1).limit(20).forEach(x -> System.out.println(x));

    //MAPPING
    //A partir d'une liste
    bankAccounts
      .stream()
      .map(bankAccount -> bankAccount.getHolder())
      .forEach(holder -> System.out.println(holder));

    //FLAT MAP
    List<List<BankAccount>> bankAccounts2 = List.of(
      (bankAccounts),
      List.of(
        new BankAccount("D", 20,BankAccountType.CURRENT),
        new BankAccount("E", 50,BankAccountType.CURRENT),
        new BankAccount("F", 100,BankAccountType.SAVINGS)
      )
    );

    bankAccounts2
      .stream()
      .flatMap(list -> list.stream())
      .map(ba -> ba.getHolder())
      .forEach(holder -> System.out.println(holder));


    //FILTRAGE
    List<BankAccount> bankAccounts3 = List.of(
        new BankAccount("A", 20,BankAccountType.SAVINGS),
        new BankAccount("B", 50, BankAccountType.CURRENT),
        new BankAccount("C", -100,BankAccountType.CURRENT),
        new BankAccount("D", 20,BankAccountType.CURRENT),
        new BankAccount("E", 50,BankAccountType.CURRENT),
        new BankAccount("F", 100,BankAccountType.SAVINGS)
    );

    bankAccounts3
        .stream()
        .filter(ba->ba.getBalance()<0)
        .map(ba->ba.getHolder())
        .forEach(holder->System.out.println(holder+" a un solde négatif"));


    //Slicing de stream
    /*bankAccounts
        .stream()
        .takeWhile(ba->ba.getBalance()>0)
        .forEach(ba->System.out.println(ba.getHolder()));*/

    //Tri des streams
    bankAccounts3
      .stream()
      //.sorted((a,b)->Double.compare(a.getBalance(), b.getBalance()))
      .sorted(Comparator.comparingDouble(BankAccount::getBalance))
      .forEach(ba->System.out.println(ba.getHolder()));

    //Différenciation des éléments dans le stream

    List<BankAccount> bankAccounts4 = bankAccounts3;

    System.out.println("Diff des streams avec redondance");
    bankAccounts4
        .stream()
        .map(BankAccount::getHolder)
        .forEach(System.out::println);
    System.out.println("Diff des streams SANS redondance");
    bankAccounts4
        .stream()
        .map(BankAccount::getHolder)
        .distinct()
        .forEach(System.out::println);


    //Observation des éléments d'un stream
    bankAccounts3
       .stream()
       .filter(ba->ba.getBalance()<0)
       .peek(ba->System.out.println("Filter "+ba.getHolder()))
       .map(ba->ba.getHolder())
       .peek(ba->System.out.println("Map "+ba))
       .forEach(holder->System.out.println(holder+" a un solde négatif"));

    System.out.println("Les Reducers");

    //Reducers -> utilisé à la fin d'un stream
    var result = bankAccounts
      .stream()
      .count();
    
    var result2 = bankAccounts
        .stream()
        //Les matchers
        //.anyMatch(ba->ba.getBalance()<0);
        //.allMatch(ba->ba.getBalance()<0);
        //.noneMatch(ba->ba.getBalance()<0);
        .findFirst().get().getHolder()
      ;

    System.out.println(result2);

    var result3 = bankAccounts
      .stream()
      .min(Comparator.comparingDouble(BankAccount::getBalance))
      .get().getHolder();
    System.out.println(result3);


    System.out.println("Reduction de stream");

    //Reduction des streams
    var result4 = bankAccounts
      .stream()
      .map(BankAccount::getBalance)
      .reduce((a,b)->a+b).get();

    System.out.println(result4);

    //Interface Collector et Stream
    System.out.println("Utilisation de l'interface Collector");

    var result5 = bankAccounts
      .stream()
      .filter(ba->ba.getBalance()>0)
      .collect(Collectors.summarizingDouble(BankAccount::getBalance));

    var result6 = bankAccounts
      .stream()
      .filter(ba->ba.getBalance()>0)
      .map(BankAccount::getHolder)
      .collect(Collectors.joining("; "));  

      System.out.println(result6);


    //Groupement des éléments 
    System.out.println("Groupement des éléments");

    var result7 = bankAccounts
     .stream()
     .collect(Collectors.groupingBy(BankAccount::getType,
        Collectors.mapping(b->b.getHolder(),Collectors.joining(", "))));

     System.out.println(result7);

    //Partitionnement des éléments
    System.out.println("Groupement des éléments par prédicat, quels comptes sont négatifs ?");
    var result8 = bankAccounts.
      stream().
      collect(Collectors.partitioningBy(b->b.getBalance()>0,
        Collectors.mapping(BankAccount::getHolder,Collectors.joining(", "))));

      System.out.println(result8);

    //Stream de type primitifs
    System.out.println("Stream primitifs");
    IntStream.range(1, 10)
      .forEach(System.out::println); 


    

  

    



  }
}
