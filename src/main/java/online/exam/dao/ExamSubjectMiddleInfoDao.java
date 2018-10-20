package online.exam.dao;

import online.exam.pojo.ExamSubjectMiddleInfo;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 14:52
 * @Author: Mr.Zhang
 * @Description:
 */
public interface ExamSubjectMiddleInfoDao {
    public List<ExamSubjectMiddleInfo> findSubject(ExamSubjectMiddleInfo esm);

    public int addESM(Map<String, Object> map);

    public int removeSubjectWithExamPaper(Map<String, Object> map);

    public Integer getEsmByExamIdWithSubjectId(ExamSubjectMiddleInfo esm);

}
