package com.virgil.hgtserver.conf;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class SummaryWish implements Comparable<SummaryWish> {
    private int summaryDouzi;
    private String wish;
    private String flag;

    public SummaryWish(){
        this.wish = "摆烂";
    }

    @Override
    public int compareTo( @NotNull SummaryWish o ) {
        return this.summaryDouzi - o.summaryDouzi;
    }
}
