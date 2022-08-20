package com.virgil.hgtserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wish {
    private String wish;
    private int travelId;
    private String token;
    private String flag; // 类别，half whole eat
    private int douzi;
    private int isEnd; // 结束投票为0，否则为1
}
