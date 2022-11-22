package com.doubledash.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="widget_subcription")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Widget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @NonNull
    @Column()
    private int user_id;

    @NonNull
    @Column()
    private long widget_id;

    @NonNull
    @Column()
    private int refresh_rate;

    @Column()
    private String param_1;

    @Column()
    private String param_2;

}
