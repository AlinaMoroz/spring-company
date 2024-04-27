package org.example.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
// имя графа и какую сущьность мы хотим с ней подтянуть
@NamedEntityGraph(
        name = "User.company",
        attributeNodes = @NamedAttributeNode("company")
)
@Entity
@Data
@ToString(exclude = "userChats")
@EqualsAndHashCode(of = "username", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
//public class User implements BaseEntity<Long>{
public class User extends AuditingEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String firstname;

    private String lastname;

    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


    @Builder.Default
    @NotAudited
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();
}
