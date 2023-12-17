package com.hqx.wzoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqx.wzoj.common.ErrorCode;
import com.hqx.wzoj.exception.BusinessException;
import com.hqx.wzoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hqx.wzoj.model.entity.QuestionSubmit;
import com.hqx.wzoj.model.entity.Question;
import com.hqx.wzoj.model.entity.User;
import com.hqx.wzoj.model.enums.QuestionSubmitLanguageEnum;
import com.hqx.wzoj.model.enums.QuestionSubmitStatusEnum;
import com.hqx.wzoj.service.QuestionService;
import com.hqx.wzoj.service.QuestionSubmitService;
import com.hqx.wzoj.mapper.QuestionSubmitMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author hqx
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2023-12-17 17:57:05
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    /**
     * 提交题目
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 检验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        Long questionId = questionSubmitAddRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Long userId = loginUser.getId();
        // 设置题目的初始信息
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        // 保存到数据库
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交失败");
        }
        return questionSubmit.getId();
    }

}
