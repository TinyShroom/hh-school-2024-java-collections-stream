package tasks;

import common.Person;
import common.PersonService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
/*
Асимптотика O(n)
*/
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Map<Integer, Person> persons = personService.findPersons(personIds).stream()
        .collect(Collectors.toMap(Person::id, Function.identity()));
    return personIds.stream()
        .map(persons::get)
        .collect(Collectors.toList());
  }
}
