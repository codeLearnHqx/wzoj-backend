package com.hqx.wzoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqx.wzoj.common.BaseResponse;
import com.hqx.wzoj.common.ErrorCode;
import com.hqx.wzoj.common.ResultUtils;
import com.hqx.wzoj.exception.BusinessException;
import com.hqx.wzoj.exception.ThrowUtils;
import com.hqx.wzoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hqx.wzoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.hqx.wzoj.model.entity.QuestionSubmit;
import com.hqx.wzoj.model.entity.User;
import com.hqx.wzoj.model.vo.QuestionSubmitVO;
import com.hqx.wzoj.service.QuestionSubmitService;
import com.hqx.wzoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     * @param questionSubmitAddRequest 题目信息
     * @param request 当前请求
     * @return 提交记录id
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                         HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }

    /**
     * 分页获取题目提交列表（除管理员外，普通用户只能看到非答案、提交代码等公开信息）
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionQueryRequest));
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }


}
