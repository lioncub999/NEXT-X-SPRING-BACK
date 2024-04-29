package org.example.apitest.model.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board implements Serializable {

    private int id;
    private String title;
    private String creId;
    private Date creDtm;
}
