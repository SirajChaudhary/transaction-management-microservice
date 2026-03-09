package com.example.transaction.query;

import com.example.transaction.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 Service responsible for executing analytics queries related to loan data.
*/

@Service
@RequiredArgsConstructor
public class AnalyticsQueryService {

    private final LoanRepository loanRepository;

    /*
     Executes the loan analytics query and returns aggregated results.
    */
    public List<Object[]> loanAnalytics() {

        return loanRepository.loanAnalytics();
    }
}