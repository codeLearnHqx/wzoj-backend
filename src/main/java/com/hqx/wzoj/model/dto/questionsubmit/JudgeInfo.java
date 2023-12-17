package com.hqx.wzoj.model.dto.questionsubmit;

import lombok.Data;

/**
 * @Description 判题信息
 * @Create by hqx
 * @Date 2023/12/17 19:39
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;
    /**
     * 消耗内存
     */
    private Long memory;
    /**
     * 消耗时间
     */
    private Long time;

}
