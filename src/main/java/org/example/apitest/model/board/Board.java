package org.example.apitest.model.board;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Board implements Serializable {

    private int id;
    private String title;
    private int thumb;
    private String creId;
    private Date creDtm;
}
