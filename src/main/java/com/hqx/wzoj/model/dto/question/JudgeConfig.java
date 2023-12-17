package com.hqx.wzoj.model.dto.question;

import lombok.Data;

/**
 * @Description 题目配置
 * @Create by hqx
 * @Date 2023/12/17 18:20
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制（ms）
     */
    private Long timeLimit;
    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;
    /**
     * 堆栈限制（KB）
     */
    private Long stackLimit;
}
