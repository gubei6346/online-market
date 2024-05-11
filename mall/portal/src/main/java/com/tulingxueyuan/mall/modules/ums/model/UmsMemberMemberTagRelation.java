package com.tulingxueyuan.mall.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户和标签关系表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_member_tag_relation")
@ApiModel(value="UmsMemberMemberTagRelation对象", description="用户和标签关系表")
public class UmsMemberMemberTagRelation implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long memberId;

    private Long tagId;


}
