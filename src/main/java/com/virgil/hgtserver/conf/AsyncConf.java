package com.virgil.hgtserver.conf;

import com.virgil.hgtserver.mappers.TravelDetailsMapper;
import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.pojo.TravelDetails;
import com.virgil.hgtserver.utils.WeightRandom;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class AsyncConf {
    @Async
    public void updateDetails( Map<String, List<SummaryWish>> map ,int travelId, TravelDetailsMapper travelDetailsMapper ){
        Set<SummaryWish> wishes = new HashSet<>();
        int select = 0;
        if(map.containsKey("half") && map.containsKey("whole")){
            int w1 = map.get("half").stream().mapToInt(SummaryWish::getSummaryDouzi).sum();
            int w2 = map.get("whole").stream().mapToInt(SummaryWish::getSummaryDouzi).sum();
            select = WeightRandom.select(w1, w2);
        }
        TravelDetails travelDetails = new TravelDetails();
        travelDetails.setTravelId(travelId);;
        travelDetails.setDate(LocalDate.now());
        if(select == 1 || !map.containsKey("whole")){
            while (wishes.size() < 2){
                wishes.add(WeightRandom.select(map.get("half")));
            }
            SummaryWish[] w = (SummaryWish[]) wishes.toArray();
            travelDetails.setMorning(w[0].getWish());
            travelDetails.setAfternoon(w[1].getWish());
        }
        else {
            while (wishes.size() < 1){
                wishes.add(WeightRandom.select(map.get("whole")));
            }
            String temp = wishes.iterator().next().getWish();
            travelDetails.setAfternoon(temp);
            travelDetails.setMorning(temp);
        }
        int prevSize = wishes.size();
        while (wishes.size() < prevSize + 3){
            wishes.add(WeightRandom.select(map.get("eat")));
        }
        SummaryWish[] w = (SummaryWish[]) wishes.toArray();
        travelDetails.setBreakfast(w[0].getWish());
        travelDetails.setLunch(w[1].getWish());
        travelDetails.setDinner(w[2].getWish());
        travelDetailsMapper.insertNewDetail(travelDetails);
    }
}
