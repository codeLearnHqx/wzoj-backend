package com.hqx.wzoj.service;

import com.hqx.wzoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hqx.wzoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hqx.wzoj.model.entity.User;

/**
* @author hqx
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-12-17 17:57:05
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser 登录用户
     * @return 提交记录id
     */
     long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

}
