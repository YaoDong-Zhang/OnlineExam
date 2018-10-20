package online.exam.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @program: OnlineExam
 * @Date: 2018/10/1 10:07
 * @Author: Mr.Zhang
 * @Description:扩展类，用于封装历史考试信息、试卷信息、考试时间
 */

@Component
@Scope("prototype")
public class ExamHistoryPaper {

    // 试卷得分
    private int examScore;

    // 考试时间
    private String beginTime;

    private Integer examPaperId;
    private String examPaperName;
    private int subjectNum;
    private int examPaperScore;

    public int getExamScore() {
        return examScore;
    }

    public void setExamScore(int examScore) {
        this.examScore = examScore;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getExamPaperId() {
        return examPaperId;
    }

    public void setExamPaperId(Integer examPaperId) {
        this.examPaperId = examPaperId;
    }

    public String getExamPaperName() {
        return examPaperName;
    }

    public void setExamPaperName(String examPaperName) {
        this.examPaperName = examPaperName;
    }

    public int getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(int subjectNum) {
        this.subjectNum = subjectNum;
    }

    public int getExamPaperScore() {
        return examPaperScore;
    }

    public void setExamPaperScore(int examPaperScore) {
        this.examPaperScore = examPaperScore;
    }

}
