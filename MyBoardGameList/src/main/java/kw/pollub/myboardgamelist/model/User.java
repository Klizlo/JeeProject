package kw.pollub.myboardgamelist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import kw.pollub.myboardgamelist.model.constraints.ValidPassword;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(nullable = false)
    private String name;
    @Email
    @NotEmpty
    @Column(nullable = false)
    private String email;
    @NotEmpty
    @ValidPassword
    @Column(nullable = false, unique = true)
    private String password;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "owner")
    private List<BoardGame> boardGames;

    public void addBoardGame(BoardGame boardGame) {
        boardGames.add(boardGame);
    }

    public void removeBoardGame(BoardGame boardGame) {
        boardGames.remove(boardGame);
    }

}
