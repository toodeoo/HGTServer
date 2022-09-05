package com.virgil.hgtserver.conf;

import com.virgil.hgtserver.mappers.TravelDetailsMapper;
import com.virgil.hgtserver.pojo.TravelDetails;
import com.virgil.hgtserver.utils.WeightRandom;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class AsyncConf {
    @Async
    public void updateDetails( Map<String, List<SummaryWish>> map ,int travelId, TravelDetailsMapper travelDetailsMapper ){
        List<SummaryWish> wishes = new ArrayList<>();
        int select = 0;
        Set<String> count = new HashSet<>();
        if(map.containsKey("half") && map.containsKey("whole")){
            int w1 = map.get("half").stream().mapToInt(SummaryWish::getSummaryDouzi).sum();
            int w2 = map.get("whole").stream().mapToInt(SummaryWish::getSummaryDouzi).sum();
            select = WeightRandom.select(w1, w2);
        }
        TravelDetails travelDetails = new TravelDetails();
        travelDetails.setTravelId(travelId);;
        travelDetails.setDate(LocalDate.now());

        if(select == 1 || !map.containsKey("whole")){
            while (count.size() < 2){
                SummaryWish temp = WeightRandom.select(map.get("half"));
                if(!count.contains(temp.getWish())) {
                    count.add(temp.getWish());
                    wishes.add(temp);
                }
            }
            travelDetails.setMorning(wishes.get(0).getWish());
            travelDetails.setAfternoon(wishes.get(1).getWish());
        }
        else {
            wishes.add(WeightRandom.select(map.get("whole")));
            String temp = wishes.get(0).getWish();
            travelDetails.setAfternoon(temp);
            travelDetails.setMorning(temp);
        }
        count.clear();
        wishes.clear();
        if(map.get("eat").size() == 3){
            wishes.add(map.get("eat").get(0));
            wishes.add(map.get("eat").get(1));
            wishes.add(map.get("eat").get(2));
        }
        else {
            while (count.size() < 3) {
                SummaryWish temp = WeightRandom.select(map.get("eat"));
                if (!count.contains(temp.getWish())) {
                    count.add(temp.getWish());
                    wishes.add(temp);
                }
            }
        }
        travelDetails.setBreakfast(wishes.get(0).getWish());
        travelDetails.setLunch(wishes.get(1).getWish());
        travelDetails.setDinner(wishes.get(2).getWish());
        if(travelDetailsMapper.queryCertainTravel(travelId) == null)
            travelDetailsMapper.insertNewDetail(travelDetails);
    }
}
