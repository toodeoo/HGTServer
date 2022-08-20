package com.virgil.hgtserver.utils;

import com.virgil.hgtserver.conf.SummaryWish;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WeightRandom {

    public static SummaryWish select( List<SummaryWish> list ){
        double random = ThreadLocalRandom.current().nextDouble();
        double[] weight = new double[list.size()];
        int sum = list.stream().mapToInt(SummaryWish::getSummaryDouzi).sum();
        int i = 0;
        for(SummaryWish wish: list){
            weight[i] = 1.0 * wish.getSummaryDouzi() / sum;
            i++;
        }
        int idx = Arrays.binarySearch(weight, random);
        if(idx < 0)
            idx = -idx - 1;
        else
            return list.get(idx);
        if(idx < weight.length && random < weight[idx]){
            return list.get(idx);
        }
        return list.get(0);
    }

    public static int select( int w1, int w2 ){
        int random = ThreadLocalRandom.current().nextInt(0, w1 + w2);
        if(random >= w1)
            return 2;
        else
            return 1;
    }

}
