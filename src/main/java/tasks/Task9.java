package tasks;

import common.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    // Лаконичнее
    // можно использовать skip()
    // не упадёт с пустым списком
    // не упадёт с неизменяемым списком
    // не изменяется исходный список
    // удаление первого элемента может иметь временную сложность O(n)
    return persons.stream()
        .skip(1)
        .map(Person::firstName)
        .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // Лаконичнее. Set вернёт различные имена без distinct()
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    // Возможно ошибка: второй раз использовалось secondName()
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  // Лаконичнее и без создания дополнительных переменных
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
        .collect(Collectors.toMap(Person::id, this::convertPersonToString, (a, b) -> a));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // Достаточно найти первое совпадение
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> person1Set = new HashSet<>(persons1);
    return persons2.stream()
        .anyMatch(person1Set::contains);
  }

  // Посчитать число четных чисел
  // Можно без создания дополнительной переменной
  // Переменные следует создавать с наименьшей областью видимости
  // Так их проще отслеживать
  // Они будут недоступны из других частей программы: их никто не изменит извне и изменение их в методе не окажет влияния на поведение других частей программы

  // Странно, что метод принимает Stream. На лекции упоминалось, что так делать нельзя, т.к. стрим одноразовый
  // Возможно, было бы лучше, если бы метод принимал коллекцию, но не решился менять сигнатуру метода
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  // Т.к. хранимые числа по значению не превышают capacity, их хеш равен их значению.
  // Поэтому они выводятся в порядке возрастания
  // Если, например, добавить в пустой HashSet числа 0, 1, 2, 3, 16, 32, то порядок будет [0, 16, 32, 1, 2, 3],
  // т.к. остаток от деления хеша на capacity для 0, 16, 32 будет совпадать, возникнет коллизия и храниться они будут в одном bucket в виде связного списка
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
