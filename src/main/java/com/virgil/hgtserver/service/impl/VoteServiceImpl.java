package com.virgil.hgtserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.virgil.hgtserver.conf.RetCode;
import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.mappers.VoteMapper;
import com.virgil.hgtserver.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private TravelMapper travelMapper;

    @Scheduled(cron = "0 0 */1 * * ?")
    public void delHistory(){
        if(voteMapper.size() != null || !voteMapper.size().equals("0")) {
            voteMapper.delBeyondTime(LocalDate.now().toString());
        }
    }

    @Override
    public String hasVote( String token ) {
        int code = 1;
        if(voteMapper.queryNumByToken(token) == null)
            code = 0;
        else if (voteMapper.queryNumByToken(token).equals("0"))
            code = 0;
        if(voteMapper.queryIsVoteByToken(token) == null)
            code = 0;
        else if(voteMapper.queryIsVoteByToken(token).equals("0"))
            code = 0;
        return JSONObject.toJSONString(new RetCode(code));
    }

    @Override
    public String createVote( String token ,List<String> list ) {
        int code = voteMapper.size() == null ? 0 : Integer.parseInt(voteMapper.size()) + 1;
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);
        voteMapper.insertVote(code, token, start.toString(), end.toString());
        StringBuilder sb = new StringBuilder();
        for(String s: list){
            sb.append(s).append("+0 ");
        }
        voteMapper.insertVoteText(code, sb.toString());
        int id = travelMapper.queryMaxIdWithToken(token) == null ? -1 : Integer.parseInt(travelMapper.queryMaxIdWithToken(token));
        List<String> list_token = travelMapper.queryByTravelId(id);
        for(String user: list_token){
            if(!user.equals(token))
                voteMapper.addUser(code, user, start.toString(), end.toString());
        }
        return JSONObject.toJSONString(new RetCode(1));
    }

    @Override
    public String getVote( String token ) {
        int code = voteMapper.queryCodeByToken(token) == null ? -1 : Integer.parseInt(voteMapper.queryCodeByToken(token));
        JSONObject jsonObject = new JSONObject();
        if(code != -1) {
            String[] text = voteMapper.queryTextByCode(code).split(" ");
            List<String> list = Arrays.asList(text);
            jsonObject.put("vote", list);
            jsonObject.put("isVoted", voteMapper.queryIsVote(token));
        }
        else
            jsonObject.put("vote", new ArrayList<String>());
        return jsonObject.toJSONString();
    }

    @Override
    public String vote( String token ,List<String> list ) {
        int code = voteMapper.queryCodeByToken(token) == null ? -1 : Integer.parseInt(voteMapper.queryCodeByToken(token));
        if(code != -1) {
            String[] text = voteMapper.queryTextByCode(code).split(" ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0 ; i < text.length; i++) {
                if(!list.get(i).split("\\+")[0].equals(text[ i ].split("\\+")[0])) {
                    sb.append(text[ i ]);
                    continue;
                }
                sb.append(text[ i ].split("\\+")[ 0 ]);
                int x = Integer.parseInt(text[ i ].split("\\+")[ 1 ]);
                x += Integer.parseInt(list.get(i).split("\\+")[ 1 ]);
                sb.append("+").append(x).append(" ");
            }
            voteMapper.updateTextByCode(sb.toString() ,code);
            voteMapper.updateIsVote(token);
        }
        return JSONObject.toJSONString(new RetCode(code == -1 ? 0 : 1));
    }


}
