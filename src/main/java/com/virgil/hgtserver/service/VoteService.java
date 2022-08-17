package com.virgil.hgtserver.service;

import java.util.List;

public interface VoteService {
    String hasVote(String token);

    String createVote( String token ,List<String> list );

    String getVote( String token );

    String vote( String token ,List<String> list );
}
