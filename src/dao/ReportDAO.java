package dao;

import java.util.List;

public interface ReportDAO {
    List<Object[]> getDailyReport(String date);
}