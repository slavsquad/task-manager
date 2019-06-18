package ru.stepanenko.tm.api.repository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;

@Repository
public interface IProjectRepository extends JpaRepository<Project, String> {

    Collection<Project> findAllByUser(
            @NotNull final User user);

    Project findByIdAndUser(
            @NotNull final String id,
            @NotNull final User user);

    @Query("SELECT e FROM Project e WHERE e.user = ?1 ORDER BY e.status DESC")
    Collection<Project> sortByStatus(
            @NotNull final User user);

    @Query(value = "SELECT e FROM Project e WHERE e.user = ?1 ORDER BY e.dateBegin DESC")
    Collection<Project> sortByDateBegin(
            @NotNull final User user);

    @Query(value = "SELECT e FROM Project e WHERE e.user = ?1 ORDER BY e.dateEnd DESC")
    Collection<Project> sortByDateEnd(
            @NotNull final User user);

    @Query("SELECT e FROM Project e WHERE e.user = ?3 AND (e.name LIKE (concat('%', ?1,'%')) OR e.description LIKE (concat('%', ?2,'%')))")
    Collection<Project> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final User user);
}