package com.hqx.wzoj.model.dto.question;

import lombok.Data;

/**
 * @Description 题目用例
 * @Create by hqx
 * @Date 2023/12/17 18:20
 */
@Data
public class JudgeCase {

    /**
     * 输入用例
     */
    private String input;
    /**
     * 输出用例
     */
    private String output;
}
