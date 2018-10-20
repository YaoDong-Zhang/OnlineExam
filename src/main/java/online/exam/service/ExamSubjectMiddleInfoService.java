package online.exam.service;

import online.exam.pojo.ExamSubjectMiddleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 14:56
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface ExamSubjectMiddleInfoService {
    public List<ExamSubjectMiddleInfo> findSubject(ExamSubjectMiddleInfo esm);

    public int addESM(Map<String, Object> map);

    public int removeSubjectWithExamPaper(Map<String, Object> map);

    public Integer getEsmByExamIdWithSubjectId(ExamSubjectMiddleInfo esm);
}
