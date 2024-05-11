package com.tulingxueyuan.mall.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 成长值变化历史记录表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_growth_change_history")
@ApiModel(value="UmsGrowthChangeHistory对象", description="成长值变化历史记录表")
public class UmsGrowthChangeHistory implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long memberId;

    private Date createTime;

    @ApiModelProperty(value = "改变类型：0->增加；1->减少")
    private Integer changeType;

    @ApiModelProperty(value = "积分改变数量")
    private Integer changeCount;

    @ApiModelProperty(value = "操作人员")
    private String operateMan;

    @ApiModelProperty(value = "操作备注")
    private String operateNote;

    @ApiModelProperty(value = "积分来源：0->购物；1->管理员修改")
    private Integer sourceType;


}
