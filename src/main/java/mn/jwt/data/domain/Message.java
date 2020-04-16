package mn.jwt.data.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long id;
    private String content;

    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    @ManyToOne
    private User user;


}