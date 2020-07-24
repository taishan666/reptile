package com.tarzan.reptile.domain.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by tarzan liu on 2020/5/9.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SimpleEmail {

    private String subject;

    private String content;
}
