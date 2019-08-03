package com.zy.admin.business.model;



import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;



@TableName("demo")
@Data
public class Demo {
	
    @TableId
    private Integer id; 

    private String title;  

    private String content;  

    private Date createDate;  // 创建时间

    
    

}