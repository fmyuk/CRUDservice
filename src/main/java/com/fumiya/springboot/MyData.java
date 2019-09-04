package com.fumiya.springboot;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "mydata")
@Data
@NamedQueries({
        @NamedQuery(
                name = "findByName",
                query = "from MyData where name like :fname"
        ),
        @NamedQuery(
                name = "findByAge",
                query = "from MyData where age  > :min and age < :max"
        )
})
public class MyData {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column
    @NotNull
    private long id;

    @Column(length = 50, nullable = false)
    @NotEmpty
    private String name;

    @Column(length = 200, nullable = true)
    @Email
    private String mail;

    @Column(nullable = true)
    @Min(0)
    @Max(200)
    private Integer age;

    @Column(nullable = true)
    @Phone(onlyNumber = true)
    private String memo;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<MsgData> msgdatas;

    public List<MsgData> getMsgdatas() {
        return msgdatas;
    }

    public void setMsgdatas(List<MsgData> msgdatas) {
        this.msgdatas = msgdatas;
    }

}
