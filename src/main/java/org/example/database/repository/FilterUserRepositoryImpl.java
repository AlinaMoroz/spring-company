package org.example.database.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.database.entity.Role;
import org.example.database.entity.User;
import org.example.dto.PersonalInfo;
import org.example.dto.UserFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {
    private final EntityManager entityManager;
    //идем через более низкий уровень, что бы улучшить производительность
    private final JdbcTemplate jdbcTemplate;
    // именнованый, что бы легче взаимодействоватать с параметрами
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private static final String FIND_BY_COMPANY_AND_ROLE = """
            SELECT 
                firstname,
                lastname,
                birth_date
            FROM users
            WHERE company_id = ?
                AND role = ?
            """;
    private static final String UPDATE_COMPANY_AND_ROLE = """
            UPDATE users
            SET  company_id = ?,
                role = ?
            WHERE id = ?
            """;

    private static final String UPDATE_COMPANY_AND_ROLE_NAMED = """
            UPDATE users
            SET  company_id = :company_id,
                role = :role
            WHERE id = :id
            """;

    @Override
    public List<User> findAllByFilter(UserFilter userFilter) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(User.class);
        var user = criteria.from(User.class);
        criteria.select(user);
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(userFilter.firstname())) {
            predicates.add(criteriaBuilder.like(user.get("firstname"), "%" + userFilter.firstname() + "%"));
        }
        if (!StringUtils.isEmpty(userFilter.lastname())) {
            predicates.add(criteriaBuilder.like(user.get("lastname"), "%" + userFilter.lastname() + "%"));
        }
        if (userFilter.birthDate() != null) {
            predicates.add(criteriaBuilder.lessThan(user.get("birthDate"), userFilter.birthDate()));
        }
        criteria.where(predicates.toArray(Predicate[]::new));
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE, (rs, rowNum) -> new PersonalInfo(
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getDate("birth_date").toLocalDate()
        ), companyId, role.name());
    }

    @Override
    public void updateCompanyAndRole(List<User> users) {
        var args = users.stream().map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
                .toList();
        jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args);
    }


    // отправление батчем используем только для update, delete, insert операция
    // А
    @Override
    public void updateCompanyAndRoleNamed(List<User> users) {
        var args = users.stream().map(user -> Map.of("companyId", user.getCompany().getId(),
                        "role", user.getRole().name(),
                        "id", user.getId()))
                .map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);

        namedJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED, args);

    }
}
