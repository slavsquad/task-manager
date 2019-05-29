package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;

@Repository
public interface ITaskRepository extends JpaRepository<Task, String> {

    Collection<Task> findByUser(
            @NotNull final User user);

    Collection<Task> findByProjectAndUser(
            @NotNull final Project project,
            @NotNull final User user);


    Task findAnyByIdAndUser(
            @NotNull final String id,
            @NotNull final User user);

    @Query("SELECT e FROM Task e WHERE e.user = ?1 ORDER BY e.status DESC")
    Collection<Task> sortByStatus(
            @NotNull final User user);

    @Query("SELECT e FROM Task e WHERE e.user = ?1 ORDER BY e.dateBegin DESC")
    Collection<Task> sortByDateBegin(
            @NotNull final User user);

    @Query("SELECT e FROM Task e WHERE e.user = ?1 ORDER BY e.dateEnd DESC")
    Collection<Task> sortByDateEnd(
            @NotNull final User user);

    @Query("SELECT e FROM Task e WHERE e.user = ?3 AND (e.name LIKE (concat('%', ?1,'%')) OR e.description LIKE (concat('%', ?2,'%')))")
    Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final User user);
}
