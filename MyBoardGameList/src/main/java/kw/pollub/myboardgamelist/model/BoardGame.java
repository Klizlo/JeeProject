package kw.pollub.myboardgamelist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "board_games")
@EntityListeners(AuditingEntityListener.class)
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(nullable = false)
    private String name;
    @NotEmpty
    @Column(nullable = false)
    private String developer;
    private String description;
    @Min(0)
    private Integer minNumberOfPlayers = 0;
    @Min(0)
    private Integer maxNumberOfPlayers = 0;
    @Min(0)
    private Double numberOfHours = 0.;
    private String picture;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardGame boardGame = (BoardGame) o;
        return Objects.equals(id, boardGame.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
