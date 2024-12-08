package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class Task8Test {
  @Mock
  PersonService personService;

  @InjectMocks
  Task8 task8;

  @Test
  public void test() {
    Person person1 = new Person(1, "Name1", null, null, Instant.now());
    Person person2 = new Person(2, "Name2", null, null, Instant.now());
    Person person3 = new Person(3, "Name3", null, null, Instant.now());
    Resume resume1 = new Resume(1, 1, "text1");
    Resume resume2 = new Resume(1, 2, "text1");
    Resume resume3 = new Resume(2, 3, "text1");
    Resume resume4 = new Resume(2, 4, "text1");
    lenient().when(personService.findResumes(eq(Set.of(1, 2, 3))))
        .thenReturn(Set.of(resume1, resume2, resume3, resume4));
    lenient().when(personService.findResumes(eq(List.of(1, 2, 3))))
        .thenReturn(Set.of(resume1, resume2, resume3, resume4));

    Set<PersonWithResumes> personWithResumes = task8.enrichPersonsWithResumes(List.of(
        person1,
        person2,
        person3
    ));

    assertEquals(
        Set.of(
            new PersonWithResumes(person1, Set.of(resume1, resume2)),
            new PersonWithResumes(person2, Set.of(resume3, resume4)),
            new PersonWithResumes(person3, Set.of())
        ),
        personWithResumes
    );

  }
}
